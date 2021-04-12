package com.java2nb.novel.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * properti konfigurasi kolam thread
 * @author xiongxiaoyang
 */
@Data
@Component
@ConfigurationProperties(prefix = "thread.pool")
public class ThreadPoolProperties {

    private Integer corePoolSize;
    private Integer maximumPoolSize;
    private Long keepAliveTime;
    private Integer queueSize;


}
