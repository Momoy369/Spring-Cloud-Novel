package com.java2nb.novel.core.crawl;

import com.java2nb.novel.core.utils.*;
import com.java2nb.novel.entity.Book;
import com.java2nb.novel.entity.BookContent;
import com.java2nb.novel.entity.BookIndex;
import com.java2nb.novel.utils.Constants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * Pengurai perayap
 *
 * @author Administrator
 */
@Slf4j
public class CrawlParser {

    private static IdWorker idWorker = new IdWorker();

    public static final Integer BOOK_INDEX_LIST_KEY = 1;

    public static final Integer BOOK_CONTENT_LIST_KEY = 2;

    private static RestTemplate restTemplate = RestTemplateUtil.getInstance("utf-8");

    private static ThreadLocal<Integer> retryCount = new ThreadLocal<>();

    @SneakyThrows
    public static Book parseBook(RuleBean ruleBean, String bookId) {
        Book book = new Book();
        String bookDetailUrl = ruleBean.getBookDetailUrl().replace("{bookId}", bookId);
        String bookDetailHtml = getByHttpClientWithChrome(bookDetailUrl);
        if (bookDetailHtml != null) {
            Pattern bookNamePatten = compile(ruleBean.getBookNamePatten());
            Matcher bookNameMatch = bookNamePatten.matcher(bookDetailHtml);
            boolean isFindBookName = bookNameMatch.find();
            if (isFindBookName) {
                String bookName = bookNameMatch.group(1);
                //Tetapkan nama baru
                book.setBookName(bookName);
                Pattern authorNamePatten = compile(ruleBean.getAuthorNamePatten());
                Matcher authorNameMatch = authorNamePatten.matcher(bookDetailHtml);
                boolean isFindAuthorName = authorNameMatch.find();
                if (isFindAuthorName) {
                    String authorName = authorNameMatch.group(1);
                    //Tetapkan nama penulis
                    book.setAuthorName(authorName);
                    if (StringUtils.isNotBlank(ruleBean.getPicUrlPatten())) {
                        Pattern picUrlPatten = compile(ruleBean.getPicUrlPatten());
                        Matcher picUrlMatch = picUrlPatten.matcher(bookDetailHtml);
                        boolean isFindPicUrl = picUrlMatch.find();
                        if (isFindPicUrl) {
                            String picUrl = picUrlMatch.group(1);
                            if (StringUtils.isNotBlank(picUrl) && StringUtils.isNotBlank(ruleBean.getPicUrlPrefix())) {
                                picUrl = ruleBean.getPicUrlPrefix() + picUrl;
                            }
                            //Atur jalur gambar sampul
                            book.setPicUrl(picUrl);
                        }
                    }
                    if (StringUtils.isNotBlank(ruleBean.getScorePatten())) {
                        Pattern scorePatten = compile(ruleBean.getScorePatten());
                        Matcher scoreMatch = scorePatten.matcher(bookDetailHtml);
                        boolean isFindScore = scoreMatch.find();
                        if (isFindScore) {
                            String score = scoreMatch.group(1);
                            //Atur skor
                            book.setScore(Float.parseFloat(score));
                        }
                    }
                    if (StringUtils.isNotBlank(ruleBean.getVisitCountPatten())) {
                        Pattern visitCountPatten = compile(ruleBean.getVisitCountPatten());
                        Matcher visitCountMatch = visitCountPatten.matcher(bookDetailHtml);
                        boolean isFindVisitCount = visitCountMatch.find();
                        if (isFindVisitCount) {
                            String visitCount = visitCountMatch.group(1);
                            //Tetapkan jumlah kunjungan
                            book.setVisitCount(Long.parseLong(visitCount));
                        }
                    }

                    String desc = bookDetailHtml.substring(bookDetailHtml.indexOf(ruleBean.getDescStart()) + ruleBean.getDescStart().length());
                    desc = desc.substring(0, desc.indexOf(ruleBean.getDescEnd()));
                    //Filter tag khusus dalam pendahuluan
                    desc = desc.replaceAll("<a[^<]+</a>", "")
                            .replaceAll("<font[^<]+</font>", "")
                            .replaceAll("<p>\\s*</p>", "")
                            .replaceAll("<p>", "")
                            .replaceAll("</p>", "<br/>");
                    //Siapkan pengenalan buku
                    book.setBookDesc(desc);
                    if (StringUtils.isNotBlank(ruleBean.getStatusPatten())) {
                        Pattern bookStatusPatten = compile(ruleBean.getStatusPatten());
                        Matcher bookStatusMatch = bookStatusPatten.matcher(bookDetailHtml);
                        boolean isFindBookStatus = bookStatusMatch.find();
                        if (isFindBookStatus) {
                            String bookStatus = bookStatusMatch.group(1);
                            if (ruleBean.getBookStatusRule().get(bookStatus) != null) {
                                //Setel status pembaruan
                                book.setBookStatus(ruleBean.getBookStatusRule().get(bookStatus));
                            }
                        }
                    }

                    if (StringUtils.isNotBlank(ruleBean.getUpadateTimePatten()) && StringUtils.isNotBlank(ruleBean.getUpadateTimeFormatPatten())) {
                        Pattern updateTimePatten = compile(ruleBean.getUpadateTimePatten());
                        Matcher updateTimeMatch = updateTimePatten.matcher(bookDetailHtml);
                        boolean isFindUpdateTime = updateTimeMatch.find();
                        if (isFindUpdateTime) {
                            String updateTime = updateTimeMatch.group(1);
                            //Atur waktu pembaruan
                            book.setLastIndexUpdateTime(new SimpleDateFormat(ruleBean.getUpadateTimeFormatPatten()).parse(updateTime));

                        }
                    }

                }
                if (book.getVisitCount() == null && book.getScore() != null) {
                    //Menghasilkan kunjungan secara acak berdasarkan skor
                    book.setVisitCount(RandomBookInfoUtil.getVisitCountByScore(book.getScore()));
                } else if (book.getVisitCount() != null && book.getScore() == null) {
                    //Hasilkan skor secara acak berdasarkan jumlah kunjungan
                    book.setScore(RandomBookInfoUtil.getScoreByVisitCount(book.getVisitCount()));
                } else if (book.getVisitCount() == null && book.getScore() == null) {
                    //Tidak ada, setel ke nilai tetap
                    book.setVisitCount(Constants.VISIT_COUNT_DEFAULT);
                    book.setScore(6.5f);
                }
            }
        }
        return book;
    }

