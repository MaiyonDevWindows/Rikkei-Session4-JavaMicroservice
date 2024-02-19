package org.maiyon.config;

import org.maiyon.service.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
public class AuthenticationFilter implements GatewayFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
            if(
                exchange.getRequest().getURI().getPath().contains("/auth") ||
                exchange.getRequest().getURI().getPath().contains("/v1/categories") ||
                exchange.getRequest().getURI().getPath().contains("/v1/products")
            ) {
                return chain.filter(exchange);
            }
            else return sendError(exchange, HttpStatus.UNAUTHORIZED);
        }
        String authHeader= Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).getFirst();
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token=authHeader.substring(7);
            try{
                jwtUtils.validateToken(token);
                List<String> roles = jwtUtils.getRolesFromToken(token);
                if(roles.contains("ROLE_ADMIN"))
                    if(exchange.getRequest().getURI().getPath().contains("/admin"))
                        return chain.filter(exchange);
                if (roles.contains("ROLE_USER"))
                    if(exchange.getRequest().getURI().getPath().contains("/user"))
                        return chain.filter(exchange);
                return sendError(exchange,HttpStatus.UNAUTHORIZED);
            }catch (Exception e){
                return sendError(exchange,HttpStatus.UNAUTHORIZED);
            }
        }
        return sendError(exchange,HttpStatus.UNAUTHORIZED);
    }

    private Mono<Void> sendError(ServerWebExchange exchange, HttpStatus httpStatus){
        ServerHttpResponse serverHttpResponse=exchange.getResponse();
        serverHttpResponse.setStatusCode(httpStatus);
        return serverHttpResponse.setComplete();
    }
}