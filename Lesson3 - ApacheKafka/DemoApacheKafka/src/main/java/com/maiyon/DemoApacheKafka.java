package com.maiyon;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApacheKafka {

    public static void main(String[] args) {
        SpringApplication.run(DemoApacheKafka.class, args);
    }
    @Bean
    NewTopic newTopic(){
        return new NewTopic("apache-kafka-demo", 2, (short) 1);
    }
}
