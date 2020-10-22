package com.sinister524.MessageService.messageService.Repositories;

import com.sinister524.MessageService.messageService.Entities.Account;
import com.sinister524.MessageService.messageService.Entities.Message;
import com.sinister524.MessageService.messageService.Entities.Status;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessageByAccount(Account account);

    List<Message> findMessageByAccount(Account account, Sort sort);

    List<Message> findMessageByStatus(Status status);

    List<Message> findMessageByStatus(Status status, Sort sort);
}
