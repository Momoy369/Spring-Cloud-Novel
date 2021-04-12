package com.java2nb.novel.core.listener;

import com.java2nb.novel.core.cache.CacheKey;
import com.java2nb.novel.core.cache.CacheService;
import com.java2nb.novel.core.utils.Constants;
import com.java2nb.novel.entity.Book;
import com.java2nb.novel.service.BookService;
import com.java2nb.novel.service.SearchService;
import com.java2nb.novel.vo.EsBookVO;
import com.rabbitmq.client.Channel;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


import java.text.SimpleDateFormat;


/**
 * @author 11797
 */
@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.rabbitmq", name = "enable", havingValue = "1")
public class BookVisitAddListener {

    private final BookService bookService;

    private final CacheService cacheService;

    private final SearchService searchService;

   // private final RedissonClient redissonClient;




    /**
     * Perbarui database
     * Puncak lalu lintas, setiap novel mengumpulkan 10 klik untuk memperbarui sekali
     */
    @SneakyThrows
    @RabbitListener(queues = {"UPDATE-DB-QUEUE"})
    public void updateDb(Long bookId, Channel channel, Message message) {

        log.debug("收到更新数据库消息：" + bookId);
        Thread.sleep(1000 * 2);
        //TODO Mengoperasikan visitCount resource bersama, ada masalah keamanan thread di lingkungan cluster, dan framework Redisson diperkenalkan untuk mengimplementasikan kunci terdistribusi
        //RLock lock = redissonClient.getLock("visitCount");
        //lock.lock();


        //Saat ini visitCount tidak penting, data bisa hilang, dan kunci terdistribusi tidak diterapkan sementara
        Integer visitCount = (Integer) cacheService.getObject(CacheKey.BOOK_ADD_VISIT_COUNT+bookId);
        if(visitCount == null){
            visitCount = 0 ;
        }
        cacheService.setObject(CacheKey.BOOK_ADD_VISIT_COUNT+bookId,++visitCount);
        if(visitCount >= Constants.ADD_MAX_VISIT_COUNT) {
            bookService.addVisitCount(bookId,visitCount);
            cacheService.del(CacheKey.BOOK_ADD_VISIT_COUNT+bookId);
        }

        //TODO Mengoperasikan visitCount resource bersama, ada masalah keamanan thread di lingkungan cluster, dan framework Redisson diperkenalkan untuk mengimplementasikan kunci terdistribusi
        //lock.unlock();



    }

    /**
     * Perbarui mesin telusur
     * Lalu lintas memuncak, diperbarui setiap 1 jam untuk setiap novel
     */
    @RabbitListener(queues = {"UPDATE-ES-QUEUE"})
    public void updateEs(Long bookId, Channel channel, Message message) {

        log.debug("Terima pesan pembaruan mesin telusur: " + bookId);
        if (cacheService.get(CacheKey.ES_IS_UPDATE_VISIT + bookId) == null) {
            cacheService.set(CacheKey.ES_IS_UPDATE_VISIT + bookId, "1", 60 * 60);
            try {
                Thread.sleep(1000 * 5);
                Book book = bookService.queryBookDetail(bookId);
                searchService.importToEs(book);
            }catch (Exception e){
                cacheService.del(CacheKey.ES_IS_UPDATE_VISIT + bookId);
                log.error("Gagal memperbarui mesin telusur"+bookId);
            }




        }


    }


}
