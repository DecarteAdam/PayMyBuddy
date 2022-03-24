package com.pay_my_buddy.PayMyBuddy.services;

import com.pay_my_buddy.PayMyBuddy.model.User;
import com.pay_my_buddy.PayMyBuddy.service.UserDetailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserDetailServiceTest {

    @Autowired
    UserDetailService userDetailService;

    @Test
    @DisplayName("Get User")
    public void getUser() {
        User user = this.userDetailService.getUser("adam@test.com");

        assertEquals("Adam",user.getFirstname());
    }

    @Test
    @DisplayName("Search User")
    public void searchUser() {
        User user = this.userDetailService.search("adam@test.com");

        assertEquals("Adam",user.getFirstname());
    }

    @Test
    @DisplayName("Find User by user name")
    public void findUser() {
        UserDetails user = this.userDetailService.loadUserByUsername("adam@test.com");

        assertEquals("adam@test.com",user.getUsername());
    }
}
