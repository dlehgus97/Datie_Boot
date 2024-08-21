package org.zerock.datie_boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())) // CORS 설정 추가
                .authorizeRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/company").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/cardpassword").permitAll()
                        .requestMatchers(HttpMethod.GET,"/qr.html").permitAll()
                        .requestMatchers(HttpMethod.GET,"/qr").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/check-login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/payresult").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/profile").permitAll()
                        .requestMatchers("/**").authenticated()
                );
        return http.build();
    }

}
