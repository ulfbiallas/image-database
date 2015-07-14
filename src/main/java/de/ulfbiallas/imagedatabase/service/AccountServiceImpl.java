package de.ulfbiallas.imagedatabase.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import de.ulfbiallas.imagedatabase.entities.Account;
import de.ulfbiallas.imagedatabase.repository.AccountRepository;

@Component
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Inject
    public AccountServiceImpl(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void createAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account getById(String id) {
        return accountRepository.findOne(id);
    }

    @Override
    public Account getByName(String name) {
        return accountRepository.findByName(name);
    }

}
