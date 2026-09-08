package com.codegnan.model;

import com.codegnan.exception.InsufficientFundsException;

public abstract class Account {

	private long accountNumber;
    private int userId;
    private String holderName;
    private double balance;

    public Account(long accountNumber, int userId,String holderName, double balance) {

        this.accountNumber = accountNumber;
        this.userId = userId;
        this.holderName = holderName;
        this.balance = balance;
    }
    // Abstract method
    public abstract String getAccountType();
    // Deposit money
    public synchronized void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        balance = balance + amount;
    }
    // Withdraw money
    public synchronized void withdraw(double amount)throws InsufficientFundsException {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        if (amount > balance) {
            throw new InsufficientFundsException( "Insufficient funds. Available balance: "+ balance);
        }
        balance = balance - amount;
    }
    // Getters
    public long getAccountNumber() {
        return accountNumber;
    }

    public int getUserId() {
        return userId;
    }

    public String getHolderName() {
        return holderName;
    }

    public synchronized double getBalance() {
        return balance;
    }

    @Override
    public String toString() {

        return "Account No: " + accountNumber+ " | Holder: " + holderName+ " | Type: " + getAccountType()+ " | Balance: "+ String.format("%.2f", balance);
    }
}
