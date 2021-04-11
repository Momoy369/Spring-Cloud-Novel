package com.java2nb.novel.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.java2nb.novel.entity.News;
import lombok.Data;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class NewsVO extends News {

    @JsonFormat(timezone = "GMT+7", pattern = "dd-MM-yyyy")
    private Date createTime;
}
