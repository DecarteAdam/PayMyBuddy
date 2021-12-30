package com.pay_my_buddy.PayMyBuddy.controller;

import com.pay_my_buddy.PayMyBuddy.service.TransactionService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    /*@GetMapping("/transactions")
    public String getTransactions(Model model){
        List<Transaction> transactions =  transactionService.getTransactions();
        model.addAttribute("transactions", transactions);
        return "home";
    }*/
}
