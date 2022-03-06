package com.pay_my_buddy.PayMyBuddy.controller;

import com.pay_my_buddy.PayMyBuddy.model.CustomUserDetails;
import com.pay_my_buddy.PayMyBuddy.model.SendMoneyForm;
import com.pay_my_buddy.PayMyBuddy.model.Transaction;
import com.pay_my_buddy.PayMyBuddy.model.User;
import com.pay_my_buddy.PayMyBuddy.service.TransactionService;
import com.pay_my_buddy.PayMyBuddy.service.UserDetailService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final TransactionService transactionService;
    private final UserDetailService userDetailsService;

    /**
     * Home endpoint to load home page
     */
    @GetMapping("/home")
    public ModelAndView homePage(@Valid Model model,
                                 @Valid SendMoneyForm sendMoneyForm,
                                 @Valid Transaction transaction,
                                 @NotNull @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        logger.info("GET: /home");
        return getOnePage(model, 1, sendMoneyForm, transaction, customUserDetails);
    }

    /**
     * Home enpoint to populate home page
     */
    @GetMapping("/home/page/{pageNumber}")
    public ModelAndView getOnePage(@Valid Model model,
                                   @PathVariable("pageNumber") int currentPage,
                                   @Valid SendMoneyForm sendMoneyForm,
                                   @Valid Transaction transaction,
                                   @NotNull @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        logger.info("GET: /home/page/{}", currentPage);
        String username = customUserDetails.getUsername();
        User user = userDetailsService.getUser(username);

        Page<Transaction> page = transactionService.getTransactions(currentPage, user.getId());
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Transaction> transactions = page.getContent();

        /*Populate home page*/
        model.addAttribute("user", user);
        model.addAttribute("sendMoneyForm", sendMoneyForm);
        model.addAttribute("description", transaction);
        model.addAttribute("transactions", transactions);

        /*Elements linked with pagination*/
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);


        return new ModelAndView("/home");
    }


}
