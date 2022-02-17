package com.pay_my_buddy.PayMyBuddy.controller;

import com.pay_my_buddy.PayMyBuddy.DTO.UserDTO;
import com.pay_my_buddy.PayMyBuddy.model.CustomUserDetails;
import com.pay_my_buddy.PayMyBuddy.model.Transaction;
import com.pay_my_buddy.PayMyBuddy.model.User;
import com.pay_my_buddy.PayMyBuddy.service.ConnectionService;
import com.pay_my_buddy.PayMyBuddy.service.SendMoneyForm;
import com.pay_my_buddy.PayMyBuddy.service.TransactionService;
import com.pay_my_buddy.PayMyBuddy.service.UserDetailService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class LoginController {
    private TransactionService transactionService;
    private UserDetailService userDetailsService;

    private ConnectionService connectionService;

    public LoginController(TransactionService transactionService, UserDetailService userDetailsService, ConnectionService connectionService) {
        this.transactionService = transactionService;
        this.userDetailsService = userDetailsService;
        this.connectionService = connectionService;
    }


    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @GetMapping("/home")
    public ModelAndView homePage(Model model, SendMoneyForm sendMoneyForm, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        /*model.addAttribute("sendMoneyForm", sendMoneyForm);*/

        String username = customUserDetails.getUsername();
        User user =  userDetailsService.getUser(username);
        List<Transaction> transactions =  transactionService.getTransactions(user.getId());

        List<UserDTO> connection = connectionService.getConnections(user);
        model.addAttribute("transactions", transactions);
        model.addAttribute("user", user);
        model.addAttribute("connections", connection);
        return new ModelAndView("/home");
    }

}
