package com.java2nb.novel.service;


import com.github.pagehelper.PageInfo;
import com.java2nb.novel.core.bean.PageBean;
import com.java2nb.novel.vo.BookSpVO;
import com.java2nb.novel.vo.BookCommentVO;
import com.java2nb.novel.vo.BookSettingVO;
import com.java2nb.novel.entity.*;
import com.java2nb.novel.vo.BookVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 11797
 */
public interface BookService {

    /**
     * Query data daftar pengaturan halaman beranda baru
     * @return
     * */
    Map<Byte, List<BookSettingVO>> listBookSettingVO();

    /**
     * Kueri data daftar klik beranda
     * @return
     * */
    List<Book> listClickRank();

    /**
     * Query home page data daftar buku baru
     * @return
     * */
    List<Book> listNewRank();

    /**
     * Kueri data daftar pembaruan halaman beranda
     * @return
     * */
    List<BookVO> listUpdateRank();

    /**
     * Pencarian pagination
     * @param params Parameter pencarian
     * @param page nomor halaman
     * @param pageSize Ukuran Paging
     * @return Informasi Paginasi Koleksi Fiksi
     * */
    PageBean searchByPage(BookSpVO params, int page, int pageSize);

    /**
     * Buat kueri daftar kategori novel
     * @return Koleksi klasifikasi
     * */
    List<BookCategory> listBookCategory();

    /**
     * Pertanyaan detail novel
     * @return Informasi buku
     * @param id ID Buku*/
    Book queryBookDetail(Long id);

    /**
     * Daftar direktori kueri
     * @param bookId ID Buku
     * @param orderBy Menyortir
     * @param page Nomor halaman kueri
     *@param pageSize Ukuran Paging
     *@return Koleksi katalog
     * */
    List<BookIndex> queryIndexList(Long bookId, String orderBy, Integer page, Integer pageSize);


    /**
     * Katalog kueri
     * @param bookIndexId ID Direktori
     * @return Informasi direktori
     * */
    BookIndex queryBookIndex(Long bookIndexId);

    /**
     * Buat kueri ID katalog dari bab sebelumnya
     * @param bookId ID Buku
     * @param indexNum Nomor katalog
     * @return ID dari direktori bab sebelumnya, atau 0 jika tidak
     * */
    Long queryPreBookIndexId(Long bookId, Integer indexNum);

    /**
     * Buat kueri ID katalog dari bab berikutnya
     * @param bookId ID Buku
     * @param indexNum Nomor katalog
     * @return ID dari direktori bab berikutnya, jika tidak, kembalikan 0
     * */
    Long queryNextBookIndexId(Long bookId, Integer indexNum);

    /**
     * Isi bab kueri
     * @param bookIndexId ID Direktori
     * @return Isi buku
     * */
    BookContent queryBookContent(Long bookIndexId);

    /**
     * Menanyakan informasi peringkat novel
     * @param type Jenis peringkat, 0 peringkat klik, 1 peringkat buku baru, 2 peringkat pembaruan
     * @param limit Jumlah kueri
     * @return Koleksi fiksi
     * */
    List<Book> listRank(Byte type, Integer limit);

    /**
     * Tingkatkan klik
     * @param bookId ID Buku
     * @param visitCount*/
    void addVisitCount(Long bookId, Integer visitCount);

    /**
     * Kueri jumlah bab
     * @param bookId ID Buku
     * @return Jumlah bab
     * */
    long queryIndexCount(Long bookId);

    /**
     * Buat kueri tentang buku-buku serupa yang direkomendasikan menurut id kategori
     * @param catId Id kategori
     * @return Koleksi buku
     * */
    List<Book> listRecBookByCatId(Integer catId);

    /**
     * Buat kueri ID katalog bab pertama
     * @param bookId ID Buku
     * @return ID katalog bab pertama
     * */
    Long queryFirstBookIndexId(Long bookId);

