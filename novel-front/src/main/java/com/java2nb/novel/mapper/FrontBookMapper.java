package com.java2nb.novel.mapper;

import com.java2nb.novel.entity.Book;
import com.java2nb.novel.vo.BookSpVO;
import com.java2nb.novel.vo.BookVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 */
public interface FrontBookMapper extends BookMapper {


    List<BookVO> searchByPage(BookSpVO params);

    void addVisitCount(@Param("bookId") Long bookId, @Param("visitCount") Integer visitCount);

    List<Book> listRecBookByCatId(@Param("catId") Integer catId);

    void addCommentCount(@Param("bookId") Long bookId);

    List<Book> queryNetworkPicBooks(@Param("localPicPrefix") String localPicPrefix, @Param("limit") Integer limit);

    /**
     * Koleksi kueri acak novel menurut peringkat
     * @param limit Jumlah kueri
     * @return Koleksi fiksi
     * */
    List<Book> selectIdsByScoreAndRandom(@Param("limit") int limit);
}
