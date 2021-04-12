package com.java2nb.novel.service;



/**
 * @author 11797
 */
public interface OrderService {


    /**
     * Buat pesanan isi ulang
     *
     * @param payChannel Saluran pembayaran
     * @param payAmount Jumlah pembayaran
     * @param userId identitas pengguna
     * @return Nomor pesanan pedagang
     * */
    Long createPayOrder(Byte payChannel, Integer payAmount, Long userId);


    /**
     * Perbarui status pesanan
     * @param outTradeNo Nomor pesanan pedagang
     * @param tradeNo      Nomor pesanan Alipay / WeChat
     * @param tradeStatus Status pembayaran
     * */
    void updatePayOrder(Long outTradeNo, String tradeNo, String tradeStatus);
}
