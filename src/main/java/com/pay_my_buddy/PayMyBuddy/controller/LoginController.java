package com.pay_my_buddy.PayMyBuddy.controller;

import com.pay_my_buddy.PayMyBuddy.model.CustomUserDetails;
import com.pay_my_buddy.PayMyBuddy.model.Transaction;
import com.pay_my_buddy.PayMyBuddy.model.User;
import com.pay_my_buddy.PayMyBuddy.service.TransactionService;
import com.pay_my_buddy.PayMyBuddy.service.UserDetailService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class LoginController {
    private TransactionService transactionService;
    private UserDetailService userDetailsService;

    public LoginController(TransactionService transactionService, UserDetailService userDetailsService) {
        this.transactionService = transactionService;
        this.userDetailsService = userDetailsService;
    }


    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @GetMapping("/home")
    public ModelAndView homePage(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUsername();
        int userId = customUserDetails.getUserId();
        User user =  userDetailsService.getUser(username);
        List<Transaction> transactions =  transactionService.getTransactions(userId);
        model.addAttribute("transactions", transactions);
        model.addAttribute("user", user);
        return new ModelAndView("/home");
    }

    @PostMapping("/save")
    public User save(User user){
        return this.userDetailsService.create(user);
    }

}
