package com.pay_my_buddy.PayMyBuddy.controller;

import com.pay_my_buddy.PayMyBuddy.model.UserFormDTO;
import com.pay_my_buddy.PayMyBuddy.service.UserDetailService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private final UserDetailService userDetailsService;


    /**
     * Dsiplay registration page
     */
    @GetMapping("/registration")
    public ModelAndView registration(Model model) {
        logger.info("GET: /registration");
        model.addAttribute("userForm", new UserFormDTO());

        return new ModelAndView("/registration");
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userForm") UserFormDTO userFormDTO, BindingResult bindingResult, Model model) {
        logger.info("POST: /registration");

        return this.userDetailsService.registration(userFormDTO, bindingResult);
    }


}
