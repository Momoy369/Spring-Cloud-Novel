package com.java2nb.novel.core.schedule;


import com.java2nb.novel.core.config.AuthorIncomeProperties;
import com.java2nb.novel.core.utils.DateUtil;
import com.java2nb.novel.entity.*;
import com.java2nb.novel.service.AuthorService;
import com.java2nb.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
public class MonthIncomeStaSchedule {

    private final AuthorService authorService;

    private final BookService bookService;

    private final AuthorIncomeProperties authorIncomeConfig;

    /**
     * Statistik data bulan lalu pada pukul 2 pagi pada tanggal 1 setiap bulan
     */
    @Scheduled(cron = "0 0 2 1 * ?")
    public void statistics() {

        //Dapatkan waktu mulai dan waktu akhir bulan sebelumnya
        Date startTime = DateUtil.getLastMonthStartTime();
        Date endTime = DateUtil.getLastMonthEndTime();

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

                Long totalPreTaxIncome = 0L;
                Long totalAfterTaxIncome = 0L;
                for (Book book : books) {

                    Long bookId = book.getId();


                    //3. Data pendapatan bulanan tidak dihitung dan disimpan di database, dan data statistik pekerjaan disimpan di database
                    Long monthIncome = authorService.queryTotalAccount(userId, bookId, startTime, endTime);

                    BigDecimal monthIncomeShare = new BigDecimal(monthIncome)
                            .multiply(authorIncomeConfig.getShareProportion());
                    Long preTaxIncome = monthIncomeShare
                            .multiply(authorIncomeConfig.getExchangeProportion())
                            .multiply(new BigDecimal(100))
                            .longValue();

                    totalPreTaxIncome += preTaxIncome;

                    Long afterTaxIncome = monthIncomeShare
                            .multiply(authorIncomeConfig.getTaxRate())
                            .multiply(authorIncomeConfig.getExchangeProportion())
                            .multiply(new BigDecimal(100))
                            .longValue();

                    totalAfterTaxIncome += afterTaxIncome;

                    //4. Tanyakan apakah statistik pendapatan bulanan ada di database
                    if (monthIncome > 0 && !authorService.queryIsStatisticsMonth(bookId, endTime)) {
                        AuthorIncome authorIncome = new AuthorIncome();
                        authorIncome.setAuthorId(authorId);
                        authorIncome.setUserId(userId);
                        authorIncome.setBookId(bookId);
                        authorIncome.setPreTaxIncome(preTaxIncome);
                        authorIncome.setAfterTaxIncome(afterTaxIncome);
                        authorIncome.setIncomeMonth(endTime);
                        authorIncome.setCreateTime(new Date());

                        authorService.saveAuthorIncomeSta(authorIncome);
                    }


                }

                if (totalPreTaxIncome > 0 && !authorService.queryIsStatisticsMonth(0L, endTime)) {

                    AuthorIncome authorIncome = new AuthorIncome();
                    authorIncome.setAuthorId(authorId);
                    authorIncome.setUserId(userId);
                    authorIncome.setBookId(0L);
                    authorIncome.setPreTaxIncome(totalPreTaxIncome);
                    authorIncome.setAfterTaxIncome(totalAfterTaxIncome);
                    authorIncome.setIncomeMonth(endTime);
                    authorIncome.setCreateTime(new Date());

                    authorService.saveAuthorIncomeSta(authorIncome);
                }


            }

        } while (needAuthorNumber == realAuthorNumber);


    }

}
