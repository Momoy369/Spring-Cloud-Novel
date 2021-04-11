package com.java2nb.novel.domain;

import java.io.Serializable;


import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.java2nb.common.jsonserializer.LongToStringSerializer;


import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;



/**
 * Lembar berita
 * 
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2020-12-01 10:05:51
 */
public class NewsDO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	//Kunci utama
// Rentang yang dapat diwakili oleh long di java lebih besar dari angka di js, yang berarti beberapa nilai tidak dapat disimpan dalam js (menjadi nilai yang tidak akurat)
// Jadi ini diselesaikan dengan serialisasi menjadi string
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long id;
	//ID Kategori
			private Integer catId;
	//Nama Kategori
			private String catName;
	//sumber
			private String sourceName;
	//judul
			private String title;
	//kandungan
			private String content;
	//waktu rilis
			@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
		private Date createTime;
	// ID Penayang
// Rentang yang dapat diwakili oleh long di java lebih besar dari angka di js, yang berarti beberapa nilai tidak dapat disimpan dalam js (menjadi nilai yang tidak akurat)
// Jadi ini diselesaikan dengan serialisasi menjadi string
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long createUserId;
	//Waktu diperbarui
			@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
		private Date updateTime;
	// Perbarui ID orang tersebut
// Rentang yang dapat diwakili oleh long di java lebih besar dari angka di js, yang berarti beberapa nilai tidak dapat disimpan dalam js (menjadi nilai yang tidak akurat)
// Jadi ini diselesaikan dengan serialisasi menjadi string
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long updateUserId;

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
	 * Pengaturan: ID Kategori
	 */
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	/**
	 * Dapatkan: ID Kategori
	 */
	public Integer getCatId() {
		return catId;
	}
	/**
	 * Pengaturan: nama kategori
	 */
	public void setCatName(String catName) {
		this.catName = catName;
	}
	/**
	 * Dapatkan: nama kategori
	 */
	public String getCatName() {
		return catName;
	}
	/**
	 * Pengaturan: Sumber
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	/**
	 * Dapatkan: Sumber
	 */
	public String getSourceName() {
		return sourceName;
	}
	/**
	 * Pengaturan: Judul
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Dapatkan: Judul
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Pengaturan: Konten
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * Dapatkan: Konten
	 */
	public String getContent() {
		return content;
	}
	/**
	 * Pengaturan: waktu rilis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * Dapatkan: Waktu rilis
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * Pengaturan: ID Penayang
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * Dapatkan: ID Penayang
	 */
	public Long getCreateUserId() {
		return createUserId;
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
	/**
	 * Pengaturan: Perbarui ID orang
	 */
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	/**
	 * Dapatkan: ID Updater
	 */
	public Long getUpdateUserId() {
		return updateUserId;
	}
}
