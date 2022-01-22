package com.pay_my_buddy.PayMyBuddy.controller;

import com.pay_my_buddy.PayMyBuddy.data.BankAccountDAO;
import com.pay_my_buddy.PayMyBuddy.model.BankAccountInfo;
import com.pay_my_buddy.PayMyBuddy.service.BankTransactionException;
import com.pay_my_buddy.PayMyBuddy.service.SendMoneyForm;
import com.pay_my_buddy.PayMyBuddy.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    private BankAccountDAO bankAccountDAO;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    /*@PostMapping("/transaction")
    public String getTransactions(Transaction transaction){

        return transactionService.createtransaction();
    }*/

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showBankAccounts(Model model) {
        List<BankAccountInfo> list = bankAccountDAO.listBankAccountInfo();

        model.addAttribute("accountInfos", list);

        return "accountsPage";
    }

    @RequestMapping(value = "/sendMoney", method = RequestMethod.GET)
    public ModelAndView viewSendMoneyPage(Model model) {

        SendMoneyForm form = new SendMoneyForm(1L, 2L, 700d);

        model.addAttribute("sendMoneyForm", form);

        return new ModelAndView("home");
    }



    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String processSendMoney(Model model, SendMoneyForm sendMoneyForm) {

        System.out.println("Send Money: " + sendMoneyForm.getAmount());

        try {
            bankAccountDAO.sendMoney(sendMoneyForm.getFromAccountId(), //
                    sendMoneyForm.getToAccountId(), //
                    sendMoneyForm.getAmount());
        } catch (BankTransactionException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "/home";
        }
        return "redirect:/home";
    }
}
