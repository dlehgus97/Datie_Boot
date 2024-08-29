package org.zerock.datie_boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.zerock.datie_boot.jwt.JWTFilter;
import org.zerock.datie_boot.jwt.JWTUtil;
import org.zerock.datie_boot.jwt.LoginFilter;
import org.zerock.datie_boot.service.UserService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, UserService userService) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
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
                        .requestMatchers(HttpMethod.GET, "/api/admin/list").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/admin/list").permitAll()
                        .requestMatchers(HttpMethod.POST, "/chat/request").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/diary").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/diary/detail").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/diary/confirmdate").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/diary/upload").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/diary/imageUpload").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/diary/image").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/diary/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/diary/image/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user/{userno}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/card/{cardno}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/card/{cardno}/payment-records").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/profile").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/profile/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/cancelcard/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/lostcard/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admin/list").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/admin/list").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/lovercheck").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/userInfoByNo").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/creationCard").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/check-person").permitAll()
                        .requestMatchers(HttpMethod.POST, "/check-id").permitAll()
                        .requestMatchers(HttpMethod.POST, "/check-account").permitAll()
                        .requestMatchers(HttpMethod.POST, "/check-fourdigit").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/admin/list").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/admin/list").permitAll()
                        .requestMatchers(HttpMethod.POST, "/chat/request").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/diary").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/diary/detail").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/diary/confirmdate").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/diary/upload").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/diary/imageUpload").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/diary/image").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/diary/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/diary/image/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/diaryboard/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/diaryboard/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/comment/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/comment/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user/{userno}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/{getCardno}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/card/{cardno}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/card/{cardno}/payment-records").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/profile/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/profileUpload/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/profileImage/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/delete/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/cancelcard/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/lostcard/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admin/list").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/admin/list").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/lovercheck").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/userInfoByNo").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/creationCard").permitAll()

                        .anyRequest().authenticated()
                )
                .formLogin(auth -> auth.disable())
                .httpBasic(auth -> auth.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .rememberMe(rememberMe -> rememberMe
                        .tokenValiditySeconds(86400)
                )

                .addFilterBefore(new JWTFilter(jwtUtil, userService), UsernamePasswordAuthenticationFilter.class);

                AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
                AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
                http.authenticationManager(authenticationManager);
                LoginFilter loginFilter = new LoginFilter(passwordEncoder(), jwtUtil);
                loginFilter.setAuthenticationManager(authenticationManager);
                http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class) // LoginFilter를 Bean으로 호출

                .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())); // CORS 설정 추가

        return http.build();
    }

//    @Bean
//    public LoginFilter loginFilter() throws Exception {
//        return new LoginFilter(passwordEncoder(), jwtUtil); // LoginFilter Bean으로 등록
//    }

}
