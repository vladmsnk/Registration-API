package com.selfio.selfio.controllers;

import com.selfio.selfio.dto.UserRegistrationDto;
import com.selfio.selfio.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping(path = "/confirmation")
    public String confirm(@RequestParam("token")  String token, Model model) {
        try {
            registrationService.confirmToken(token);
        } catch (IllegalStateException | UsernameNotFoundException ignored) {

        }
        return "successRegistration";
    }

    @GetMapping(path = "/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        model.addAttribute("user",  new UserRegistrationDto());
        return "index";
    }

    @PostMapping(path = "/registration")
    public String register(@RequestBody @Valid UserRegistrationDto userRegistrationDto, BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error");
        }
        try {
            registrationService.register(userRegistrationDto);
        } catch (IllegalStateException | MailException  e) {
            model.addAttribute("error");
        }
        return "successRegistration";
    }
}