package com.withsafe.domain.indoorMap.dao;

import com.withsafe.domain.indoorMap.domain.IndoorMap;
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
class IndoorMapRepositoryTest {

    @Autowired IndoorMapRepository indoorMapRepository;

//    @Test
//    public void testFindByMapId(){
//        List<IndoorUserLocation> locationByMapId = indoorMapRepository.findLocationByMapId("1004");
//        for (IndoorUserLocation indoorUserLocation : locationByMapId) {
//            System.out.println("indoorUserLocation = " + indoorUserLocation.getId());
//        }
//    }

    @Test
    public void testFindByDepartmentNameAndName(){
        String departmentName = "a부서";
        String mapName = "500ho";
        IndoorMap byDepartmentNameAndName = indoorMapRepository.findByDepartmentNameAndName(departmentName, mapName);

        System.out.println("byDepartmentNameAndName = " + byDepartmentNameAndName.getName());
    }

}