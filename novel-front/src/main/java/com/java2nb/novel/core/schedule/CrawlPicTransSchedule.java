package com.java2nb.novel.core.schedule;

import com.java2nb.novel.core.utils.Constants;
import com.java2nb.novel.entity.Book;
import com.java2nb.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Transfer gambar jaringan yang dirayapi ke tugas media penyimpanan Anda sendiri (lokal, OSS, fastDfs)
 *
 * @author Administrator
 */
@ConditionalOnProperty(prefix = "pic.save", name = "type", havingValue = "2")
@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlPicTransSchedule {

    private final BookService bookService;

    @Value("${pic.save.type}")
    private Integer picSaveType;

    @Value("${pic.save.path}")
    private String picSavePath;

    /**
     * Setiap 10 menit
     */
    @Scheduled(fixedRate = 1000 * 60 * 10)
    @SneakyThrows
    public void trans() {

        log.info("Network2LocalPicSchedule。。。。。。。。。。。。");


        List<Book> networkPicBooks = bookService.queryNetworkPicBooks(Constants.LOCAL_PIC_PREFIX,100);
        for (Book book : networkPicBooks) {
            bookService.updateBookPicToLocal(book.getPicUrl(), book.getId());
            //Ubah gambar dalam 3 detik, dan ubah 200 gambar dalam 10 menit
            Thread.sleep(3000);
        }


    }
}
