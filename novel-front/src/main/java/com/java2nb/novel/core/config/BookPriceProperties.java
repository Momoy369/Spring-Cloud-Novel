package com.java2nb.novel.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Atribut konfigurasi biaya bab
 * @author cd
 */
@Component
@Data
@ConfigurationProperties(prefix = "book.price")
public class BookPriceProperties {

    private BigDecimal wordCount;

    private BigDecimal value;


}
