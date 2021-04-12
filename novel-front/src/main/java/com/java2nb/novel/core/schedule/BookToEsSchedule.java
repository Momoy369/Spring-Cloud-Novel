package com.java2nb.novel.core.schedule;

import com.java2nb.novel.core.cache.CacheKey;
import com.java2nb.novel.core.cache.CacheService;
import com.java2nb.novel.entity.Book;
import com.java2nb.novel.service.BookService;
import com.java2nb.novel.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Mesin pencari impor baru
 *
 * @author Administrator
 */
@ConditionalOnProperty(prefix = "spring.elasticsearch", name = "enable", havingValue = "1")
@Service
@RequiredArgsConstructor
@Slf4j
public class BookToEsSchedule {

    private final BookService bookService;

    private final CacheService cacheService;



    private final SearchService searchService;


    /**
     * Impor setiap 1 menit
     */
    @Scheduled(fixedRate = 1000 * 60)
    public void saveToEs() {
        //TODO Memperkenalkan framework Redisson untuk mengimplementasikan kunci terdistribusi
        //Ini dapat diperbarui berulang kali, tetapi efisiensinya mungkin sedikit berkurang, sehingga kunci terdistribusi tidak diterapkan untuk sementara
        if (cacheService.get(CacheKey.ES_TRANS_LOCK) == null) {
            cacheService.set(CacheKey.ES_TRANS_LOCK, "1", 60 * 20);
            try {
                //Temukan novel yang perlu diperbarui
                Date lastDate = (Date) cacheService.getObject(CacheKey.ES_LAST_UPDATE_TIME);
                if (lastDate == null) {
                    lastDate = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2020");
                }


                List<Book> books = bookService.queryBookByUpdateTimeByPage(lastDate, 100);
                for (Book book : books) {
                    searchService.importToEs(book);
                    lastDate = book.getUpdateTime();
                    Thread.sleep(5000);

                }

                cacheService.setObject(CacheKey.ES_LAST_UPDATE_TIME, lastDate);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            cacheService.del(CacheKey.ES_TRANS_LOCK);


        }


    }


}
