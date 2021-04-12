package com.java2nb.novel.page;

import com.java2nb.novel.controller.BaseController;
import com.java2nb.novel.core.bean.UserDetails;
import com.java2nb.novel.core.utils.ThreadLocalUtil;
import com.java2nb.novel.entity.*;
import com.java2nb.novel.service.AuthorService;
import com.java2nb.novel.service.BookService;
import com.java2nb.novel.service.NewsService;
import com.java2nb.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 11797
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class PageController extends BaseController {

    private final BookService bookService;

    private final NewsService newsService;

    private final AuthorService authorService;

    private final UserService userService;

    private final ThreadPoolExecutor threadPoolExecutor;


    @RequestMapping("{url}.html")
    public String module(@PathVariable("url") String url) {
        return url;
    }

    @RequestMapping("{module}/{url}.html")
    public String module2(@PathVariable("module") String module, @PathVariable("url") String url, HttpServletRequest request) {

        if (request.getRequestURI().startsWith("/author")) {
            //Kunjungi area penulis
            UserDetails user = getUserDetails(request);
            if (user == null) {
                //Belum masuk
                return "redirect:/user/login.html?originUrl=" + request.getRequestURI();
            }

            boolean isAuthor = authorService.isAuthor(user.getId());
            if (!isAuthor) {
                return "redirect:/author/register.html";
            }
        }

        return module + "/" + url;
    }

    @RequestMapping("{module}/{classify}/{url}.html")
    public String module3(@PathVariable("module") String module, @PathVariable("classify") String classify, @PathVariable("url") String url) {
        return module + "/" + classify + "/" + url;
    }

    /**
     * Halaman muka
     */
    @RequestMapping(path = {"/", "/index", "/index.html"})
    public String index() {
        return ThreadLocalUtil.getTemplateDir() + "index";
    }

    /**
     * Halaman kerja
     */
    @RequestMapping("book/bookclass.html")
    public String bookClass() {
        return "book/bookclass";
    }

    /**
     * Halaman peringkat
     */
    @RequestMapping("book/book_ranking.html")
    public String bookRank() {

        return ThreadLocalUtil.getTemplateDir() + "book/book_ranking";
    }


    /**
     * Halaman detail
     */
    @SneakyThrows
    @RequestMapping("/book/{bookId}.html")
    public String bookDetail(@PathVariable("bookId") Long bookId, Model model) {
        Book book = bookService.queryBookDetail(bookId);
        model.addAttribute("book", book);
        if (book.getLastIndexId() != null) {
            //Buat kueri ID katalog bab pertama
            Long firstBookIndexId = bookService.queryFirstBookIndexId(bookId);
            model.addAttribute("firstBookIndexId", firstBookIndexId);
        }
        return ThreadLocalUtil.getTemplateDir() + "book/book_detail";
    }

    /**
     * Halaman isi
     */
    @SneakyThrows
    @RequestMapping("/book/indexList-{bookId}.html")
    public String indexList(@PathVariable("bookId") Long bookId, Model model) {
        Book book = bookService.queryBookDetail(bookId);
        model.addAttribute("book", book);
        List<BookIndex> bookIndexList = bookService.queryIndexList(bookId, null, 1, null);
        model.addAttribute("bookIndexList", bookIndexList);
        model.addAttribute("bookIndexCount", bookIndexList.size());
        return ThreadLocalUtil.getTemplateDir() + "book/book_index";
    }

    /**
     * Halaman konten
     */
    @SneakyThrows
    @RequestMapping("/book/{bookId}/{bookIndexId}.html")
    public String indexList(@PathVariable("bookId") Long bookId, @PathVariable("bookIndexId") Long bookIndexId, HttpServletRequest request, Model model) {


        //Muat utas informasi dasar novel
        CompletableFuture<Book> bookCompletableFuture = CompletableFuture.supplyAsync(() -> {
            //Temukan buku
            Book book = bookService.queryBookDetail(bookId);
            log.debug("Memuat informasi dasar dari konten novel berakhir");
            return book;
        }, threadPoolExecutor);

        //Memuat utas informasi bab baru
        CompletableFuture<BookIndex> bookIndexCompletableFuture = CompletableFuture.supplyAsync(() -> {
            //Katalog kueri
            BookIndex bookIndex = bookService.queryBookIndex(bookIndexId);
            log.debug("Akhir pemuatan konten informasi bab yang baru++");
            return bookIndex;
        }, threadPoolExecutor);

        //Muat utas informasi bab sebelumnya dari novel, yang akan dieksekusi setelah utas pemuatan informasi bab novel dijalankan
        CompletableFuture<Long> preBookIndexIdCompletableFuture = bookIndexCompletableFuture.thenApplyAsync((bookIndex) -> {
            //Buat kueri ID katalog dari bab sebelumnya
            Long preBookIndexId = bookService.queryPreBookIndexId(bookId, bookIndex.getIndexNum());
            log.debug("Memuat utas informasi bab terakhir dari novel berakhir");
            return preBookIndexId;
        }, threadPoolExecutor);

        //Muat utas informasi bab berikutnya dari novel. Utas ini akan dijalankan setelah utas memuat informasi bab novel selesai.
        CompletableFuture<Long> nextBookIndexIdCompletableFuture = bookIndexCompletableFuture.thenApplyAsync((bookIndex) -> {
            //Buat kueri ID katalog dari bab berikutnya
            Long nextBookIndexId = bookService.queryNextBookIndexId(bookId, bookIndex.getIndexNum());
            log.debug("Memuat bab selanjutnya dari utas informasi baru berakhir");
            return nextBookIndexId;
        }, threadPoolExecutor);

        //Muat utas informasi konten baru
        CompletableFuture<BookContent> bookContentCompletableFuture = CompletableFuture.supplyAsync(() -> {
            //Konten kueri
            BookContent bookContent = bookService.queryBookContent(bookIndexId);
            log.debug("Akhir pemuatan utas informasi konten baru");
            return bookContent;
        }, threadPoolExecutor);


        //Tentukan apakah pengguna perlu membeli utas, yang akan dieksekusi setelah utas memuat informasi bab baru dijalankan
        CompletableFuture<Boolean> needBuyCompletableFuture = bookIndexCompletableFuture.thenApplyAsync((bookIndex) -> {
            //Tentukan apakah direktori dikenakan biaya
            if (bookIndex.getIsVip() != null && bookIndex.getIsVip() == 1) {
                //korban
                UserDetails user = getUserDetails(request);
                if (user == null) {
                    //Belum masuk, perlu membeli
                    return true;
                }
                //Tentukan apakah pengguna telah membeli katalog
                boolean isBuy = userService.queryIsBuyBookIndex(user.getId(), bookIndexId);
                if (!isBuy) {
                    //Belum membeli, perlu membeli
                    return true;
                }
            }

            log.debug("Tentukan apakah pengguna perlu membeli sampai akhir");
            return false;

        }, threadPoolExecutor);


        model.addAttribute("book", bookCompletableFuture.get());
        model.addAttribute("bookIndex", bookIndexCompletableFuture.get());
        model.addAttribute("preBookIndexId", preBookIndexIdCompletableFuture.get());
        model.addAttribute("nextBookIndexId", nextBookIndexIdCompletableFuture.get());
        model.addAttribute("bookContent", bookContentCompletableFuture.get());
        model.addAttribute("needBuy", needBuyCompletableFuture.get());

        return ThreadLocalUtil.getTemplateDir() + "book/book_content";
    }

    /**
     * Halaman komentar
     */
    @RequestMapping("/book/comment-{bookId}.html")
    public String commentList(@PathVariable("bookId") Long bookId, Model model) {
        //Temukan buku
        Book book = bookService.queryBookDetail(bookId);
        model.addAttribute("book", book);
        return "book/book_comment";
    }

    /**
     * Halaman konten berita
     */
    @RequestMapping("/about/newsInfo-{newsId}.html")
    public String newsInfo(@PathVariable("newsId") Long newsId, Model model) {
        //Periksa berita
        News news = newsService.queryNewsInfo(newsId);
        model.addAttribute("news", news);
        return "about/news_info";
    }


    /**
     * Halaman pendaftaran penulis
     */
    @RequestMapping("author/register.html")
    public String authorRegister(Author author, HttpServletRequest request, Model model) {
        UserDetails user = getUserDetails(request);
        if (user == null) {
            //Belum masuk
            return "redirect:/user/login.html?originUrl=/author/register.html";
        }

        if (StringUtils.isNotBlank(author.getInviteCode())) {
            //Kirimkan informasi pendaftaran penulis
            String errorInfo = authorService.register(user.getId(), author);
            if (StringUtils.isBlank(errorInfo)) {
                //registrasi berhasil
                return "redirect:/author/index.html";
            }
            model.addAttribute("LabErr", errorInfo);
            model.addAttribute("author", author);
        }
        return "author/register";
    }


}
