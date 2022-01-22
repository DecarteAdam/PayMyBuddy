/*
package com.pay_my_buddy.PayMyBuddy.data;

import com.pay_my_buddy.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username){
        User byUsername = this.userRepository.findByUsername(username);
        User user = new User();
        user.setUsername(byUsername.getUsername());
        user.setFirstname(byUsername.getFirstname());
        user.setEmail(byUsername.getEmail());
        user.setId(byUsername.getId());
        return user;
    }
}
*/
