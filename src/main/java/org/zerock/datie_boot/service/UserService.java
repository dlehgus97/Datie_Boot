package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.AccountRepository;
import org.zerock.datie_boot.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public User login(String id, String pw) {
        Optional<User> optionalUser = userRepository.findByUserId(id); // 메소드 이름 변경
        return (optionalUser.isPresent() && optionalUser.get().getPw().equals(pw)) ? optionalUser.get() : null; // 비밀번호 확인
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

}
