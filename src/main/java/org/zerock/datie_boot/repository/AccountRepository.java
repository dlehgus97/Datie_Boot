package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.datie_boot.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    //userno로 account조회
    Account findByUserno(int userno);
}
