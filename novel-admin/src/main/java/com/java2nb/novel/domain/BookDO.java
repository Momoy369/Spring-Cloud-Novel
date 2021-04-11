package com.java2nb.novel.domain;

import java.io.Serializable;


import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.java2nb.common.jsonserializer.LongToStringSerializer;


import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;



/**
 * 小说表
 * 
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2020-12-01 03:49:46
 */
public class BookDO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	//Kunci utama
// Rentang yang dapat diwakili oleh long di java lebih besar dari angka di js, yang berarti beberapa nilai tidak dapat disimpan dalam js (menjadi nilai yang tidak akurat)
// Jadi ini diselesaikan dengan serialisasi menjadi string
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long id;
	// Arah kerja, 0: frekuensi laki-laki, 1: frekuensi perempuan '
			private Integer workDirection;
	// ID Genre
			private Integer catId;
	// Nama Genre
			private String catName;
	// ID sub-genre
			private Integer catChildId;
	// Nama sub-genre
			private String catChildName;
	// Sampul novel
			private String picUrl;
	// Judul buku
			private String bookName;
	// Nama hero
			private String heroName;
	// Nama heroine
			private String ladyName;
	// Gaya kerja, 0: hewan peliharaan yang manis, 1: sadomasokisme, 2: lainnya
			private Integer bookStyle;
	// Label
			private String bookLabel;
	// ID Penulis
		//Rentang yang dapat diwakili oleh long di java lebih besar dari angka di js, yang berarti beberapa nilai tidak dapat disimpan dalam js (menjadi nilai yang tidak akurat)
	// Jadi ini diselesaikan dengan serialisasi menjadi string
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long authorId;
	// Nama penulis
			private String authorName;
	// Blurb Buku
			private String bookDesc;
	// Skor
			private Float score;
	// Status buku, 0: berjalan, 1: selesai
			private Integer bookStatus;
	// Volume klik
		// Rentang yang dapat diwakili oleh long di java lebih besar dari angka di js, yang berarti beberapa nilai tidak dapat disimpan dalam js (menjadi nilai yang tidak akurat)
	// Jadi selesaikan dengan membuat serial menjadi string
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long visitCount;
	// Jumlah kata
			private Integer wordCount;
	// Jumlah komentar
			private Integer commentCount;
	// Pembelian koin kemarin
			private Integer yesterdayBuy;
	// ID Katalog
		//Rentang yang dapat diwakili oleh long di java lebih besar dari angka di js, yang berarti beberapa nilai tidak dapat disimpan dalam js (menjadi nilai yang tidak akurat)
	// Jadi ini diselesaikan dengan serialisasi menjadi string
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long lastIndexId;
	// Nama direktori terbaru
			private String lastIndexName;
	// Waktu pembaruan bab
			@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
		private Date lastIndexUpdateTime;
	// Status buku, 1: berbayar, 0: gratis
			private Integer isVip;
	// Status, 0: Masuk, 1: Di rak
			private Integer status;
	// Waktu diperbarui
			@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
		private Date updateTime;
	// Waktu dibuat
			@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
		private Date createTime;
	// ID Sumber perayapan
			private Integer crawlSourceId;
	// ID novel situs asal yang diambil
			private String crawlBookId;
	// Waktu penjelajahan terakhir
			@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
		private Date crawlLastTime;
	// Apakah pembaruan telah dihentikan, 0: tidak dihentikan, 1: dihentikan
			private Integer crawlIsStop;

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
	 * Pengaturan: arah kerja, 0: frekuensi pria, 1: frekuensi wanita '
	 */
	public void setWorkDirection(Integer workDirection) {
		this.workDirection = workDirection;
	}
	/**
	 * Akuisisi: arah kerja, 0: frekuensi pria, 1: frekuensi wanita '
	 */
	public Integer getWorkDirection() {
		return workDirection;
	}
	/**
	 * Pengaturan: ID genre
	 */
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	/**
	 * Dapatkan: ID genre
	 */
	public Integer getCatId() {
		return catId;
	}
	/**
	 * Pengaturan: nama genre
	 */
	public void setCatName(String catName) {
		this.catName = catName;
	}
	/**
	 * Dapatkan: nama genre
	 */
	public String getCatName() {
		return catName;
	}
	/**
	 * Pengaturan: ID sub-kategori
	 */
	public void setCatChildId(Integer catChildId) {
		this.catChildId = catChildId;
	}
	/**
	 * Dapatkan: ID sub-kategori
	 */
	public Integer getCatChildId() {
		return catChildId;
	}
	/**
	 * Pengaturan: nama sub-kategori
	 */
	public void setCatChildName(String catChildName) {
		this.catChildName = catChildName;
	}
	/**
	 * Dapatkan: nama sub-kategori
	 */
	public String getCatChildName() {
		return catChildName;
	}
	/**
	 * Pengaturan: sampul novel
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * Dapatkan: Sampul Novel
	 */
	public String getPicUrl() {
		return picUrl;
	}
	/**
	 * Pengaturan: Judul buku
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	/**
	 * Dapatkan judul buku
	 */
	public String getBookName() {
		return bookName;
	}
	/**
	 * Pengaturan: Nama Hero
	 */
	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}
	/**
	 * Dapatkan nama hero
	 */
	public String getHeroName() {
		return heroName;
	}
	/**
	 * Pengaturan: tentukan nama heroine
	 */
	public void setLadyName(String ladyName) {
		this.ladyName = ladyName;
	}
	/**
	 * Dapatkan nama heroine
	 */
	public String getLadyName() {
		return ladyName;
	}
	/**
	 * Pengaturan: gaya kerja, 0: hewan peliharaan yang manis, 1: sadomasokisme, 2: lainnya
	 */
	public void setBookStyle(Integer bookStyle) {
		this.bookStyle = bookStyle;
	}
	/**
	 * Akuisisi: gaya kerja, 0: hewan peliharaan yang manis, 1: sadomasokisme, 2: lainnya
	 */
	public Integer getBookStyle() {
		return bookStyle;
	}
	/**
	 * Pengaturan: Label kerja
	 */
	public void setBookLabel(String bookLabel) {
		this.bookLabel = bookLabel;
	}
	/**
	 * Dapatkan: Tag Kerja
	 */
	public String getBookLabel() {
		return bookLabel;
	}
	/**
	 * Pengaturan: id penulis
	 */
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	/**
	 * Dapatkan: ID Penulis
	 */
	public Long getAuthorId() {
		return authorId;
	}
	/**
	 * Pengaturan: Nama penulis
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	/**
	 * Dapatkan: Nama penulis
	 */
	public String getAuthorName() {
		return authorName;
	}
	/**
	 * Pengaturan: Deskripsi buku
	 */
	public void setBookDesc(String bookDesc) {
		this.bookDesc = bookDesc;
	}
	/**
	 * Dapatkan: Deskripsi buku
	 */
	public String getBookDesc() {
		return bookDesc;
	}
	/**
	 * Pengaturan: penilaian, bidang yang dipesan
	 */
	public void setScore(Float score) {
		this.score = score;
	}
	/**
	 * Dapatkan: Skor, bidang yang dipesan
	 */
	public Float getScore() {
		return score;
	}
	/**
	 * Setting: status buku, 0: berjalan, 1: selesai
	 */
	public void setBookStatus(Integer bookStatus) {
		this.bookStatus = bookStatus;
	}
	/**
	 * Dapatkan: status buku, 0: berjalan, 1: selesai
	 */
	public Integer getBookStatus() {
		return bookStatus;
	}
	/**
	 * Pengaturan: Klik
	 */
	public void setVisitCount(Long visitCount) {
		this.visitCount = visitCount;
	}
	/**
	 * Akuisisi: Klik
	 */
	public Long getVisitCount() {
		return visitCount;
	}
	/**
	 * Pengaturan: kata total
	 */
	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}
	/**
	 * Dapatkan: Total kata
	 */
	public Integer getWordCount() {
		return wordCount;
	}
	/**
	 * Pengaturan: Jumlah komentar
	 */
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	/**
	 * Dapatkan: Jumlah komentar
	 */
	public Integer getCommentCount() {
		return commentCount;
	}
	/**
	 * Setelan: Jumlah langganan kemarin
	 */
	public void setYesterdayBuy(Integer yesterdayBuy) {
		this.yesterdayBuy = yesterdayBuy;
	}
	/**
	 * Dapatkan: Jumlah langganan kemarin
	 */
	public Integer getYesterdayBuy() {
		return yesterdayBuy;
	}
	/**
	 * Setting: ID katalog terbaru
	 */
	public void setLastIndexId(Long lastIndexId) {
		this.lastIndexId = lastIndexId;
	}
	/**
	 * Dapatkan: ID katalog terbaru
	 */
	public Long getLastIndexId() {
		return lastIndexId;
	}
	/**
	 * Setting: nama direktori terbaru
	 */
	public void setLastIndexName(String lastIndexName) {
		this.lastIndexName = lastIndexName;
	}
	/**
	 * Dapatkan: nama direktori terbaru
	 */
	public String getLastIndexName() {
		return lastIndexName;
	}
	/**
	 * Setting: waktu update katalog terbaru
	 */
	public void setLastIndexUpdateTime(Date lastIndexUpdateTime) {
		this.lastIndexUpdateTime = lastIndexUpdateTime;
	}
	/**
	 * Dapatkan: Waktu pembaruan katalog terbaru
	 */
	public Date getLastIndexUpdateTime() {
		return lastIndexUpdateTime;
	}
	/**
	 * Setting: status buku, 1: bayar, 0: gratis
	 */
	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}
	/**
	 * Dapatkan: status buku, 1: bayar, 0: gratis
	 */
	public Integer getIsVip() {
		return isVip;
	}
	/**
	 * Pengaturan: status, 0: simpan, 1: simpan di rak
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * Dapatkan: Status, 0: Masuk, 1: Di rak
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * Pengaturan: waktu update
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * Dapatkan: waktu update
	 */
	public Date getUpdateTime() {
		return updateTime;
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
	 * Pengaturan: ID stasiun sumber crawler
	 */
	public void setCrawlSourceId(Integer crawlSourceId) {
		this.crawlSourceId = crawlSourceId;
	}
	/**
	 * Dapatkan: ID stasiun sumber crawler
	 */
	public Integer getCrawlSourceId() {
		return crawlSourceId;
	}
	/**
	 * Pengaturan: ID dari novel situs asal yang diambil
	 */
	public void setCrawlBookId(String crawlBookId) {
		this.crawlBookId = crawlBookId;
	}
	/**
	 * Dapatkan: ID novel situs asal yang diambil
	 */
	public String getCrawlBookId() {
		return crawlBookId;
	}
	/**
	 * Pengaturan: Waktu penjelajahan terakhir
	 */
	public void setCrawlLastTime(Date crawlLastTime) {
		this.crawlLastTime = crawlLastTime;
	}
	/**
	 * Ambil: Waktu pengambilan terakhir
	 */
	public Date getCrawlLastTime() {
		return crawlLastTime;
	}
	/**
	 * Pengaturan: apakah pembaruan telah dihentikan, 0: tidak dihentikan, 1: dihentikan
	 */
	public void setCrawlIsStop(Integer crawlIsStop) {
		this.crawlIsStop = crawlIsStop;
	}
	/**
	 * Dapatkan: Apakah update telah dihentikan, 0: tidak dihentikan, 1: dihentikan
	 */
	public Integer getCrawlIsStop() {
		return crawlIsStop;
	}
}
