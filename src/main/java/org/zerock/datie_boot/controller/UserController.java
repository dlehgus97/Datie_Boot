package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.dto.SignUpRequest;
import org.zerock.datie_boot.dto.UserLoginRequest;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest) {
        User user = new User();
        user.setId(signUpRequest.getId());      // userId 설정
        user.setName(signUpRequest.getName());
        user.setPw(signUpRequest.getPw());
        user.setAddr1(signUpRequest.getAddr1());
        user.setAddr2(signUpRequest.getAddr2());
//        user.setAccountno(Integer.parseInt(signUpRequest.getAccountno()));
        user.setHp(String.valueOf(signUpRequest.getHp())); // 전화번호
        user.setEmail(signUpRequest.getEmail()); // 이메일 주소
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest loginRequest) {
        User user = userService.login(loginRequest.getId(), loginRequest.getPw());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디가 존재하지 않습니다.");
        } else {
            // 비밀번호가 틀린 경우
            if (!user.getPw().equals(loginRequest.getPw())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
            }
            return ResponseEntity.ok("로그인에 성공했습니다.");
        }
    }
}

