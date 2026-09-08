package com.codegnan.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentTransactionService {
	private AccountService accountService;

    public ConcurrentTransactionService(
            AccountService accountService) {

        this.accountService = accountService;
    }

    public void runDepositTest(
            long accountNumber,
            double amount,
            int numberOfThreads)
            throws InterruptedException {

        ExecutorService executor =
                Executors.newFixedThreadPool(
                        numberOfThreads);

        for (int i = 1;
             i <= numberOfThreads;
             i++) {

            final int threadNumber = i;

            executor.submit(() -> {

                try {

                    accountService.deposit(
                            accountNumber,
                            amount);

                    System.out.println(
                            "Thread "
                            + threadNumber
                            + " deposited "
                            + amount);

                } catch (Exception e) {

                    System.out.println(
                            "Thread "
                            + threadNumber
                            + " failed: "
                            + e.getMessage());
                }
            });
        }

        executor.shutdown();

        while (!executor.isTerminated()) {

            Thread.sleep(50);
        }
    }

}
