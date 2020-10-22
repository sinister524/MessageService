package com.sinister524.MessageService.messageService.Controllers.RestControllers;

import com.sinister524.MessageService.messageService.Entities.Account;
import com.sinister524.MessageService.messageService.Entities.Role;
import com.sinister524.MessageService.messageService.Repositories.AccountRepository;
import com.sinister524.MessageService.messageService.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/admin")
public class AdminRestController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping
    public List<Account> getAccounts () {
        return accountService.allAccounts();
    }

    @PutMapping("{account:\\d+}/appointOperator")
    public Account appointOperator (@PathVariable Account account) {
        if (account.removeRole(Role.ROLE_USER)) {
            account.addRole(Role.ROLE_OPERATOR);
        }
        return accountRepository.save(account);
    }

    @PutMapping("{account:\\d+}/removeOperator")
    public Account removeOperator (@PathVariable Account account) {
        if (account.removeRole(Role.ROLE_OPERATOR)) {
            account.addRole(Role.ROLE_USER);
        }
        return accountRepository.save(account);
    }

}
