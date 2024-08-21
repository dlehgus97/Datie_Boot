package org.zerock.datie_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.datie_boot.dto.ProfileDTO;
import org.zerock.datie_boot.repository.ProfileRepository;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    // 사용자 번호로 사용자 조회 (계좌 정보 포함)
    public ProfileDTO getUserByUserno(int userno) {
        Optional<ProfileDTO> profileOptional = profileRepository.findUserWithAccountByUserno(userno);
        return profileOptional.orElse(null);
    }
}
