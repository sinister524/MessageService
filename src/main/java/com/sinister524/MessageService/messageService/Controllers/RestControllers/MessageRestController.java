package com.sinister524.MessageService.messageService.Controllers.RestControllers;

import com.sinister524.MessageService.messageService.Entities.Account;
import com.sinister524.MessageService.messageService.Entities.Message;
import com.sinister524.MessageService.messageService.Entities.Status;
import com.sinister524.MessageService.messageService.Repositories.MessageRepository;
import com.sinister524.MessageService.messageService.Services.AccountService;
import com.sinister524.MessageService.messageService.Services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/messages")
public class MessageRestController {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageService messageService;

    @Autowired
    AccountService accountService;

    @GetMapping
    public List<Message> getMessages (@AuthenticationPrincipal UserDetails currentUser) {

        Account account = (Account) accountService.loadUserByUsername(currentUser.getUsername());

        return messageService.getMessages(account);
    }
    @PostMapping("draft")
    public Message saveDraft (@AuthenticationPrincipal UserDetails currentUser, @RequestBody String text) {
        Message message = new Message(text, Status.DRAFT, (Account) accountService.loadUserByUsername(currentUser.getUsername()));



        return messageRepository.save(message);
    }

    @PostMapping("send")
    public Message sendMessage (@AuthenticationPrincipal UserDetails currentUser, @RequestBody String text) {
        Message message = new Message(text, Status.SENT, (Account) accountService.loadUserByUsername(currentUser.getUsername()));

        return messageRepository.save(message);
    }

    @PutMapping("{message:\\d+}/edit")
    public Message editMessage (@PathVariable Message message, @RequestBody String text) {

        if (message.getStatus().equals(Status.DRAFT)){
            message.setText(text);
        }

        return messageRepository.save(message);

    }

    @PutMapping("{message:\\d+}/send")
    public Message sentDraft (@PathVariable Message message) {

        if (message.getStatus().equals(Status.DRAFT)){
            message.setStatus(Status.SENT);
        }

        return messageRepository.save(message);

    }

    @PutMapping("{message:\\d+}/accept")
    public Message acceptMessage (@PathVariable Message message) {

        if (message.getStatus().equals(Status.SENT)){
            message.setStatus(Status.ACCEPTED);
        }

        return messageRepository.save(message);

    }

    @PutMapping("{message:\\d+}/reject")
    public Message rejectMessage (@PathVariable Message message) {

        if (message.getStatus().equals(Status.SENT)){
            message.setStatus(Status.REJECTED);
        }

        return messageRepository.save(message);

    }

}
