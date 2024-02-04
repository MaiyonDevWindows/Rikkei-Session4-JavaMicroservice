package com.maiyon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@SpringBootApplication
public class DemoKafkaProducer {
    public static void main(String[] args) {
        SpringApplication.run(DemoKafkaProducer.class, args);
    }
    @Bean
    JsonMessageConverter messageConverter(){
        return new JsonMessageConverter();
    }
}
