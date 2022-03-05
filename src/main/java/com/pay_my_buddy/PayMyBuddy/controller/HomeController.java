package com.pay_my_buddy.PayMyBuddy.controller;

import com.pay_my_buddy.PayMyBuddy.DTO.UserDTO;
import com.pay_my_buddy.PayMyBuddy.model.CustomUserDetails;
import com.pay_my_buddy.PayMyBuddy.model.SendMoneyForm;
import com.pay_my_buddy.PayMyBuddy.model.Transaction;
import com.pay_my_buddy.PayMyBuddy.model.User;
import com.pay_my_buddy.PayMyBuddy.service.ConnectionService;
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
import java.util.List;

@AllArgsConstructor
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final TransactionService transactionService;
    private final UserDetailService userDetailsService;
    private final ConnectionService connectionService;


	@GetMapping("/home")
	public ModelAndView homePage(Model model, SendMoneyForm sendMoneyForm, @AuthenticationPrincipal CustomUserDetails customUserDetails){
		logger.info("GET: /home");

		String username = customUserDetails.getUsername();
		User user =  userDetailsService.getUser(username);
		//List<Transaction> transactions =  transactionService.getTransactions(user.getId());

		List<UserDTO> connection = connectionService.getConnections(user);
		//model.addAttribute("transactions", transactions);
		model.addAttribute("user", user);
		model.addAttribute("connections", connection);
		model.addAttribute("sendMoneyForm", sendMoneyForm);
		return getOnePage(model, 1, sendMoneyForm, customUserDetails);
	}


    @GetMapping("/home/page/{pageNumber}")
    public ModelAndView getOnePage(@Valid Model model,
                                   @PathVariable("pageNumber") int currentPage,
                                   SendMoneyForm sendMoneyForm,
                                   @AuthenticationPrincipal @Valid CustomUserDetails customUserDetails){

        String username = customUserDetails.getUsername();
        User user =  userDetailsService.getUser(username);

        Page<Transaction> page = transactionService.getTransactions(currentPage, user.getId());
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Transaction> transactions = page.getContent();

        model.addAttribute("user", user);
        model.addAttribute("sendMoneyForm", sendMoneyForm);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("transactions", transactions);

        return new ModelAndView("/home");
    }


}
