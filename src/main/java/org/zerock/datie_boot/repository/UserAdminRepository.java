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
            + "WHERE u.status = :ms ")
    List<Object[]> adminGetListByMs(String ms);

    @Query("SELECT u, c FROM User u LEFT JOIN Card c ON u.userno = c.userno "
            + "WHERE c.cStatus = :cs ")
    List<Object[]> adminGetListByCs(String cs);
}
