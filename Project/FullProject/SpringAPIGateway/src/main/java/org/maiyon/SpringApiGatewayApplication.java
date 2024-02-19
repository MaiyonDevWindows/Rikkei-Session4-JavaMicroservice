package org.maiyon;

import org.maiyon.config.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringApiGatewayApplication {
    @Value("${auth.service.url}")
    private String authServiceUrl;
    @Value("${auth.service.port}")
    private String authServicePort;
    @Value("${product.service.url}")
    private String productServiceUrl;
    @Value("${product.service.port}")
    private String productServicePort;
    @Value("${order.service.url}")
    private String orderServiceUrl;
    @Value("${order.service.port}")
    private String orderServicePort;

    @Autowired
    private AuthenticationFilter authenticationFilter;

    public static void main(String[] args) {
        SpringApplication.run(SpringApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder
            .routes()
            .route(r->r.path("/v1/auth/**").uri("http://" + authServiceUrl + ":" + authServicePort + "/"))
            .route(r->r.path("/v1/products/**", "/v1/categories/**",
                "/v1/admin/categories","/v1/admin/categories/**","/v1/user/categories","/v1/user/categories/**",
                "/v1/admin/products","/v1/admin/products/**","/v1/user/products","/v1/user/products/**")
                    .filters(f->f.filters(authenticationFilter)).uri("http://" + productServiceUrl + ":" + productServicePort + "/"))
            .route(r->r.path("/v1/admin/order/**","/v1/user/order/**","/v1/admin/shopping-cart/**","/v1/user/shopping-cart/**")
                    .filters(f->f.filters(authenticationFilter)).uri("http://" + orderServiceUrl + ":" + orderServicePort + "/"))
            .build();
    }
}