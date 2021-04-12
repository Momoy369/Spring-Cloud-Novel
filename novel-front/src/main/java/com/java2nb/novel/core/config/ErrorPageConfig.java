package com.java2nb.novel.core.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Konfigurasi halaman kesalahan
 * @author xiongxiaoyang
 */
@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        /*1.Jenis kesalahannya adalah 404, dan halaman web 404.html ditampilkan secara default*/
        ErrorPage e404 = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
        /**
        TODO 2.Jenis kesalahannya adalah 500, yang berarti server merespons dengan tidak benar, dan halaman web /500.html ditampilkan secara default
        */
        registry.addErrorPages(e404);
    }
}
