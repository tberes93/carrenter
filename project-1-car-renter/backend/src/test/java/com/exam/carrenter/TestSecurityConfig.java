package com.exam.carrenter;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@TestConfiguration
@Profile("test")
public class TestSecurityConfig {
    @Bean
    @Primary // <-- ez fontos, hogy ezt vegye felül
    SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .sessionManagement(sm -> sm.disable()) // vagy STATELESS, mindegy, itt permitAll a lényeg
                .headers(h -> {}); // semmi extra
        return http.build();
    }
}
