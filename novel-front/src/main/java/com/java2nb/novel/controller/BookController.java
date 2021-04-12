package com.java2nb.novel.controller;

import com.github.pagehelper.PageInfo;
import com.java2nb.novel.core.bean.PageBean;
import com.java2nb.novel.core.bean.ResultBean;
import com.java2nb.novel.core.bean.UserDetails;
import com.java2nb.novel.core.enums.ResponseStatus;
import com.java2nb.novel.entity.Book;
import com.java2nb.novel.entity.BookComment;
import com.java2nb.novel.vo.BookSpVO;
import com.java2nb.novel.service.BookService;
import com.java2nb.novel.vo.BookVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 11797
 */
@RequestMapping("book")
@RestController
@Slf4j
@RequiredArgsConstructor
public class BookController extends BaseController{

    private final BookService bookService;

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.enable}")
    private Integer enableMq;


    /**
     * Query data daftar pengaturan halaman beranda baru
     * */
    @GetMapping("listBookSetting")
    public ResultBean listBookSetting(){
        return ResultBean.ok(bookService.listBookSettingVO());
    }

    /**
     * Kueri data daftar klik beranda
     * */
    @GetMapping("listClickRank")
    public ResultBean listClickRank(){
        return ResultBean.ok(bookService.listClickRank());
    }

    /**
     * Query home page data daftar buku baru
     * */
    @GetMapping("listNewRank")
    public ResultBean listNewRank(){
        return ResultBean.ok(bookService.listNewRank());
    }

    /**
     * Kueri data daftar pembaruan halaman beranda
     * */
    @GetMapping("listUpdateRank")
    public ResultBean listUpdateRank(){
        return ResultBean.ok(bookService.listUpdateRank());
    }

    /**
     * Buat kueri daftar kategori novel
     * */
    @GetMapping("listBookCategory")
    public ResultBean listBookCategory(){
        return ResultBean.ok(bookService.listBookCategory());
    }

    /**
     * Pencarian pagination
     * */
    @GetMapping("searchByPage")
    public ResultBean searchByPage(BookSpVO bookSP, @RequestParam(value = "curr", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "20") int pageSize){
        return ResultBean.ok(bookService.searchByPage(bookSP,page,pageSize));
    }

    /**
     * Pertanyaan detail novel
     * */
    @GetMapping("queryBookDetail/{id}")
    public ResultBean queryBookDetail(@PathVariable("id") Long id){
        return ResultBean.ok(bookService.queryBookDetail(id));
    }


    /**
     * Menanyakan informasi peringkat novel
     * */
    @GetMapping("listRank")
    public ResultBean listRank(@RequestParam(value = "type",defaultValue = "0") Byte type,@RequestParam(value = "limit",defaultValue = "30") Integer limit){
        return ResultBean.ok(bookService.listRank(type,limit));
    }

    /**
     * Tingkatkan klik
     * */
    @PostMapping("addVisitCount")
    public ResultBean addVisitCount(Long bookId){
        if(enableMq == 1) {
            rabbitTemplate.convertAndSend("ADD-BOOK-VISIT-EXCHANGE", null, bookId);
        }else {
            bookService.addVisitCount(bookId, 1);
        }
        return ResultBean.ok();
    }

    /**
     * Informasi terkait bagian kueri
     * */
    @GetMapping("queryBookIndexAbout")
    public ResultBean queryBookIndexAbout(Long bookId,Long lastBookIndexId) {
        Map<String,Object> data = new HashMap<>(2);
        data.put("bookIndexCount",bookService.queryIndexCount(bookId));
        String lastBookContent = bookService.queryBookContent(lastBookIndexId).getContent();
        if(lastBookContent.length()>42){
            lastBookContent=lastBookContent.substring(0,42);
        }
        data.put("lastBookContent",lastBookContent);
        return ResultBean.ok(data);
    }

    /**
     * Buat kueri tentang buku-buku serupa yang direkomendasikan menurut id kategori
     * */
    @GetMapping("listRecBookByCatId")
    public ResultBean listRecBookByCatId(Integer catId) {
        return ResultBean.ok(bookService.listRecBookByCatId(catId));
    }


    /**
     *Daftar pertanyaan penomoran halaman dari resensi buku
     * */
    @GetMapping("listCommentByPage")
    public ResultBean listCommentByPage(@RequestParam("bookId") Long bookId,@RequestParam(value = "curr", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "5") int pageSize) {
        return ResultBean.ok(bookService.listCommentByPage(null,bookId,page,pageSize));
    }

    /**
     * Tambahkan ulasan
     * */
    @PostMapping("addBookComment")
    public ResultBean addBookComment(BookComment comment, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(request);
        if (userDetails == null) {
            return ResultBean.fail(ResponseStatus.NO_LOGIN);
        }
        bookService.addBookComment(userDetails.getId(),comment);
        return ResultBean.ok();
    }

    /**
     * Kueri sepuluh koleksi novel terbaru yang diperbarui menurut ID novel
     * */
    @GetMapping("queryNewIndexList")
    public ResultBean queryNewIndexList(Long bookId){
        return ResultBean.ok(bookService.queryIndexList(bookId,"index_num desc",1,10));
    }

    /**
     * Halaman isi
     * */
    @GetMapping("/queryIndexList")
    public ResultBean indexList(Long bookId,@RequestParam(value = "curr", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "5") int pageSize,@RequestParam(value = "orderBy",defaultValue = "index_num desc") String orderBy) {
        return ResultBean.ok(new PageBean<>(bookService.queryIndexList(bookId,orderBy,page,pageSize)));
    }






}
