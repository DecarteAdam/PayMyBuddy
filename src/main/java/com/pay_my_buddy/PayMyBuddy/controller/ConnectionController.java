package com.pay_my_buddy.PayMyBuddy.controller;

import com.pay_my_buddy.PayMyBuddy.model.CustomUserDetails;
import com.pay_my_buddy.PayMyBuddy.model.User;
import com.pay_my_buddy.PayMyBuddy.service.UserDetailService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
public class ConnectionController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private final UserDetailService userDetailsService;

    /**
     * Display add connection page
     */
    @GetMapping("/addConnection")
    public ModelAndView addConnection() {
        logger.info("GET: /registration");
        return new ModelAndView("/addConnection");
    }

    /**
     * Add connection to the user
     * @param id of the connection
     * @param customUserDetails the user connected
     */
    @PostMapping("/add-connection/{id}")
    public ModelAndView addMyConnection(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        logger.info("GET: /registration");
        this.userDetailsService.addConnection(customUserDetails, id);
        return new ModelAndView("/addConnection");
    }

    /**
     * Search connection by connection email (keyword)
     */
    @GetMapping("/search")
    public ModelAndView search(Model model, String keyword) {
        logger.info("GET: /search");

        User user = this.userDetailsService.search(keyword);
        model.addAttribute("connection", user);

        return new ModelAndView("/addConnection");
    }
}
