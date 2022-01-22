package com.pay_my_buddy.PayMyBuddy.data;

import com.pay_my_buddy.PayMyBuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    User findByUsername(String username);

    /*@Query("SELECT * FROM pay_my_buddy.user_connections inner join pay_ where ")
    User getAllByUserUserId();*/
}
