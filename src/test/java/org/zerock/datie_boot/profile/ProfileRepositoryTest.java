package org.zerock.datie_boot.profile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.datie_boot.repository.ProfileRepository;

@SpringBootTest
public class ProfileRepositoryTest {
    @Autowired
    private ProfileService service;

    @Autowired
    private ProfileRepository repository;


    @Test
    public void test() {
//        respository.
        System.out.println(service.getUserByUserno(62));
    }

}
