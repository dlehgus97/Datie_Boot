package org.zerock.datie_boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/login").permitAll() // 로그인 POST 요청 허용
                        .requestMatchers(HttpMethod.POST, "/signup").permitAll() // 회원가입 POST 요청 허용
                        .requestMatchers(HttpMethod.GET, "/api/company").permitAll() // API 요청 허용
                        .requestMatchers("/**").authenticated() // 모든 다른 요청은 인증 필요
                );
        return http.build();
    }
}
