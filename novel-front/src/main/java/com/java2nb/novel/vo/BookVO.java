package com.java2nb.novel.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.java2nb.novel.entity.Book;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class BookVO extends Book implements Serializable {

    @JsonFormat(timezone = "GMT+7", pattern = "dd/MM HH:mm")
    private Date lastIndexUpdateTime;


}
