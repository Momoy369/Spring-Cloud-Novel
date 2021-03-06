package com.java2nb.novel.controller;

import com.github.pagehelper.PageInfo;
import com.java2nb.novel.core.bean.ResultBean;
import com.java2nb.novel.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author 11797
 */
@RequestMapping("news")
@RestController
@Slf4j
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    /**
     * Lihat Home News
     * */
    @GetMapping("listIndexNews")
    public ResultBean listIndexNews(){
        return ResultBean.ok(newsService.listIndexNews());
    }

    /**
     * Query daftar berita berdasarkan halaman
     * */
    @GetMapping("listByPage")
    public ResultBean listByPage(@RequestParam(value = "curr", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "5") int pageSize){
        return ResultBean.ok(newsService.listByPage(page,pageSize));
    }



}
