package com.sinister524.MessageService.messageService.Controllers;

import com.sinister524.MessageService.messageService.Entities.Account;
import com.sinister524.MessageService.messageService.Entities.Message;
import com.sinister524.MessageService.messageService.Entities.Status;
import com.sinister524.MessageService.messageService.Repositories.MessageRepository;
import com.sinister524.MessageService.messageService.Services.AccountService;
import com.sinister524.MessageService.messageService.Services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/messages")
public class MessagesController {

    @Autowired
    MessageService messageService;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    AccountService accountService;

    @GetMapping
    public String getMessages(@AuthenticationPrincipal UserDetails currentUser, Model model) {

        Account account = (Account) accountService.loadUserByUsername(currentUser.getUsername());

        model.addAttribute("messages", messageService.getMessages(account));
        return "messages";
    }

    @PostMapping
    public String addMessage (@AuthenticationPrincipal UserDetails currentUser, @RequestParam String text, @RequestParam String status) {
        Account account = (Account) accountService.loadUserByUsername(currentUser.getUsername());

        Message message = new Message(text, Status.valueOf(status), account);
        messageRepository.save(message);

        return "redirect:/messages";
    }

    @GetMapping("{message:\\d+}")
    public String openMessage (@PathVariable Message message, @AuthenticationPrincipal UserDetails currentUser, Model model) {

        Account account = (Account) accountService.loadUserByUsername(currentUser.getUsername());
        if (account.getId().equals(message.getAccount().getId())){
            model.addAttribute("message", message);
        }

        return "open-message";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("{message:\\d+}/edit")
    public String editMessage (@PathVariable Message message, @RequestParam String text, @RequestParam String status) {
        message.setDate(new Date());
        message.setText(text);
        message.setStatus(Status.valueOf(status));

        messageRepository.save(message);

        return "redirect:/messages";
    }

    @PreAuthorize("hasRole('ROLE_OPERATOR')")
    @PostMapping("{message:\\d+}/process")
    public String processMessage (@PathVariable Message message, @RequestParam String status) {

        message.setStatus(Status.valueOf(status));

        messageRepository.save(message);

        return "redirect:/messages";
    }

}
