package com.pay_my_buddy.PayMyBuddy.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
public class LoginController {
    private  static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * Login endpoint to redirect to login page
     */
    @GetMapping("/login")
    public ModelAndView login() {
        logger.info("GET: /login");
        return new ModelAndView("/login");
    }
}
