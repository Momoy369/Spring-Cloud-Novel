package com.java2nb.novel.service;


/**
 * @author 11797
 */
public interface FileService {

    /**
     *Ekspor gambar jaringan yang dirayapi ke media penyimpanan Anda sendiri (lokal, OSS, fastDfs)
     * @param picSrc Jalur gambar jaringan yang dirayapi
     * @param picSavePath simpan rute
     * @return Alamat gambar baru
     * */
    String transFile(String picSrc, String picSavePath);

}
