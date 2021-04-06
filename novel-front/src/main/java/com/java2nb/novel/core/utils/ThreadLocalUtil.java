package com.java2nb.novel.core.utils;

import com.java2nb.novel.core.cache.CacheKey;
import com.java2nb.novel.core.cache.CacheService;

/**
 * Alat operasi template
 * @author Administrator
 */
public class ThreadLocalUtil {

    /**
     * Simpan direktori template yang diakses oleh thread saat ini
     * */
    private static ThreadLocal<String> templateDir = new ThreadLocal<>();

    /**
     * Simpan sessionID dari sesi saat ini
     * */
    private static ThreadLocal<String> clientId = new ThreadLocal<>();

    /**
     * Set direktori template yang harus dikunjungi saat ini
     * */
    public static void setTemplateDir(String dir){
        templateDir.set(dir);
    }

    /**
     * Dapatkan awalan jalur template yang harus dikunjungi saat ini
     * */
    public static String getTemplateDir(){
        CacheService cacheService = SpringUtil.getBean(CacheService.class);
        String prefix = cacheService.get(CacheKey.TEMPLATE_DIR_KEY+clientId.get());
        if(prefix != null){
            return prefix;
        }
        return templateDir.get();
    }
    
    /**
     * Tetapkan ID klien dari utas akses saat ini
     * */
    public static void setCientId(String id){
        clientId.set(id);
    }



}