    /**
     *Daftar pertanyaan penomoran halaman dari resensi buku
     * @param userId ID pengguna
     * @param bookId ID Buku
     * @param page nomor halaman
     * @param pageSize Ukuran Paging
     * @return Mengomentari data penomoran halaman
     * */
    PageBean<BookCommentVO> listCommentByPage(Long userId, Long bookId, int page, int pageSize);

    /**
     * Tambahkan ulasan
     * @param userId ID pengguna
     * @param comment komentar
     * */
    void addBookComment(Long userId, BookComment comment);

    /**
     * Dapatkan atau buat Id penulis dengan nama penulis
     * @param authorName Nama penulis
     * @param workDirection Arah kerja
     * @return ID Penulis
     * */
    Long getOrCreateAuthorIdByName(String authorName, Byte workDirection);



    /**
     * Buat kueri ID novel
     * @param bookName Judul
     * @param author Nama penulis
     * @return ID Novel
     * */
    Long queryIdByNameAndAuthor(String bookName, String author);

    /**
     * Menanyakan koleksi nomor katalog berdasarkan ID novel
     * @param bookId ID Novel
     * @return Koleksi nomor katalog
     * */
    List<Integer> queryIndexNumByBookId(Long bookId);

    /**
     * Pertanyaan novel tentang gambar jaringan
     *
     * @param localPicPrefix
     * @param limit Jumlah kueri
     * @return Kembali ke koleksi novel
     * */
    List<Book> queryNetworkPicBooks(String localPicPrefix, Integer limit);


    /**
     * Perbarui gambar jaringan novel yang dirayapi ke media penyimpanan Anda sendiri (lokal, OSS, fastDfs)
     * @param picUrl Jalur gambar jaringan yang dirayapi
     * @param bookId ID Novel
     */
    void updateBookPicToLocal(String picUrl, Long bookId);

    /**
     * Buat kueri daftar penomoran halaman novel menurut ID penulis
     * @param userId ID pengguna
     * @param page nomor halaman
     * @param pageSize Ukuran Paging
     * @return Data pagination baru
     * */
    PageBean<Book> listBookPageByUserId(Long userId, int page, int pageSize);

    /**
     * Publikasikan novel
     * @param book Informasi Novel
     * @param authorId ID Penulis
     * @param penName Nama pena penulis
     * */
    void addBook(Book book, Long authorId, String penName);

    /**
     * Perbarui status novel, tambah atau hapus
     * @param bookId ID Novel
     * @param status Status diperbarui
     * @param authorId ID Penulis
     * */
    void updateBookStatus(Long bookId, Byte status, Long authorId);

    /**
     * Publikasikan konten bab
     * @param bookId ID Novel
     * @param indexName Nama bab
     * @param content Isi bab
     * @param isVip Gratis atau berbayar
     * @param authorId ID Penulis   */
    void addBookContent(Long bookId, String indexName, String content, Byte isVip, Long authorId);


    /**
     * Buat kueri halaman daftar buku demi halaman sesuai dengan waktu pembaruan
     * @param startDate Waktu mulai, termasuk waktu
     * @param limit Jumlah kueri
     * @return Daftar buku
     * */
    List<Book> queryBookByUpdateTimeByPage(Date startDate, int limit);

    /**
     * Buat kueri daftar karya
     * @param authorId ID Penulis
     * @return Daftar karya
     */
    List<Book> queryBookList(Long authorId);

    /**
     * Hapus bab
     * @param indexId
     * @param authorId ID Penulis
     */
    void deleteIndex(Long indexId, Long authorId);

    /**
     * Perbarui nama bab
     * @param indexId
     * @param indexName
     * @param authorId
     */
    void updateIndexName(Long indexId, String indexName, Long authorId);

    /**
     * Isi bab kueri
     * @param indexId
     * @param authorId
     * @return
     */
    String queryIndexContent(Long indexId, Long authorId);

    /**
     *  Perbarui konten bab
     * @param indexId
     * @param indexName
     * @param content
     * @param authorId
     */
    void updateBookContent( Long indexId, String indexName, String content, Long authorId);
}
