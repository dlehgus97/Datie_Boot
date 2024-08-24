package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.datie_boot.dto.ProfileDTO;
import org.zerock.datie_boot.entity.User;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<User, Integer> {


    @Query(value = "SELECT new org.zerock.datie_boot.dto.ProfileDTO(u, a.bank, a.account) " +
            "FROM User u LEFT JOIN Account a ON CAST(u.accountno AS int) = a.accountno " +
            "WHERE u.userno = :userno", nativeQuery = true)// user accountno를 string으로 바꿧을때

    Optional<ProfileDTO> findUserWithAccountByUserno(@Param("userno") int userno);

}
