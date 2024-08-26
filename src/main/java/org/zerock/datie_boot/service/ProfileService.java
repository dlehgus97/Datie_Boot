package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.datie_boot.dto.DeleteIdDTO;
import org.zerock.datie_boot.dto.PasswordChangeRequestDTO;
import org.zerock.datie_boot.dto.ProfileDTO;
import org.zerock.datie_boot.entity.Account;
import org.zerock.datie_boot.entity.Card;
import org.zerock.datie_boot.entity.DiaryImage;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

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

    private static final String UPLOAD_DIR = "D:/shpj2/datie_boot/src/main/resources/static/upload/profile";

    @Transactional
    public void saveProfile(int userno, MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
        Path filePath = Paths.get(UPLOAD_DIR, uniqueFilename);
        System.out.println("pro: " + uniqueFilename);

        // 파일을 로컬 디렉토리에 저장
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, image.getBytes());

        // 기존 User 엔티티를 데이터베이스에서 조회
        Optional<User> optionalUser = userRepository.findById(userno);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // 프로필 관련 필드만 업데이트
            user.setProfileOrg(originalFilename);
            user.setProfileReal(uniqueFilename);

            // 업데이트된 엔티티를 저장
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("해당 유저를 찾을 수 없습니다: " + userno);
        }
    }

    @Transactional
    public boolean deleteID(DeleteIdDTO deleteIdDTO) {
        int userno = deleteIdDTO.getUserno();
        int cardno = deleteIdDTO.getCardno();
        int newStatus = deleteIdDTO.getStatus();

        Optional<User> userOptional = userRepository.findById(userno);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (cardno == 0) {
                // cardno가 0인 경우, 사용자의 status를 0으로 변경
                user.setStatus(0);
                userRepository.save(user);
                return true;
            } else {
                // cardno가 0이 아닌 경우, 카드 탈퇴 처리 로직
                Optional<Card> cardOptional = cardRepository.findByUserno(userno);
                if (cardOptional.isPresent()) {
                    Card card = cardOptional.get();

                    // 카드 상태를 주어진 status로 변경
                    card.setCStatus(0);
                    cardRepository.save(card);
                    return true;
                }
            }
        }
        return false;
    }
}
