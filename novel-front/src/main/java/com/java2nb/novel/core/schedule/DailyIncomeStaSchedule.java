package com.java2nb.novel.core.schedule;


import com.java2nb.novel.core.utils.DateUtil;
import com.java2nb.novel.entity.Author;
import com.java2nb.novel.entity.AuthorIncomeDetail;
import com.java2nb.novel.entity.Book;
import com.java2nb.novel.service.AuthorService;
import com.java2nb.novel.service.BookService;
import com.java2nb.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Tugas statistik pendapatan harian penulis
 *
 * @author cd
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DailyIncomeStaSchedule {

    private final AuthorService authorService;

    private final UserService userService;

    private final BookService bookService;


    /**
     * Statistik data hari sebelumnya pukul 0 pagi setiap hari
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void statistics() {

        //Dapatkan tanggal dan waktu kemarin
        Date yesterday = DateUtil.getYesterday();
        //Dapatkan waktu mulai kemarin
        Date startTime = DateUtil.getDateStartTime(yesterday);
        //Dapatkan waktu akhir kemarin
        Date endTime = DateUtil.getDateEndTime(yesterday);

        //Jumlah penulis per kueri
        int needAuthorNumber = 10;
        //Jumlah penulis sungguhan yang ditanyakan
        int realAuthorNumber;
        //Waktu aplikasi maksimum per kueri
        Date maxAuthorCreateTime = new Date();
        do {
            //1. Menanyakan daftar penulis
            List<Author> authors = authorService.queryAuthorList(needAuthorNumber, maxAuthorCreateTime);
            realAuthorNumber = authors.size();
            for (Author author : authors) {
                maxAuthorCreateTime = author.getCreateTime();
                Long authorId = author.getId();
                Long userId = author.getUserId();
                //2. Bertanya tentang karya penulis
                List<Book> books = bookService.queryBookList(authorId);

                int buyTotalMember = 0;
                int buyTotalCount = 0;
                int buyTotalAccount = 0;
                List<Long> bookIds = new ArrayList<>(books.size());
                for (Book book : books) {

                    Long bookId = book.getId();

                    //3. Menanyakan jumlah pelanggan karya penulis kemarin
                    int buyMember = userService.queryBuyMember(bookId, startTime, endTime);

                    int buyCount = 0;

                    int buyAccount = 0;


                    if (buyMember > 0) {
                        //4. Tanyakan jumlah langganan karya penulis kemarin
                        buyCount = userService.queryBuyCount(bookId, startTime, endTime);
                        //5. Tanyakan jumlah total langganan untuk karya penulis kemarin
                        buyAccount = userService.queryBuyAccount(bookId, startTime, endTime);
                    }

                    //6. Menilai apakah data pendapatan karya penulis kemarin secara statistik disimpan di database
                    boolean isStatistics = authorService.queryIsStatisticsDaily(bookId, yesterday);
                    if (!isStatistics) {
                        //7. Data pendapatan karya penulis kemarin belum dihitung ke dalam database, dan data statistik karya tersebut dimasukkan ke dalam database
                        AuthorIncomeDetail authorIncomeDetail = new AuthorIncomeDetail();
                        authorIncomeDetail.setAuthorId(authorId);
                        authorIncomeDetail.setUserId(userId);
                        authorIncomeDetail.setBookId(bookId);
                        authorIncomeDetail.setIncomeDate(yesterday);
                        authorIncomeDetail.setIncomeNumber(buyMember);
                        authorIncomeDetail.setIncomeCount(buyCount);
                        authorIncomeDetail.setIncomeAccount(buyAccount);
                        authorIncomeDetail.setCreateTime(new Date());
                        authorService.saveDailyIncomeSta(authorIncomeDetail);
                    }


                    buyTotalCount += buyCount;
                    buyTotalAccount += buyAccount;
                    bookIds.add(bookId);

                }

                //8. Tentukan apakah data pendapatan dari semua karya penulis kemarin disimpan secara statistik di database
                boolean isStatistics = authorService.queryIsStatisticsDaily(authorId,0L, yesterday);
                if (!isStatistics) {
                    if (buyTotalCount > 0) {
                        //Jika total jumlah langganan lebih dari 0, jumlah pelanggan juga lebih dari 0
                        buyTotalMember = userService.queryBuyTotalMember(bookIds, startTime, endTime);
                    }

                    //9. Data statistik pendapatan dari semua karya penulis kemarin dimasukkan ke dalam database
                    AuthorIncomeDetail authorIncomeAllDetail = new AuthorIncomeDetail();
                    authorIncomeAllDetail.setAuthorId(authorId);
                    authorIncomeAllDetail.setUserId(userId);
                    authorIncomeAllDetail.setBookId(0L);
                    authorIncomeAllDetail.setIncomeDate(yesterday);
                    authorIncomeAllDetail.setIncomeNumber(buyTotalMember);
                    authorIncomeAllDetail.setIncomeCount(buyTotalCount);
                    authorIncomeAllDetail.setIncomeAccount(buyTotalAccount);
                    authorIncomeAllDetail.setCreateTime(new Date());
                    authorService.saveDailyIncomeSta(authorIncomeAllDetail);
                }

            }

        } while (needAuthorNumber == realAuthorNumber);


    }

}
