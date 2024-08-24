package org.zerock.datie_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.datie_boot.entity.User;

import java.util.List;

public interface UserAdminRepository extends JpaRepository<User, Integer> {









    @Query("SELECT u , c FROM User u LEFT JOIN Card c ON u.userno = c.userno")
    public List<Object[]> adminGetList();

    @Query("SELECT u, c FROM User u LEFT JOIN Card c ON u.userno = c.userno "
            + "WHERE u.userno = :num ")
    public List<Object[]> adminGetListByNum(String num);

    @Query("SELECT u, c FROM User u LEFT JOIN Card c ON u.userno = c.userno "
            + "WHERE u.id LIKE CONCAT('%', :id, '%')")
    List<Object[]> adminGetListById(String id);

    @Query("SELECT u, c FROM User u LEFT JOIN Card c ON u.userno = c.userno "
            + "WHERE u.name LIKE CONCAT('%', :name, '%')")
    List<Object[]> adminGetListByName(String name);



    @Query("SELECT u, c FROM User u LEFT JOIN Card c ON u.userno = c.userno "
            + "WHERE u.status = CASE "
            + "WHEN :ms = '탈퇴' THEN 0 "
            + "WHEN :ms = '이용중' THEN 1 "
            + "WHEN :ms = '관리자' THEN 2 "
            + "END")
    List<Object[]> adminGetListByMs(String ms);

    @Query("SELECT u, c FROM User u LEFT JOIN Card c ON u.userno = c.userno "
            + "WHERE (c.cStatus = CASE "
            + "WHEN :cs = '사용중' THEN 1 "
            + "WHEN :cs = '정지' THEN 2 "
            + "WHEN :cs = '해지' THEN 3 "
            + "WHEN :cs = '정지신청' THEN 4 "
            + "END) "
            + "OR (:cs = '미사용' AND c.cStatus IS NULL)")
    List<Object[]> adminGetListByCs(String cs);
}
