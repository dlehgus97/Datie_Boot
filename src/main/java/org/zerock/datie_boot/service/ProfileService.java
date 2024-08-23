package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.datie_boot.dto.ProfileDTO;
import org.zerock.datie_boot.entity.Account;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.AccountRepository;
import org.zerock.datie_boot.repository.ProfileRepository;
import org.zerock.datie_boot.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public ProfileDTO getUserByUserno(int userno) {
        return profileRepository.findUserWithAccountByUserno(userno)
                .orElse(null);
    }

    @Transactional
    public void updateUserProfile(int userno, ProfileDTO profileDTO) {
        User user = userRepository.findById(userno)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setName(profileDTO.getName());
        user.setEmail(profileDTO.getEmail());
        user.setAddr1(profileDTO.getAddr1());
        user.setAddr2(profileDTO.getAddr2());
        user.setSex(profileDTO.getSex());
        user.setAge(profileDTO.getAge());

        // Account 정보 업데이트 (가정)
        // accountno를 통해 Account 엔티티를 조회하고 업데이트합니다.
        Account account = accountRepository.findByUserno(userno); // userno으로 Account 조회
        if (account != null) {
            account.setBank(profileDTO.getBank());
            account.setAccount(profileDTO.getAccount());
            accountRepository.save(account); // Account 엔티티 저장
        }

        // 현재 시간으로 moddate 설정
        user.setModdate(LocalDateTime.now());

        userRepository.save(user);
    }

}
