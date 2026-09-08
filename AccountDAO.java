package com.codegnan.dao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codegnan.exception.InvalidAccountException;
import com.codegnan.model.Account;

public class AccountDAO {
	HashMap<Long, Account> accounts = new HashMap<>();

    public synchronized void save(Account account) {

        if (accounts.containsKey(
                account.getAccountNumber())) {

            throw new IllegalArgumentException("Account number already exists.");
        }

        accounts.put(account.getAccountNumber(),account);
    }
    public Account findByAccountNumber(long accountNumber)throws InvalidAccountException {
        Account account =accounts.get(accountNumber);
        if (account == null) {
            throw new InvalidAccountException("Account not found: "+ accountNumber);
        }
        return account;
    }
    public List<Account> findByUserId(int userId) {
        List<Account> result =new ArrayList<>();

        for (Account account :accounts.values()) {
            if (account.getUserId() == userId) {
                result.add(account);
            }
        }
        return result;
    }
    public boolean exists(long accountNumber) {
        return accounts.containsKey(accountNumber);
    }
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

}
