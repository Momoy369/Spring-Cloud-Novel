package com.java2nb.novel.domain;

import java.io.Serializable;


import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.java2nb.common.jsonserializer.LongToStringSerializer;


import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;



/**
 * Tabel kategori berita
 * 
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2020-12-01 10:03:41
 */
public class CategoryDO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	//Kunci utama
			private Integer id;
	//Nama Kategori
			private String name;
	//Menyortir
			private Integer sort;
	//
		//Rentang yang dapat diwakili oleh long di java lebih besar dari angka di js, yang berarti beberapa nilai tidak dapat disimpan dalam js (menjadi nilai yang tidak akurat)
	//Jadi selesaikan dengan membuat serialisasi menjadi string
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long createUserId;
	//
			@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
		private Date createTime;
	//
		//Rentang yang dapat diwakili oleh long di java lebih besar dari angka di js, yang berarti beberapa nilai tidak dapat disimpan dalam js (menjadi nilai yang tidak akurat)
	//Jadi selesaikan dengan membuat serialisasi menjadi string
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long updateUserId;
	//
			@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
		private Date updateTime;

	/**
	 * Pengaturan: Kunci utama
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * Dapatkan: Kunci utama
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Pengaturan: nama kategori
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Dapatkan: nama kategori
	 */
	public String getName() {
		return name;
	}
	/**
	 * Pengaturan: Sortir
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * Dapatkan: Sortir
	 */
	public Integer getSort() {
		return sort;
	}
	/**
	 * Pengaturan:
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * Pengaturan:
	 */
	public Long getCreateUserId() {
		return createUserId;
	}
	/**
	 * Pengaturan:
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * Memperoleh:
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * Pengaturan:
	 */
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	/**
	 * Memperoleh:
	 */
	public Long getUpdateUserId() {
		return updateUserId;
	}
	/**
	 * Pengaturan:
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * Memperoleh:
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
