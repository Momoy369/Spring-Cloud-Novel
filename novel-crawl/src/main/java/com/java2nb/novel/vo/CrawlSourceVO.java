package com.java2nb.novel.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.java2nb.novel.entity.CrawlSource;
import lombok.Data;

import javax.annotation.Generated;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class CrawlSourceVO extends CrawlSource{

    @JsonFormat(timezone = "GMT+7", pattern = "dd-MM-yyyy HH:mm")
    private Date createTime;

    @JsonFormat(timezone = "GMT+7", pattern = "dd-MM-yyyy HH:mm")
    private Date updateTime;



    @Override
    public String toString() {
        return super.toString();
    }
}
