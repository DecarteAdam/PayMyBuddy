package com.pay_my_buddy.PayMyBuddy.data;

import com.pay_my_buddy.PayMyBuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    User findByUsername(String username);

    User findUserById(int id);


    List<User> findAllConnectionsById(int id);

    /*@Query("SELECT * FROM pay_my_buddy.user inner join user_connections where user.username = :username")
    User getCollections(@Param("username") String username);*/
}
