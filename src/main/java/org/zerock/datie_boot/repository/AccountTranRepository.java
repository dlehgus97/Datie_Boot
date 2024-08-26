package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.datie_boot.entity.AccountTran;

import java.util.List;

@Repository
public interface AccountTranRepository extends JpaRepository<AccountTran, Long> {
    List<AccountTran> findByAccountnoAndAmount(int accountno, int amount);
}
