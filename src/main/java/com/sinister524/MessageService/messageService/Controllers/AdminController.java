package com.sinister524.MessageService.messageService.Controllers;

import com.sinister524.MessageService.messageService.Entities.Account;
import com.sinister524.MessageService.messageService.Entities.Role;
import com.sinister524.MessageService.messageService.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public String getAllAccounts (Model model) {
        List<Account> accounts = accountService.allAccounts();
        model.addAttribute("accounts", accounts);
        return "admin";
    }


    @PostMapping("{account:\\d+}/edit")
    public String editAccountRole (@PathVariable Account account, String role) {

        accountService.setAccountRole(account, Role.valueOf(role));

        return "redirect:/admin";
    }
}
