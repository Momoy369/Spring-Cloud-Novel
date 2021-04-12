package com.java2nb.novel.controller;

import com.java2nb.novel.core.bean.ResultBean;
import com.java2nb.novel.core.cache.CacheKey;
import com.java2nb.novel.core.cache.CacheService;
import com.java2nb.novel.core.enums.ResponseStatus;
import com.java2nb.novel.service.BookService;
import com.java2nb.novel.service.FriendLinkService;
import com.java2nb.novel.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 11797
 */
@RequestMapping("cache")
@RestController
@Slf4j
@RequiredArgsConstructor
public class CacheController {

    @Value("${cache.manager.password}")
    private String cacheManagerPass;

    private final CacheService cacheService;

    private final BookService bookService;

    private final NewsService newsService;

    private final FriendLinkService friendLinkService;

    /**
     * Segarkan cache
     * @param type Jenis cache, 1: Rekomendasi buku halaman muka, 2: Berita halaman muka, 3: Link pertemanan halaman muka
     * */
    @GetMapping("refresh/{pass}/{type}")
    public ResultBean refreshCache(@PathVariable("type") Byte type, @PathVariable("pass") String pass){
        if(!cacheManagerPass.equals(pass)){
            return ResultBean.fail(ResponseStatus.PASSWORD_ERROR);
        }
        switch (type){
            case 1:{
                //Segarkan cache buku yang direkomendasikan di beranda
                cacheService.del(CacheKey.INDEX_BOOK_SETTINGS_KEY);
                bookService.listBookSettingVO();
                break;
            }
            case 2:{
                //Segarkan cache berita beranda
                cacheService.del(CacheKey.INDEX_NEWS_KEY);
                newsService.listIndexNews();
                break;
            }
            case 3:{
                //Segarkan tautan beranda
                cacheService.del(CacheKey.INDEX_LINK_KEY);
                friendLinkService.listIndexLink();
                break;
            }
            default:{
                break;
            }

        }

        return ResultBean.ok();
    }




}
