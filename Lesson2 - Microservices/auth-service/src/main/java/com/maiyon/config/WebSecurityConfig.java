package com.maiyon.config;

import com.maiyon.model.entity.enums.RoleName;
import com.maiyon.security.jwt.AccessDenied;
import com.maiyon.security.jwt.JwtEntryPoint;
import com.maiyon.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UserDetailsService userDetailService;
    private final JwtEntryPoint jwtEntryPoint;
    private final JwtTokenFilter jwtTokenFilter;
    private final AccessDenied accessDenied;
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.
            csrf(AbstractHttpConfigurer::disable).
            authenticationProvider(authenticationProvider()).
            authorizeHttpRequests(
                (auth)->auth
                    .requestMatchers("/v1/admin/**").hasAuthority(RoleName.ROLE_ADMIN.name())
                    .requestMatchers("/v1/user/**").hasAuthority(RoleName.ROLE_USER.name())
                    .requestMatchers("/v1/**").permitAll()
                    .anyRequest().authenticated()
            ).
            exceptionHandling(
                (auth)->auth.authenticationEntryPoint(jwtEntryPoint)
                    .accessDeniedHandler(accessDenied)
            ).
            addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class).
            build();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
