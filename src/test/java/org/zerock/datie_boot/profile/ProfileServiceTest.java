package org.zerock.datie_boot.profile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.datie_boot.dto.ProfileDTO;
import org.zerock.datie_boot.entity.Account;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.AccountRepository;
import org.zerock.datie_boot.repository.ProfileRepository;
import org.zerock.datie_boot.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProfileServiceTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        // 사용자와 계정 엔티티를 삽입합니다.
        User user = new User();
        user.setName("John Doe");
        user.setPw("password");
        user.setEmail("john.doe@example.com");
        user.setAddr1("123 Main St");
        user.setAddr2("Apt 4B");
        user.setModdate(LocalDateTime.now());

        testUser = userRepository.save(user);

        Account account = new Account();
        account.setBank("Bank of Spring");
        account.setAccount("123456789");
        account.setAccountno(testUser.getAccountno());

        accountRepository.save(account);
    }

//    @Test
//    public void testFindUserWithAccountByUserno() {
//        // 테스트할 사용자 ID (userno)
//        int userno = testUser.getUserno();
//
//        // ProfileDTO 조회
//        Optional<ProfileDTO> result = profileRepository.findUserWithAccountByUserno(userno);
//
//        // 결과 검증
//        assertThat(result).isPresent();
//        ProfileDTO profileDTO = result.get();
//        assertThat(profileDTO.getName()).isEqualTo("John Doe");
//        assertThat(profileDTO.getPw()).isEqualTo("password");
//        assertThat(profileDTO.getEmail()).isEqualTo("john.doe@example.com");
//        assertThat(profileDTO.getAddr1()).isEqualTo("123 Main St");
//        assertThat(profileDTO.getAddr2()).isEqualTo("Apt 4B");
//        assertThat(profileDTO.getBank()).isEqualTo("Bank of Spring");
//        assertThat(profileDTO.getAccount()).isEqualTo("123456789");
//        assertThat(profileDTO.getModdate()).isNotNull();
//    }
}
