package com.java2nb.novel.service;


import com.github.pagehelper.PageInfo;
import com.java2nb.novel.core.bean.PageBean;
import com.java2nb.novel.entity.Book;
import com.java2nb.novel.vo.BookSpVO;
import com.java2nb.novel.vo.BookVO;
import com.java2nb.novel.vo.EsBookVO;

/**
 * @author 11797
 */
public interface SearchService {

    /**
     * Impor ke es
     * @param book Data fiksi
     */
    void importToEs(Book book);

    /**
     * pencarian untuk
     * @param params Parameter pencarian
     * @param page Nomor halaman saat ini
     * @param pageSize Ukuran halaman
     * @return Informasi paging
     */
    PageBean<EsBookVO> searchBook(BookSpVO params, int page, int pageSize);
}