    public static Map<Integer, List> parseBookIndexAndContent(String sourceBookId, Book book, RuleBean ruleBean, Map<Integer, BookIndex> hasIndexs) {
        Map<Integer, List> result = new HashMap<>(2);
        result.put(BOOK_INDEX_LIST_KEY, new ArrayList(0));
        result.put(BOOK_CONTENT_LIST_KEY, new ArrayList(0));

        Date currentDate = new Date();

        List<BookIndex> indexList = new ArrayList<>();
        List<BookContent> contentList = new ArrayList<>();
        //Baca direktori
        String indexListUrl = ruleBean.getBookIndexUrl().replace("{bookId}", sourceBookId);
        String indexListHtml = getByHttpClientWithChrome(indexListUrl);

        if (indexListHtml != null) {
            if (StringUtils.isNotBlank(ruleBean.getBookIndexStart())) {
                indexListHtml = indexListHtml.substring(indexListHtml.indexOf(ruleBean.getBookIndexStart()) + ruleBean.getBookIndexStart().length());
            }

            Pattern indexIdPatten = compile(ruleBean.getIndexIdPatten());
            Matcher indexIdMatch = indexIdPatten.matcher(indexListHtml);

            Pattern indexNamePatten = compile(ruleBean.getIndexNamePatten());
            Matcher indexNameMatch = indexNamePatten.matcher(indexListHtml);

            boolean isFindIndex = indexIdMatch.find() & indexNameMatch.find();

            int indexNum = 0;

            //Total kata
            Integer totalWordCount = book.getWordCount() == null ? 0 : book.getWordCount();

            while (isFindIndex) {

                BookIndex hasIndex = hasIndexs.get(indexNum);
                String indexName = indexNameMatch.group(1);

                if (hasIndex == null || !StringUtils.deleteWhitespace(hasIndex.getIndexName()).equals(StringUtils.deleteWhitespace(indexName))) {

                    String sourceIndexId = indexIdMatch.group(1);
                    String bookContentUrl = ruleBean.getBookContentUrl();
                    int calStart = bookContentUrl.indexOf("{cal_");
                    if (calStart != -1) {
                        //URL halaman konten perlu dihitung untuk mendapatkan
                        String calStr = bookContentUrl.substring(calStart, calStart + bookContentUrl.substring(calStart).indexOf("}"));
                        String[] calArr = calStr.split("_");
                        int calType = Integer.parseInt(calArr[1]);
                        if (calType == 1) {
                            ///{cal_1_1_3}_{bookId}/{indexId}.html
                            //Aturan perhitungan pertama, hapus huruf y terakhir dari parameter ke-x
                            int x = Integer.parseInt(calArr[2]);
                            int y = Integer.parseInt(calArr[3]);
                            String calResult;
                            if (x == 1) {
                                calResult = sourceBookId.substring(0, sourceBookId.length() - y);
                            } else {
                                calResult = sourceIndexId.substring(0, sourceBookId.length() - y);
                            }

                            if (calResult.length() == 0) {
                                calResult = "0";

                            }

                            bookContentUrl = bookContentUrl.replace(calStr + "}", calResult);
                        }

                    }

                    String contentUrl = bookContentUrl.replace("{bookId}", sourceBookId).replace("{indexId}", sourceIndexId);

                    //Isi bab kueri
                    String contentHtml = getByHttpClientWithChrome(contentUrl);
                    if (contentHtml != null && !contentHtml.contains("正在手打中")) {
                        String content = contentHtml.substring(contentHtml.indexOf(ruleBean.getContentStart()) + ruleBean.getContentStart().length());
                        content = content.substring(0, content.indexOf(ruleBean.getContentEnd()));
                        //Masukkan daftar isi bab dan isi bab
                        BookIndex bookIndex = new BookIndex();
                        bookIndex.setIndexName(indexName);
                        bookIndex.setIndexNum(indexNum);
                        Integer wordCount = StringUtil.getStrValidWordCount(content);
                        bookIndex.setWordCount(wordCount);
                        indexList.add(bookIndex);

                        BookContent bookContent = new BookContent();
                        bookContent.setContent(content);
                        contentList.add(bookContent);

                        if (hasIndex != null) {
                            //Pembaruan bab
                            bookIndex.setId(hasIndex.getId());
                            bookContent.setIndexId(hasIndex.getId());

                            //Hitung jumlah kata
                            totalWordCount = (totalWordCount+wordCount-hasIndex.getWordCount());
                        } else {
                            //Bab Sisipan
                            //Atur daftar isi dan isi bab
                            Long indexId = idWorker.nextId();
                            bookIndex.setId(indexId);
                            bookIndex.setBookId(book.getId());

                            bookIndex.setCreateTime(currentDate);

                            bookContent.setIndexId(indexId);

                            //Hitung jumlah kata
                            totalWordCount += wordCount;
                        }
                        bookIndex.setUpdateTime(currentDate);



                    }


                }
                indexNum++;
                isFindIndex = indexIdMatch.find() & indexNameMatch.find();
            }


            if (indexList.size() > 0) {
                //Jika Anda telah naik ke chapter terbaru, atur informasi chapter terbaru dari tabel utama novel
                //Dapatkan bab terbaru dirayapi
                BookIndex lastIndex = indexList.get(indexList.size()-1);
                book.setLastIndexId(lastIndex.getId());
                book.setLastIndexName(lastIndex.getIndexName());
                book.setLastIndexUpdateTime(currentDate);

            }
            book.setWordCount(totalWordCount);
            book.setUpdateTime(currentDate);

            if (indexList.size() == contentList.size() && indexList.size() > 0) {

                result.put(BOOK_INDEX_LIST_KEY, indexList);
                result.put(BOOK_CONTENT_LIST_KEY, contentList);

            }

        }


        return result;
    }


    private static String getByHttpClient(String url) {
        try {
            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
            if (forEntity.getStatusCode() == HttpStatus.OK) {
                String body = forEntity.getBody();
                if (body.length() < Constants.INVALID_HTML_LENGTH) {
                    return processErrorHttpResult(url);
                }
                //Berhasil mendapatkan konten html
                return body;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processErrorHttpResult(url);

    }

    private static String getByHttpClientWithChrome(String url) {
        try {

            String body = HttpUtil.getByHttpClientWithChrome(url);
            if (body != null && body.length() < Constants.INVALID_HTML_LENGTH) {
                return processErrorHttpResult(url);
            }
            //Berhasil mendapatkan konten html
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processErrorHttpResult(url);

    }

    @SneakyThrows
    private static String processErrorHttpResult(String url) {
        Integer count = retryCount.get();
        if (count == null) {
            count = 0;
        }
        if (count < Constants.HTTP_FAIL_RETRY_COUNT) {
            Thread.sleep(new Random().nextInt(10 * 1000));
            retryCount.set(++count);
            return getByHttpClient(url);
        }
        return null;
    }


}
