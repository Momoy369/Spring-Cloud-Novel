package com.java2nb.novel.service;


import com.java2nb.novel.core.bean.PageBean;
import com.java2nb.novel.entity.Author;
import com.java2nb.novel.entity.AuthorIncome;
import com.java2nb.novel.entity.AuthorIncomeDetail;
import com.java2nb.novel.entity.FriendLink;

import java.util.Date;
import java.util.List;

/**
 * @author 11797
 */
public interface AuthorService {

    /**
     * Periksa apakah nama pena ada
     * @param penName Nama pena yang diperiksa
     * @return benar: nama pena ada, salah: nama pena tidak ada
     * */
    Boolean checkPenName(String penName);

    /**
     * Pendaftaran penulis
     * @param userId ID pengguna terdaftar
     *@param author pesan pendaftaran
     * @return Kembalikan pesan kesalahan
     * */
    String register(Long userId, Author author);

    /**
     * Tentukan apakah ia seorang penulis
     * @param userId identitas pengguna
     * @return benar: dia penulis, salah: bukan penulis
     * */
    Boolean isAuthor(Long userId);

    /**
     * Menanyakan informasi penulis
     * @param userId identitas pengguna
     * @return Objek penulis
     * */
    Author queryAuthor(Long userId);

    /**
     * Buat kueri daftar penulis
     * @return Daftar penulis
     * @param limit Jumlah kueri
     * @param maxAuthorCreateTime Waktu aplikasi maksimal
     */
    List<Author> queryAuthorList(int limit, Date maxAuthorCreateTime);

    /**
     * Tanyakan apakah statistik hari pendapatan ada di database
     * @param bookId ID Pekerjaan
     * @param date Waktu penghasilan
     * @return true: telah disimpan, false: tidak disimpan
     */
    boolean queryIsStatisticsDaily(Long bookId, Date date);


    /**
     * Simpan statistik pendapatan harian (berdasarkan pekerjaan)
     * @param authorIncomeDetail Detail pendapatan
     * */
    void saveDailyIncomeSta(AuthorIncomeDetail authorIncomeDetail);



    /**
     * Tanyakan apakah statistik pendapatan bulanan ada di database
     * @param bookId ID Pekerjaan
     * @param incomeDate Waktu penghasilan
     * @return true: telah disimpan, false: tidak disimpan
     * */
    boolean queryIsStatisticsMonth(Long bookId, Date incomeDate);

    /**
     * Kueri jumlah total langganan selama periode waktu tersebut
     *
     * @param userId
     * @param bookId ID Pekerjaan
     * @param startTime Waktu mulai
     * @param endTime Akhir waktu
     * @return Jumlah langganan (Koin)
     * */
    Long queryTotalAccount(Long userId, Long bookId, Date startTime, Date endTime);


    /**
     * Simpan statistik pendapatan bulanan
     * @param authorIncome Detail pendapatan
     * */
    void saveAuthorIncomeSta(AuthorIncome authorIncome);

    /**
     * Tanyakan apakah statistik hari pendapatan ada di database
     * @param authorId ID Penulis
     * @param bookId ID Pekerjaan
     * @param date Waktu penghasilan
     * @return true: telah disimpan, false: tidak disimpan
     */
    boolean queryIsStatisticsDaily(Long authorId, Long bookId, Date date);

    /**
     *Statistik pendapatan harian penulis paged list query
     * @param userId
     * @param page nomor halaman
     * @param pageSize Ukuran Paging
     * @param bookId ID Novel
     * @param startTime Waktu mulai
     * @param endTime Akhir waktu
     * @return Data pagination statistik pendapatan harian
     */
    PageBean<AuthorIncomeDetail> listIncomeDailyByPage(int page, int pageSize, Long userId, Long bookId, Date startTime, Date endTime);


    /**
     * Kueri daftar halaman statistik pendapatan bulanan penulis
     * @param page nomor halaman
     * @param pageSize Ukuran Paging
     * @param userId ID pengguna
     * @param bookId ID Novel
     * @return Data paging
     * */
    PageBean<AuthorIncome> listIncomeMonthByPage(int page, int pageSize, Long userId, Long bookId);
}
