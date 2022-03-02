package com.pay_my_buddy.PayMyBuddy.controller;

import com.pay_my_buddy.PayMyBuddy.service.ConnectionService;
import com.pay_my_buddy.PayMyBuddy.service.TransactionService;
import com.pay_my_buddy.PayMyBuddy.service.UserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    private  static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final TransactionService transactionService;
    private final UserDetailService userDetailsService;

    private final ConnectionService connectionService;

    public LoginController(TransactionService transactionService, UserDetailService userDetailsService, ConnectionService connectionService) {
        this.transactionService = transactionService;
        this.userDetailsService = userDetailsService;
        this.connectionService = connectionService;
    }


    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    /*@GetMapping("/home")
    public ModelAndView homePage(Model model, SendMoneyForm sendMoneyForm, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        *//*model.addAttribute("sendMoneyForm", sendMoneyForm);*//*
        logger.info(String.format("CustomUserDetails: , %s", customUserDetails.toString()) );

        String username = customUserDetails.getUsername();
        User user =  userDetailsService.getUser(username);
        List<Transaction> transactions =  transactionService.getTransactions(user.getId());

        List<UserDTO> connection = connectionService.getConnections(user);
        model.addAttribute("transactions", transactions);
        model.addAttribute("user", user);
        model.addAttribute("connections", connection);
        model.addAttribute("sendMoneyForm", sendMoneyForm);
        return new ModelAndView("/home");
    }*/

}
