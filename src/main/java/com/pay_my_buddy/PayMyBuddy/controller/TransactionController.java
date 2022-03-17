package com.pay_my_buddy.PayMyBuddy.controller;

import com.pay_my_buddy.PayMyBuddy.data.BankAccountDAO;
import com.pay_my_buddy.PayMyBuddy.exception.BankTransactionException;
import com.pay_my_buddy.PayMyBuddy.model.*;
import com.pay_my_buddy.PayMyBuddy.service.TransactionService;
import com.pay_my_buddy.PayMyBuddy.service.UserDetailService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@RestController
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private BankAccountDAO bankAccountDAO;

    private final UserDetailService userDetailsService;

    private final TransactionService transactionService;


    /**
     * Transaction endpoint to make a tra,saction
     */
    @PostMapping(value = "/sendMoney")
    public ModelAndView processSendMoney(@NotNull Model model,
                                         @ModelAttribute("connection") User connection,
                                         @NotNull SendMoneyForm sendMoneyForm,
                                         @NotNull Transaction transaction,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        String username = customUserDetails.getUsername();
        User user = userDetailsService.getUser(username);

        logger.info("POST: /sendMoney");

        model.addAttribute("user", user);
        model.addAttribute("sendMoneyForm", sendMoneyForm);
        model.addAttribute("description", transaction);

        try {
            bankAccountDAO.sendMoney(user, connection, transaction.getDescription(), sendMoneyForm.getAmount());
        } catch (BankTransactionException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return new ModelAndView("/home");
        }
        return new ModelAndView("redirect:/home");
    }


    @GetMapping("/bill")
    public BillDTO getFacture(int userId,
                              @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @NotNull Date date) {
        return this.transactionService.getTransactionByUserIdAndDate(userId, date);
    }
}
