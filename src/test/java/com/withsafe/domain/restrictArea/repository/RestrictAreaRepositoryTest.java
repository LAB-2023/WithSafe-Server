package com.withsafe.domain.restrictArea.repository;

import com.withsafe.domain.restrictArea.domain.RestrictArea;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(value = false)
class RestrictAreaRepositoryTest {

    @Autowired RestrictAreaRepository restrictAreaRepository;

    @Test
    public void testFindAll(){
        List<RestrictArea> all = restrictAreaRepository.findAll();

        for (RestrictArea area : all) {
            System.out.println("area = " + area.getCoordinate_left());
        }
    }

}