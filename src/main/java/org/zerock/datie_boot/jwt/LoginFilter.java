package org.zerock.datie_boot.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.zerock.datie_boot.dto.CustomUserDetails;
import org.zerock.datie_boot.dto.UserLoginRequest;

import java.io.BufferedReader;
import java.io.IOException;

@Component
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil; // JWTUtil 주입 추가

    public LoginFilter(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil; // JWTUtil 주입
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String id = null;
        String pw = null;

        // JSON 형식의 본문 읽기
        try {
            BufferedReader reader = request.getReader();
            ObjectMapper objectMapper = new ObjectMapper();
            UserLoginRequest loginRequest = objectMapper.readValue(reader, UserLoginRequest.class);
            id = loginRequest.getId();
            pw = loginRequest.getPw();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("ID**********************" + id);
        System.out.println("pw*******************8**" + pw);

        // id, pw 검증을 위해 토큰에 담고
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(id, pw);
        // authenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws ServletException, IOException {
        // 성공적인 인증 처리
        System.out.println("*********&&&&&&&&&&&&&&&&&&& Successful Authentication");
        // CustomUserDetails에서 id와 userno를 가져옴
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = customUserDetails.getId(); // ID를 사용
        String userno = String.valueOf(customUserDetails.getUserno()); // userno를 추가로 가져옴

        // JWT 생성
        String token = jwtUtil.createJwt(userId, userno, 60 * 60 * 10L); // 10시간 유효한 JWT 생성
        System.out.println("token:" + token);
        // JWT를 Authorization 헤더에 추가
        response.setHeader("Authorization", "Bearer " + token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws ServletException, IOException {
        // 실패한 인증 처리
        System.out.println("Authentication failed: " + failed.getMessage());
        // 추가 정보 출력
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Request Method: " + request.getMethod());
        response.setStatus(401);
    }
}
