package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.dto.IdCheckRequest;
import org.zerock.datie_boot.dto.SignUpRequest;
import org.zerock.datie_boot.dto.UserLoginRequest;
import org.zerock.datie_boot.entity.Account;
import org.zerock.datie_boot.entity.AccountTran;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.jwt.JWTUtil;
import org.zerock.datie_boot.repository.AccountRepository;
import org.zerock.datie_boot.repository.AccountTranRepository;
import org.zerock.datie_boot.repository.UserRepository;
import org.zerock.datie_boot.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RequestMapping("/api")
@RestController
//@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTranRepository accountTranRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody SignUpRequest signUpRequest) {

        Map<String, Object> response = new HashMap<>();
        User user = new User();
        user.setId(signUpRequest.getId());
        user.setPw(signUpRequest.getPw());
        user.setName(signUpRequest.getName());
//        user.setIdnumber(signUpRequest.getIdnumber());
        user.setHp(String.valueOf(signUpRequest.getHp()));
        user.setSex(signUpRequest.getSex());
        user.setAge(signUpRequest.getAge());
        user.setBank(signUpRequest.getBank());
        user.setAccountno(signUpRequest.getAccountno());
        user.setEmail(signUpRequest.getEmail());
        user.setAddr1(signUpRequest.getAddr1());
        user.setAddr2(signUpRequest.getAddr2());

        try {
            User savedUser = userService.registerUser(user, passwordEncoder);
            response.put("success", true);
            response.put("message", "회원가입이 완료되었습니다.");
            response.put("user", savedUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "회원가입에 실패하였습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();
        User user = userService.login(loginRequest.getId(), loginRequest.getPw(), passwordEncoder);

        if (user == null) {
            response.put("success", false);
            response.put("message", "아이디가 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else if (!passwordEncoder.matches(loginRequest.getPw(), user.getPw())) {
            response.put("success", false);
            response.put("message", "비밀번호가 일치하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String token = jwtUtil.createJwt(user.getId(), String.valueOf(user.getUserno()), 3600000L);
        response.put("success", true);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/check-person")
//    public ResponseEntity<Map<String, Object>> checkIdNumber(@RequestBody Map<String, String> request) {
//        String fullIdNumber = request.get("fullIdNumber");
//        System.out.println("Checking ID Number: " + fullIdNumber);
//        boolean exists = userService.checkIdNumberExists(fullIdNumber);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("exists", exists);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("check-id")
    public ResponseEntity<Map<String, Object>> checkId(@RequestBody IdCheckRequest request) {
        boolean exists = userService.isIdExists(request.getId());
        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        response.put("message", exists ? "이미 존재하는 아이디입니다." : "사용 가능한 아이디입니다.");
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/check-account")
//    public ResponseEntity<Map<String, Object>> checkAccount(@RequestBody AccountCheckRequest request) {
//        Map<String, Object> response = new HashMap<>();
//        boolean isValid = userService.checkAccount(request.getAccountno());
//
//        response.put("success", isValid);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/check-account")
    public ResponseEntity<Map<String, Object>> checkAccount(@RequestBody Map<String, String> payload) {
        String account = payload.get("accountno");
        boolean exists = userService.checkAccountExists(account);

        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-fourdigit")
    public ResponseEntity<Map<String, Object>> checkFourdigit(@RequestBody Map<String, String> request) {
        System.out.println("들어왔니?????");
        String accountnoStr = request.get("accountno");
        String accountcheck = request.get("accountcheck");

        // 로그 추가: 입력 값 확인
        System.out.println("입력된 계좌번호: " + accountnoStr);
        System.out.println("입력된 인증번호: " + accountcheck);

        // account 테이블에서 accountno 확인
        Optional<Account> accountOptional = accountRepository.findByAccount(accountnoStr);
        if (!accountOptional.isPresent()) {
            System.out.println("계좌번호가 존재하지 않습니다."); // 로그 추가
            return ResponseEntity.ok(Map.of("success", false));
        }

        // 계좌의 accountno를 가져오기
        int accountno = accountOptional.get().getAccountno();

        // accounttran 테이블에서 해당 accountno의 amount가 1인 행 찾기
        List<AccountTran> accountTrans = accountTranRepository.findByAccountnoAndAmount(accountno, 1);
        if (accountTrans.isEmpty()) {
            System.out.println("해당 계좌의 거래가 존재하지 않습니다."); // 로그 추가
            return ResponseEntity.ok(Map.of("success", false));
        }

        // name의 뒷 4자리 가져오기
        String lastFourDigits = accountTrans.get(0).getName().substring(accountTrans.get(0).getName().length() - 4);
        System.out.println("DB에서 가져온 마지막 4자리: " + lastFourDigits); // 로그 추가

        // 인증번호 비교
        boolean isMatch = lastFourDigits.equals(accountcheck);
        System.out.println("인증번호 일치 여부: " + isMatch); // 로그 추가
        return ResponseEntity.ok(Map.of("success", isMatch));


    }



}
