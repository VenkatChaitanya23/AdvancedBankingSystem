package com.codegnan.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.codegnan.dao.AccountDAO;
import com.codegnan.dao.TransactionDAO;
import com.codegnan.exception.InsufficientFundsException;
import com.codegnan.exception.InvalidAccountException;
import com.codegnan.model.Account;
import com.codegnan.model.Transaction;

public class AccountService {
	private AccountDAO accountDAO = new AccountDAO();

    private TransactionDAO transactionDAO = new TransactionDAO();
    // Locks for individual accounts
    private ConcurrentHashMap<Long, ReentrantLock> locks = new ConcurrentHashMap<>();
    private long transactionId = 1;
    // Create account
    public void createAccount(Account account) {

        if (account.getBalance() < 0) {

            throw new IllegalArgumentException("Opening balance cannot be negative.");
        }
        accountDAO.save(account);
    }
    // Get all accounts of a user
    public List<Account> getAccounts(int userId) {

        return accountDAO.findByUserId(userId);
    }

    // Check balance
    public double getBalance(long accountNumber)throws InvalidAccountException {
        Account account =accountDAO.findByAccountNumber(accountNumber);
        return account.getBalance();
    }
    // Deposit
    public void deposit(long accountNumber, double amount)throws InvalidAccountException {
        ReentrantLock lock =getLock(accountNumber);
        lock.lock();
        try {
            Account account =accountDAO.findByAccountNumber(accountNumber);

            account.deposit(amount);

            saveTransaction(accountNumber, "DEPOSIT", amount,"Money deposited");

        } finally {
            lock.unlock();
        }
    }

    // Withdraw
    public void withdraw(long accountNumber, double amount)throws InvalidAccountException,InsufficientFundsException {
        ReentrantLock lock =getLock(accountNumber);

        lock.lock();

        try {
            Account account =accountDAO.findByAccountNumber(accountNumber);
            account.withdraw(amount);
            saveTransaction(accountNumber, "WITHDRAW",amount, "Money withdrawn");
        } finally {
            lock.unlock();
        }
    }
    // Transfer money
    public void transfer(long fromAccount,long toAccount,double amount) throws InvalidAccountException,InsufficientFundsException {
        if (fromAccount == toAccount) {
            throw new IllegalArgumentException("Source and destination cannot be same.");
        }
        /*
         * Always lock accounts in the same order.
         * This helps prevent deadlock.
         */
        long first =Math.min(fromAccount, toAccount);
        long second = Math.max(fromAccount, toAccount);

        ReentrantLock firstLock = getLock(first);
        ReentrantLock secondLock = getLock(second);

        firstLock.lock();
        secondLock.lock();

        try {

            Account source =accountDAO.findByAccountNumber(fromAccount);
            Account destination =accountDAO.findByAccountNumber(toAccount);
            // Withdraw from source
            source.withdraw(amount);

            // Deposit into destination
            destination.deposit(amount);

            // Source transaction
            saveTransaction( fromAccount,"TRANSFER_OUT",amount,"Transferred to "+ toAccount);
            // Destination transaction
            saveTransaction(toAccount,"TRANSFER_IN", amount,"Received from " + fromAccount);
        } finally {
            secondLock.unlock();
            firstLock.unlock();
        }
    }
    // Transaction history
    public List<Transaction> getHistory(
            long accountNumber) {

        return transactionDAO.findByAccount(accountNumber);
    }
    // Save transaction
    private void saveTransaction(long accountNumber,String type, double amount, String description) {
        synchronized (this) {
            Transaction transaction =new Transaction(transactionId++,accountNumber,type,amount,description);
            transactionDAO.save(transaction);
        }
    }
    // Get account lock
    private ReentrantLock getLock(long accountNumber) {
        return locks.computeIfAbsent(accountNumber,key -> new ReentrantLock());
    }

}
