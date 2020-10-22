package com.sinister524.MessageService.messageService.Controllers;

import com.sinister524.MessageService.messageService.Entities.Account;
import com.sinister524.MessageService.messageService.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public String getRegistration () {
        return "registration";
    }

    @PostMapping
    public String registerNewAccount (Model model, String name, String password, String confirm) {
        if (!password.equals(confirm)) {
            model.addAttribute("confirmError", "Пароли не совпадают");
            return "registration";
        }
        if (!accountService.saveAccount(new Account(name,password))) {
            model.addAttribute("nameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        return "redirect:/login";
    }

}
