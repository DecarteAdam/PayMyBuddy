package com.pay_my_buddy.PayMyBuddy.service;

import com.pay_my_buddy.PayMyBuddy.data.UserRepository;
import com.pay_my_buddy.PayMyBuddy.model.CustomUserDetails;
import com.pay_my_buddy.PayMyBuddy.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

@Service
@SessionAttributes("user")
public class UserDetailService implements UserDetailsService {
    private  static final Logger logger = LoggerFactory.getLogger(UserDetailService.class);

    /**
     * Load user data
     */

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Load User by username: {}", username);
        User user = userRepository.findByUsername(username);
        return new CustomUserDetails(user);
    }

    public User getUser(String username) throws UsernameNotFoundException {
        logger.info("Get User by username: {}", username);
        return userRepository.findByUsername(username);
    }



}
