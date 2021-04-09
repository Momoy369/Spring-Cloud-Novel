package com.java2nb.novel.core.utils;

public interface AzureBlobService{
 
    /**
     *Ekspor gambar jaringan yang dirayapi ke media penyimpanan Anda sendiri (lokal, OSS, fastDfs)
     * @param picSrc Jalur gambar jaringan yang dirayapi
     * @param picSavePath simpan rute
     * @return Alamat gambar baru
     * */
    String uploadFile(String picSrc, String picSavePath);
    
}