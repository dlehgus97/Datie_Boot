package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // PasswordEncoder 임포트
import org.springframework.stereotype.Service;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User login(String id, String pw, PasswordEncoder passwordEncoder) {
        Optional<User> optionalUser = userRepository.findByUserId(id);
        return (optionalUser.isPresent() && passwordEncoder.matches(pw, optionalUser.get().getPw())) ? optionalUser.get() : null;
    }

    public User registerUser(User user, PasswordEncoder passwordEncoder) {
        user.setPw(passwordEncoder.encode(user.getPw()));
        return userRepository.save(user);
    }

    public User findById(String id) {
        Optional<User> optionalUser = userRepository.findById(Integer.valueOf(id));
        return optionalUser.orElse(null);
    }

    public Optional<User> getUserByUserno(int userno) {
        return userRepository.findByUserno(userno);
    }

//    // 실명인증으로 중복인원 확인하기
//    public boolean checkIdNumberExists(String idNumber) {
//        Optional<User> user = userRepository.findByIdNumber(idNumber);
//        return user.isPresent();
//    }

    public boolean isIdExists(String id) {
        return userRepository.existsById(id);
    }

//    public boolean checkAccount(String accountno) {
//        // DB에서 마지막 4자리와 비교
//        String lastFourDigits = accountno.substring(accountno.length() - 4);
//        return userRepository.existsByLastFourDigits(lastFourDigits);
//    }

    public boolean checkAccountExists(String accountno) {
        return userRepository.existsByAccountno(accountno);

    }
}
