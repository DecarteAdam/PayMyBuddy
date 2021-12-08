package com.pay_my_buddy.PayMyBuddy.controller;

import com.pay_my_buddy.PayMyBuddy.model.User;
import com.pay_my_buddy.PayMyBuddy.service.UserDetailService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {
    private  final OAuth2AuthorizedClientService auth2AuthorizedClientService;
    private UserDetailService userDetailsService;

    public LoginController(OAuth2AuthorizedClientService auth2AuthorizedClientService) {
        this.auth2AuthorizedClientService = auth2AuthorizedClientService;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/login");

    }

    @GetMapping("/home")
    public ModelAndView homePage(){
        return new ModelAndView("/home");
    }

    @PostMapping("/save")
    public User save(User user){
        return this.userDetailsService.create(user);
    }

}
