package com.withsafe.repository;

import com.withsafe.domain.User;
import com.withsafe.domain.device.Watch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }
    public User findOne(Long id){
        return em.find(User.class, id);
    }
    public List<User> findAll(){
        return em.createQuery("select u from User u",User.class)
                .getResultList();
    }

    public List<User> findByName(String name){
        return em.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name",name)
                .getResultList();
    }
    /**
     * 부서별 유저 검색 기능 구현 필요시 findByDepartment 메소드 추가
     */
}