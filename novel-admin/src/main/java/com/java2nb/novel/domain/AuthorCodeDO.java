package com.java2nb.novel.domain;

import java.io.Serializable;


import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.java2nb.common.jsonserializer.LongToStringSerializer;


import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;



/**
 * Daftar kode undangan penulis
 * 
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2020-05-13 11:29:15
 */
public class AuthorCodeDO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	//Kunci utama
		//Rentang yang dapat diwakili oleh long di java lebih besar dari angka di js, yang berarti beberapa nilai tidak dapat disimpan dalam js (menjadi nilai yang tidak akurat)
	//Jadi selesaikan dengan membuat serialisasi menjadi string
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long id;
	//Kode undangan
			private String inviteCode;
	//Waktu efektif
			@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
		private Date validityTime;
	//Sudahkah Anda menggunakannya, 0: tidak digunakan, 1: bekas
			private Integer isUse;
	//Waktu penciptaan
			@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
		private Date createTime;
	//ID Pembuat
		//Rentang yang dapat diwakili oleh long di java lebih besar dari angka di js, yang berarti beberapa nilai tidak dapat disimpan dalam js (menjadi nilai yang tidak akurat)
	//Jadi selesaikan dengan membuat serialisasi menjadi string
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long createUserId;

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
	 * Pengaturan: Kode undangan
	 */
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	/**
	 * Dapatkan: Kode Undangan
	 */
	public String getInviteCode() {
		return inviteCode;
	}
	/**
	 * Setting: waktu efektif
	 */
	public void setValidityTime(Date validityTime) {
		this.validityTime = validityTime;
	}
	/**
	 * Dapatkan: Waktu efektif
	 */
	public Date getValidityTime() {
		return validityTime;
	}
	/**
	 * Setting: apakah digunakan, 0: tidak digunakan, 1: digunakan
	 */
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	/**
	 * Dapatkan: Sudahkah Anda menggunakannya, 0: tidak digunakan, 1: bekas
	 */
	public Integer getIsUse() {
		return isUse;
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
	 * Pengaturan: ID Pembuat
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * Dapatkan: ID Pembuat Konten
	 */
	public Long getCreateUserId() {
		return createUserId;
	}
}
