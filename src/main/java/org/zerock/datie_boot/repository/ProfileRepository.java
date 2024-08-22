package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.datie_boot.dto.ProfileDTO;
import org.zerock.datie_boot.entity.User;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<User, Integer> {

    @Query("SELECT new org.zerock.datie_boot.dto.ProfileDTO(u.userno, u.name, u.pw, u.email, u.addr1, u.addr2, u.sex,u.age, u.accountno, a.bank, a.account, u.moddate) " +
            "FROM User u LEFT JOIN Account a ON u.userno = a.userno " +
            "WHERE u.userno = :userno")
    Optional<ProfileDTO> findUserWithAccountByUserno(@Param("userno") int userno);

}
