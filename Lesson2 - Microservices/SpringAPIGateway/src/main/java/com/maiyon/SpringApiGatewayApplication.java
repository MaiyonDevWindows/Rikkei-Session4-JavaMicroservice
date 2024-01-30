package com.maiyon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringApiGatewayApplication.class, args);
    }
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/students/**").uri("http://admin-pc:8081")).build();
    }
}