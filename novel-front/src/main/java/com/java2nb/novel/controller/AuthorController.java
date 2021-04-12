package com.java2nb.novel.controller;

import com.github.pagehelper.PageInfo;
import com.java2nb.novel.core.bean.ResultBean;
import com.java2nb.novel.core.bean.UserDetails;
import com.java2nb.novel.core.enums.ResponseStatus;
import com.java2nb.novel.core.exception.BusinessException;
import com.java2nb.novel.core.utils.BeanUtil;
import com.java2nb.novel.entity.Author;
import com.java2nb.novel.entity.Book;
import com.java2nb.novel.service.AuthorService;
import com.java2nb.novel.service.BookService;
import com.java2nb.novel.service.FriendLinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author 11797
 */
@RequestMapping("author")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthorController extends BaseController{

    private final AuthorService authorService;

    private final BookService bookService;

    /**
     * Periksa apakah nama pena ada
     * */
    @GetMapping("checkPenName")
    public ResultBean checkPenName(String penName){

        return ResultBean.ok(authorService.checkPenName(penName));
    }

    /**
     * Kueri daftar halaman novel yang diterbitkan penulis
     * */
    @GetMapping("listBookByPage")
    public ResultBean listBookByPage(@RequestParam(value = "curr", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int pageSize ,HttpServletRequest request){

        return ResultBean.ok(bookService.listBookPageByUserId(getUserDetails(request).getId(),page,pageSize));
    }

    /**
     * Publikasikan novel
     * */
    @PostMapping("addBook")
    public ResultBean addBook(@RequestParam("bookDesc") String bookDesc,Book book,HttpServletRequest request){

        Author author = checkAuthor(request);

        //bookDesc tidak dapat menggunakan objek buku untuk menerima, jika tidak spasi sebelumnya akan otomatis dihapus
        book.setBookDesc(bookDesc
                .replaceAll("\\n","<br>")
                .replaceAll("\\s","&nbsp;"));
        //Publikasikan novel
        bookService.addBook(book,author.getId(),author.getPenName());

        return ResultBean.ok();
    }

    /**
     * Perbarui status novel, tambah atau hapus
     * */
    @PostMapping("updateBookStatus")
    public ResultBean updateBookStatus(Long bookId,Byte status,HttpServletRequest request){
        Author author = checkAuthor(request);

        //Perbarui status novel, tambah atau hapus
        bookService.updateBookStatus(bookId,status,author.getId());

        return ResultBean.ok();
    }



    /**
     * Hapus bab
     */
    @DeleteMapping("deleteIndex/{indexId}")
    public ResultBean deleteIndex(@PathVariable("indexId") Long indexId,  HttpServletRequest request) {

        Author author = checkAuthor(request);

        //Hapus bab
        bookService.deleteIndex(indexId, author.getId());

        return ResultBean.ok();
    }

    /**
     * Perbarui nama bab
     */
    @PostMapping("updateIndexName")
    public ResultBean updateIndexName(Long indexId,  String indexName, HttpServletRequest request) {

        Author author = checkAuthor(request);

        //Perbarui nama bab
        bookService.updateIndexName(indexId, indexName, author.getId());

        return ResultBean.ok();
    }




    /**
     * Publikasikan konten bab
     */
    @PostMapping("addBookContent")
    public ResultBean addBookContent(Long bookId, String indexName, String content,Byte isVip, HttpServletRequest request) {
        Author author = checkAuthor(request);

        content = content.replaceAll("\\n", "<br>")
                .replaceAll("\\s", "&nbsp;");
        //Publikasikan konten bab
        bookService.addBookContent(bookId, indexName, content,isVip, author.getId());

        return ResultBean.ok();
    }

    /**
     * Isi bab kueri
     */
    @GetMapping("queryIndexContent/{indexId}")
    public ResultBean queryIndexContent(@PathVariable("indexId") Long indexId,  HttpServletRequest request) {

        Author author = checkAuthor(request);

        String content = bookService.queryIndexContent(indexId, author.getId());

        content = content.replaceAll("<br>", "\n")
                .replaceAll("&nbsp;", " ");

        return ResultBean.ok(content);
    }

    /**
     * Perbarui konten bab
     */
    @PostMapping("updateBookContent")
    public ResultBean updateBookContent(Long indexId, String indexName, String content, HttpServletRequest request) {
        Author author = checkAuthor(request);

        content = content.replaceAll("\\n", "<br>")
                .replaceAll("\\s", "&nbsp;");
        //Perbarui konten bab
        bookService.updateBookContent(indexId, indexName, content, author.getId());

        return ResultBean.ok();
    }

    /**
     * Kueri daftar halaman statistik pendapatan harian penulis
     * */
    @GetMapping("listIncomeDailyByPage")
    public ResultBean listIncomeDailyByPage(@RequestParam(value = "curr", defaultValue = "1") int page,
                                            @RequestParam(value = "limit", defaultValue = "10") int pageSize ,
                                            @RequestParam(value = "bookId", defaultValue = "0") Long bookId,
                                            @RequestParam(value = "startTime",defaultValue = "2020-05-01") Date startTime,
                                            @RequestParam(value = "endTime",defaultValue = "2030-01-01") Date endTime,
                                            HttpServletRequest request){

        return ResultBean.ok(authorService.listIncomeDailyByPage(page,pageSize,getUserDetails(request).getId(),bookId,startTime,endTime));
    }


    /**
     * Kueri daftar halaman statistik pendapatan bulanan penulis
     * */
    @GetMapping("listIncomeMonthByPage")
    public ResultBean listIncomeMonthByPage(@RequestParam(value = "curr", defaultValue = "1") int page,
                                            @RequestParam(value = "limit", defaultValue = "10") int pageSize ,
                                            @RequestParam(value = "bookId", defaultValue = "0") Long bookId,
                                            HttpServletRequest request){

        return ResultBean.ok(authorService.listIncomeMonthByPage(page,pageSize,getUserDetails(request).getId(),bookId));
    }

    private Author checkAuthor(HttpServletRequest request) {

        UserDetails userDetails = getUserDetails(request);
        if (userDetails == null) {
            throw new BusinessException(ResponseStatus.NO_LOGIN);
        }

        //Menanyakan informasi penulis
        Author author = authorService.queryAuthor(userDetails.getId());

        //Tentukan apakah status penulis normal
        if (author.getStatus() == 1) {
            //Status dilarang, tidak bisa menerbitkan novel
            throw new BusinessException(ResponseStatus.AUTHOR_STATUS_FORBIDDEN);
        }


        return author;


    }




}
