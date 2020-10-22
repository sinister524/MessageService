package com.sinister524.MessageService.messageService.Repositories;

import com.sinister524.MessageService.messageService.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByName(String name);
}
