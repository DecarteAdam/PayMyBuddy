package com.pay_my_buddy.PayMyBuddy.controller;

import com.pay_my_buddy.PayMyBuddy.data.BankAccountDAO;
import com.pay_my_buddy.PayMyBuddy.data.UserRepository;
import com.pay_my_buddy.PayMyBuddy.model.CustomUserDetails;
import com.pay_my_buddy.PayMyBuddy.model.User;
import com.pay_my_buddy.PayMyBuddy.service.BankTransactionException;
import com.pay_my_buddy.PayMyBuddy.service.SendMoneyForm;
import com.pay_my_buddy.PayMyBuddy.service.TransactionService;
import com.pay_my_buddy.PayMyBuddy.service.UserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TransactionController {
    private TransactionService transactionService;

    private  static Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private BankAccountDAO bankAccountDAO;

    private UserRepository userRepository;

    private final UserDetailService userDetailsService;

    public TransactionController(TransactionService transactionService, UserDetailService userDetailsService) {
        this.transactionService = transactionService;
        this.userDetailsService = userDetailsService;
    }


    /*@PostMapping("/transaction")
    public String getTransactions(Transaction transaction){

        return transactionService.createtransaction();
    }*/

    /*@RequestMapping(value = "/", method = RequestMethod.GET)
    public String showBankAccounts(Model model) {
        List<BankAccount> list = bankAccountDAO.listBankAccountInfo();

        model.addAttribute("accountInfos", list);

        return "accountsPage";
    }*/

    /*@RequestMapping(value = "/sendMoney", method = RequestMethod.GET)
    public ModelAndView viewSendMoneyPage(Model model) {

        SendMoneyForm form = new SendMoneyForm(1L, 2L, 700d);

        model.addAttribute("sendMoneyForm", form);

        return new ModelAndView("home");
    }*/



    @RequestMapping(value = "/sendMoney", method = RequestMethod.POST)
    public ModelAndView processSendMoney(Model model,
                                         @ModelAttribute("connection")User connection,
                                         SendMoneyForm sendMoneyForm ,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) throws BankTransactionException {

        String username = customUserDetails.getUsername();
        User user =  userDetailsService.getUser(username);

        logger.info(String.format("---Connection---: , %s", user.toString()) );
        model.addAttribute("user", user);
        model.addAttribute("sendMoneyForm", sendMoneyForm);


        System.out.println("Send Money: " + sendMoneyForm.getAmount());
        try {
            bankAccountDAO.sendMoney(
                    user.getAccount().getId(),
                    connection.getAccount().getId(),
                    sendMoneyForm.getAmount(),
                    customUserDetails.getUsername());
        } catch (BankTransactionException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return new ModelAndView("/home");
        }
        return new ModelAndView("redirect:/home");
    }
}
