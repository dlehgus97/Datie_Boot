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
}
