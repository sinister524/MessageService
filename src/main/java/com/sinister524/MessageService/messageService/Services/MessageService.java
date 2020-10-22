package com.sinister524.MessageService.messageService.Services;

import com.sinister524.MessageService.messageService.Entities.Account;
import com.sinister524.MessageService.messageService.Entities.Message;
import com.sinister524.MessageService.messageService.Entities.Role;
import com.sinister524.MessageService.messageService.Entities.Status;
import com.sinister524.MessageService.messageService.Repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public List<Message> getMessages(Account account) {
        if (account.isRole(Role.ROLE_OPERATOR)) {
            return messageRepository.findMessageByStatus(Status.SENT, Sort.by(Sort.Direction.DESC, "date"));
        } else {
            return messageRepository.findMessageByAccount(account, Sort.by(Sort.Direction.DESC, "date"));
        }
    }
}
