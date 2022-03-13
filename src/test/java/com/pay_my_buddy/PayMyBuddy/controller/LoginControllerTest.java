package com.pay_my_buddy.PayMyBuddy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = LoginControllerTest.class)
class LoginControllerTest {

    @Test
    void login() {
    }
}