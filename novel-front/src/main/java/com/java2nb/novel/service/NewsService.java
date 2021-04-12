package com.java2nb.novel.service;


import com.java2nb.novel.core.bean.PageBean;
import com.java2nb.novel.entity.News;
import com.java2nb.novel.vo.NewsVO;

import java.util.List;

/**
 * @author 11797
 */
public interface NewsService {

    /**
     * Lihat Home News
     * @return
     * */
    List<News> listIndexNews();

    /**
     * Periksa berita
     * @param newsId ID berita
     * @return berita
     * */
    News queryNewsInfo(Long newsId);

    /**
     * Query daftar berita berdasarkan halaman
     * @param page nomor halaman
     * @param pageSize Ukuran Paging
     * @return Data paginasi berita
     * */
    PageBean<News> listByPage(int page, int pageSize);
}
