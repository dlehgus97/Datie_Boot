package org.zerock.datie_boot.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.datie_boot.dto.CustomUserDetails;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // DB에서 id로 조회
        Optional<User> userDataOptional = userRepository.findByUserId(id);

        // Optional을 안전하게 처리
        User user = userDataOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        // 사용자 정보 출력
        System.out.println("Loaded User: ID = " + user.getId() + ", Username = " + user.getName() + ", Password = " + user.getPw());

        // 비밀번호를 비교하기 위한 로그 추가
        // 실제 비밀번호 비교는 LoginFilter에서 진행되어야 하므로, 여기에선 확인용 로그만 추가
        System.out.println("User Loaded. Proceed to authentication with password: " + user.getPw());

        return new CustomUserDetails(user);
    }
}
