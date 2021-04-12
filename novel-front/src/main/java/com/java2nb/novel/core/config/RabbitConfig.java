package com.java2nb.novel.core.config;

import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 11797
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.rabbitmq", name = "enable", havingValue = "1")
public class RabbitConfig {


    /**
     * Perbarui antrean database
     */
    @Bean
    public Queue updateDbQueue() {
        return new Queue("UPDATE-DB-QUEUE", true);
    }

    /**
     * Perbarui antrean database
     */
    @Bean
    public Queue updateEsQueue() {
        return new Queue("UPDATE-ES-QUEUE", true);
    }


    /**
     * Tingkatkan sakelar lalu lintas
     */
    @Bean
    public FanoutExchange addVisitExchange() {
        return new FanoutExchange("ADD-BOOK-VISIT-EXCHANGE");
    }

    /**
     * Perbarui pengikatan antrian mesin pencari untuk meningkatkan pertukaran lalu lintas
     */
    @Bean
    public Binding updateEsBinding() {

        return BindingBuilder.bind(updateEsQueue()).to(addVisitExchange());
    }

    /**
     * Memperbarui database terikat pada sakelar untuk meningkatkan lalu lintas
     */
    @Bean
    public Binding updateDbBinding() {
        return BindingBuilder.bind(updateDbQueue()).to(addVisitExchange());
    }


}
