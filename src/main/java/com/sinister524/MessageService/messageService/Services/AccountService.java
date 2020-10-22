package com.sinister524.MessageService.messageService.Services;

import com.sinister524.MessageService.messageService.Entities.Account;
import com.sinister524.MessageService.messageService.Entities.Role;
import com.sinister524.MessageService.messageService.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountService() {
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByName(s);

        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return account;
    }

    public Account findAccountById(Long accountId) {
        Optional<Account> accountFromDb = accountRepository.findById(accountId);
        return accountFromDb.orElse(new Account());
    }

    public List<Account> allAccounts() {
        return accountRepository.findAll();
    }

    public boolean saveAccount(Account account) {
        Account accountFromDB = accountRepository.findAccountByName(account.getUsername());

        if (accountFromDB != null) {
            return false;
        }

        account.setRoles(Collections.singleton(Role.ROLE_USER));
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return true;
    }

    public void setAccountRole (Account account, Role role) {
        Account accountFromDB = accountRepository.findAccountByName(account.getUsername());

        if (accountFromDB == null) {
            return;
        }

        if (role.equals(Role.ROLE_USER)) {
            account.addRole(role);
            account.removeRole(Role.ROLE_OPERATOR);
        }
        if (role.equals(Role.ROLE_OPERATOR)) {
            account.addRole(role);
            account.removeRole(Role.ROLE_USER);
        }
        accountRepository.save(account);
    }
}
