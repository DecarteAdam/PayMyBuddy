package com.pay_my_buddy.PayMyBuddy.service;

import com.pay_my_buddy.PayMyBuddy.data.UserRepository;
import com.pay_my_buddy.PayMyBuddy.model.CustomUserDetails;
import com.pay_my_buddy.PayMyBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

@Service
@SessionAttributes("user")
public class UserDetailService implements UserDetailsService {

    /**
     * Load user data
     */

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        return new CustomUserDetails(user);
    }

    public User getUser(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        return user;
    }



}
