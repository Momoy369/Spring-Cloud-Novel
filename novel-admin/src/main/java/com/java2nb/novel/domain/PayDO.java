package com.java2nb.novel.domain;

import java.io.Serializable;


import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.java2nb.common.jsonserializer.LongToStringSerializer;


import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;



/**
 * Pesanan top-up
 * 
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2020-12-01 03:49:57
 */
public class PayDO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	//Kunci utama
// Rentang yang dapat diwakili oleh long di java lebih besar dari angka di js, yang berarti beberapa nilai tidak dapat disimpan dalam js (menjadi nilai yang tidak akurat)
// Jadi ini diselesaikan dengan serialisasi menjadi string
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long id;
	// Dicadangkan
// Rentang yang dapat diwakili oleh long di java lebih besar dari angka di js, yang berarti beberapa nilai tidak dapat disimpan dalam js (menjadi nilai yang tidak akurat)
// Jadi ini diselesaikan dengan serialisasi menjadi string
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long outTradeNo;
	//jumlah order
			private String tradeNo;
	//Menjaga
			private Integer payChannel;
	//Perdagangkan Koin Pisang
			private Integer totalAmount;
	// ID pengguna pembayaran
// Rentang yang dapat diwakili oleh long di java lebih besar dari angka di js, yang berarti beberapa nilai tidak dapat disimpan dalam js (menjadi nilai yang tidak akurat)
// Jadi ini diselesaikan dengan serialisasi menjadi string
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long userId;
	//Status pembayaran: 0: pembayaran gagal, 1: pembayaran berhasil, 2: pembayaran tertunda
			private Integer payStatus;
	//Waktu penciptaan
			@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
		private Date createTime;
	//Perbarui waktu
			@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
		private Date updateTime;

	/**
	 * Pengaturan: Kunci utama
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Dapatkan: Kunci utama
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Pengaturan: dipesan
	 */
	public void setOutTradeNo(Long outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	/**
	 * Dapatkan: Keep
	 */
	public Long getOutTradeNo() {
		return outTradeNo;
	}
	/**
	 * Pengaturan: nomor pesanan
	 */
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	/**
	 * Dapatkan: nomor pesanan
	 */
	public String getTradeNo() {
		return tradeNo;
	}
	/**
	 * Pengaturan: dipesan
	 */
	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}
	/**
	 * Dapatkan: Keep
	 */
	public Integer getPayChannel() {
		return payChannel;
	}
	/**
	 * Setting: Tukarkan Koin Pisang
	 */
	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * Peroleh: Tukarkan Koin Pisang
	 */
	public Integer getTotalAmount() {
		return totalAmount;
	}
	/**
	 * Pengaturan: Bayar ID pengguna
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * Dapatkan: Membayar ID pengguna
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * Pengaturan: status pembayaran: 0: pembayaran gagal, 1: pembayaran berhasil, 2: pembayaran tertunda
	 */
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	/**
	 * Dapatkan: status pembayaran: 0: pembayaran gagal, 1: pembayaran berhasil, 2: pembayaran tertunda
	 */
	public Integer getPayStatus() {
		return payStatus;
	}
	/**
	 * Pengaturan: Waktu pembuatan
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * Dapatkan: Waktu pembuatan
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * Pengaturan: waktu update
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * Dapatkan: Perbarui waktu
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
