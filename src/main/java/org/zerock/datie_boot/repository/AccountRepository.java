package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.datie_boot.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    //userno로 account조회
    Account findByUserno(int userno);


    Optional<Account> findByAccount(String accountno);
    Optional<Account> findByAccountno(int accountno);

    Optional<Account> findAccountnoByAccount(String account);

    boolean existsByAccount(String account);
}
