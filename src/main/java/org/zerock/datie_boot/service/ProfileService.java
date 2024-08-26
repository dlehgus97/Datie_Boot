package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.datie_boot.dto.ProfileDTO;
import org.zerock.datie_boot.entity.Account;
import org.zerock.datie_boot.entity.DiaryImage;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.AccountRepository;
import org.zerock.datie_boot.repository.DiaryImageRepository;
import org.zerock.datie_boot.repository.ProfileRepository;
import org.zerock.datie_boot.repository.UserRepository;

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

    private static final String UPLOAD_DIR = "D:/shpj2/datie_boot/src/main/resources/static/upload/profile";  // 변경된 경로
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
            // 만약 userno에 해당하는 사용자가 존재하지 않는다면 예외를 던지거나 원하는 처리를 추가할 수 있습니다.
            throw new IllegalArgumentException("해당 유저를 찾을 수 없습니다: " + userno);
        }
    }

}
