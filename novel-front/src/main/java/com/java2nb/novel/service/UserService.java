package com.java2nb.novel.service;


import com.java2nb.novel.core.bean.PageBean;
import com.java2nb.novel.core.bean.UserDetails;
import com.java2nb.novel.entity.UserBuyRecord;
import com.java2nb.novel.entity.UserFeedback;
import com.java2nb.novel.vo.BookReadHistoryVO;
import com.java2nb.novel.vo.BookShelfVO;
import com.java2nb.novel.entity.User;
import com.java2nb.novel.vo.UserFeedbackVO;

import java.util.Date;
import java.util.List;

/**
 * @author 11797
 */
public interface UserService {

    /**
     * Pendaftaran pengguna
     * @param user Informasi pendaftaran pengguna
     * @return informasi pembawa jwt
     * */
    UserDetails register(User user);

    /**
     * login pengguna
     * @param user Informasi login pengguna
     * @return informasi pembawa jwt
     * */
    UserDetails login(User user);

    /**
     * Periksa apakah novel telah ditambahkan ke rak buku
     * @param userId ID Pengguna
     * @param bookId ID Novel
     * @return true: ditambahkan ke rak buku, tidak ditambahkan ke rak buku
     * */
    Boolean queryIsInShelf(Long userId, Long bookId);

    /**
     * Tambahkan ke rak buku
     * @param userId ID Pengguna
     * @param bookId ID Novel
     * @param preContentId Membaca ID konten
     * */
    void addToBookShelf(Long userId, Long bookId, Long preContentId);

    /**
     * Keluar dari rak buku
     * @param userId ID Pengguna
     * @param bookId ID Novel
     * */
    void removeFromBookShelf(Long userId, Long bookId);

    /**
     * Rak buku kueri
     * @param userId ID Pengguna
     * @param page
     * @param pageSize
     * @return Informasi pagination rak buku
     * */
    PageBean<BookShelfVO> listBookShelfByPage(Long userId, int page, int pageSize);

    /**
     * Tambahkan catatan bacaan
     * @param userId ID pengguna
     * @param bookId ID buku
     * @param preContentId ID direktori yang akan dibaca
     * */
    void addReadHistory(Long userId, Long bookId, Long preContentId);

    /**
     * Tambahkan umpan balik
     * @param userId ID pengguna
     * @param content Konten umpan balik
     * */
    void addFeedBack(Long userId, String content);

    /**
     * Kueri daftar umpan balik saya per halaman
     * @param userId ID pengguna
     * @param page nomor halaman
     * @param pageSize Ukuran Paging
     * @return Umpan balik data pagination
     * */
    PageBean<UserFeedback> listUserFeedBackByPage(Long userId, int page, int pageSize);

    /**
     * Menanyakan informasi pribadi
     * @param userId ID pengguna
     * @return Info Pengguna
     * */
    User userInfo(Long userId);

    /**
     * Paging query membaca catatan
     * @param userId ID pengguna
     * @param page nomor halaman
     * @param pageSize Ukuran Paging
     * @return Data paging
     * */
    PageBean<BookReadHistoryVO> listReadHistoryByPage(Long userId, int page, int pageSize);

    /**
     * Perbarui informasi pribadi
     * @param userId ID pengguna
     * @param user Informasi yang perlu diperbarui
     * */
    void updateUserInfo(Long userId, User user);

    /**
     * Perbarui kata sandi
     * @param userId ID pengguna
     * @param oldPassword password lama
     * @param newPassword kata sandi baru
     * */
    void updatePassword(Long userId, String oldPassword, String newPassword);


    /**
     * Tingkatkan saldo pengguna
     * @param userId ID pengguna
     * @param amount Penambahan saldo */
    void addAmount(Long userId, int amount);

    /**
     * Tentukan apakah pengguna telah membeli bab dari novel
     * @param userId ID pengguna
     * @param bookIndexId ID Direktori Bab
     * @return benar: dibeli, salah: tidak dibeli
     * */
    boolean queryIsBuyBookIndex(Long userId, Long bookIndexId);

    /**
     * Beli bab baru
     * @param userId ID pengguna
     * @param buyRecord Informasi pembelian
     * */
    void buyBookIndex(Long userId, UserBuyRecord buyRecord);

    /**
     * Kueri jumlah pelanggan dalam jangka waktu pekerjaan
     * @param bookId ID Pekerjaan
     * @param startTime Waktu mulai
     * @param endTime Akhir waktu
     * @return Jumlah pelanggan
     */
    int queryBuyMember(Long bookId, Date startTime, Date endTime);

    /**
     * Kueri jumlah langganan dalam jangka waktu pekerjaan
     * @param bookId ID Pekerjaan
     * @param startTime Waktu mulai
     * @param endTime Akhir waktu
     * @return Jumlah langganan
     */
    int queryBuyCount(Long bookId, Date startTime, Date endTime);

    /**
     * Kueri jumlah total langganan dalam periode waktu pekerjaan (mata uang rumah)
     * @param bookId ID Pekerjaan
     * @param startTime Waktu mulai
     * @param endTime Akhir waktu
     * @return Total langganan (mata uang rumah)
     */
    int queryBuyAccount(Long bookId, Date startTime, Date endTime);

    /**
     * Kueri jumlah pelanggan dalam periode waktu penulis
     * @param bookIds z Semua ID kerja penulis
     * @param startTime Waktu mulai
     * @param endTime Akhir waktu
     * @return Jumlah pelanggan
     */
    int queryBuyTotalMember(List<Long> bookIds, Date startTime, Date endTime);
}
