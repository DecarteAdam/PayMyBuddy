package com.pay_my_buddy.PayMyBuddy.controller;

import com.pay_my_buddy.PayMyBuddy.data.BankAccountDAO;
import com.pay_my_buddy.PayMyBuddy.model.CustomUserDetails;
import com.pay_my_buddy.PayMyBuddy.model.Transaction;
import com.pay_my_buddy.PayMyBuddy.model.User;
import com.pay_my_buddy.PayMyBuddy.exception.BankTransactionException;
import com.pay_my_buddy.PayMyBuddy.model.SendMoneyForm;
import com.pay_my_buddy.PayMyBuddy.service.UserDetailService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Controller
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private BankAccountDAO bankAccountDAO;

    private final UserDetailService userDetailsService;


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
            bankAccountDAO.sendMoney(
                    user.getAccount().getId(),
                    connection.getAccount().getId(),
                    transaction.getDescription(),
                    sendMoneyForm.getAmount(),
                    connection);
        } catch (BankTransactionException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return new ModelAndView("/home");
        }
        return new ModelAndView("redirect:/home");
    }
}
