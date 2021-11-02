package com.selfio.selfio.controllers;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.MailDateFormat;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping(path = "/registration/confirmation")
    public String confirm(@RequestParam("token") String token) {
        String page = null;
        try {
            page =  registrationService.confirmToken(token);
        } catch (IllegalStateException | UsernameNotFoundException ignored) {

        }
        return page;
    }

    @GetMapping(path = "/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        model.addAttribute("user",  new UserRegistrationDto());
        return "index";
    }

    @PostMapping(path = "/registration")
    public String register(@ModelAttribute("user") @Valid UserRegistrationDto userRegistrationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        try {
            registrationService.register(userRegistrationDto);
        } catch (IllegalStateException illegalStateException) {
            return "error";
        }
        return "successRegistration";
    }
}