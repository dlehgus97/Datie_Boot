package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.datie_boot.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findByUserId(@Param("id") String id); // 메소드 이름 변경
}
