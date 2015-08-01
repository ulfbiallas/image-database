package de.ulfbiallas.imagedatabase.service;

import java.util.List;

import de.ulfbiallas.imagedatabase.entities.Account;

public interface AccountService {

    List<Account> getAllAccounts();

    void createAccount(Account account);

    Account getById(String id);

    Account getByName(String name);

}
