package com.codegnan.service;
import java.util.List;

import com.codegnan.model.Account;

public class InterestService implements Runnable {

	 private AccountService accountService;
	    private int userId;
	    private double monthlyRate;

	    public InterestService(
	            AccountService accountService,
	            int userId,
	            double monthlyRate) {

	        this.accountService = accountService;
	        this.userId = userId;
	        this.monthlyRate = monthlyRate;
	    }

	    @Override
	    public void run() {

	        try {

	            List<Account> accounts =
	                    accountService.getAccounts(userId);

	            for (Account account : accounts) {

	                if (account.getAccountType()
	                        .equals("SAVINGS")
	                        && account.getBalance() > 0) {

	                    double interest =
	                            account.getBalance()
	                            * monthlyRate;

	                    accountService.deposit(
	                            account.getAccountNumber(),
	                            interest);

	                    System.out.println(
	                            "Interest added to account "
	                            + account.getAccountNumber()
	                            + " : "
	                            + String.format(
	                                    "%.2f",
	                                    interest));
	                }
	            }

	        } catch (Exception e) {

	            System.out.println(
	                    "Interest calculation error: "
	                    + e.getMessage());
	        }
	    }

}
