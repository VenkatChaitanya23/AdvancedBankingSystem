package com.codegnan;

import java.util.List;
import java.util.Scanner;

import com.codegnan.exception.AuthenticationException;
import com.codegnan.exception.InsufficientFundsException;
import com.codegnan.exception.InvalidAccountException;
import com.codegnan.model.Account;
import com.codegnan.model.CurrentAccount;
import com.codegnan.model.SavingsAccount;
import com.codegnan.model.Transaction;
import com.codegnan.model.User;
import com.codegnan.service.AccountService;
import com.codegnan.service.AuthenticationService;
import com.codegnan.service.ConcurrentTransactionService;
import com.codegnan.service.ExportService;
import com.codegnan.service.InterestService;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static AuthenticationService authenticationService =new AuthenticationService();

    static AccountService accountService =new AccountService();

    static ExportService exportService =new ExportService();

    static ConcurrentTransactionService concurrentService =new ConcurrentTransactionService(accountService);

    public static void main(String[] args) {

        while (true) {

            System.out.println();
            System.out.println("=================================");
            System.out.println("     ADVANCED BANKING SYSTEM");
            System.out.println("=================================");

            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            try {
                switch (choice) {
                case "1":
                    register();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    System.out.println("Thank you for using Banking System!");
                    return;
                default:
                    System.out.println("Invalid choice.");
                }
            } catch (Exception e) {

                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ================= REGISTER =================

    static void register() {

        System.out.println();
        System.out.println("----- USER REGISTRATION -----");

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user = authenticationService.register(username,password);

        System.out.println("Registration successful!");

        System.out.println("Your User ID: "+ user.getUserId());
    }

    // ================= LOGIN =================

    static void login()throws AuthenticationException {

        System.out.println();
        System.out.println("----- USER LOGIN -----");

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user =authenticationService.login(username,password);

        System.out.println();
        System.out.println("Welcome, "+ user.getUsername()+ "!");

        bankingMenu(user);
    }

    // ================= BANKING MENU =================

    static void bankingMenu(User user) {

        while (true) {

            System.out.println();
            System.out.println("=================================");
            System.out.println("          BANKING MENU");
            System.out.println("=================================");

            System.out.println("1. Create Account");
            System.out.println("2. View My Accounts");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer Money");
            System.out.println("6. Check Balance");
            System.out.println("7. Transaction History");
            System.out.println("8. Export Statement");
            System.out.println("9. Concurrent Transaction Test");
            System.out.println("10. Calculate Interest");
            System.out.println("11. Logout");

            System.out.print("Enter your choice: ");

            String choice = sc.nextLine();

            try {
                switch (choice) {
                case "1":
                    createAccount(user);
                    break;
                case "2":
                    viewAccounts(user);
                    break;
                case "3":
                    deposit();
                    break;
                case "4":
                    withdraw();
                    break;
                case "5":
                    transfer();
                    break;
                case "6":
                    checkBalance();
                    break;
                case "7":
                    transactionHistory();
                    break;

                case "8":
                    exportStatement();
                    break;
                case "9":
                    concurrentTransaction();
                    break;
                case "10":
                    calculateInterest(user);
                    break;
                case "11":
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice.");
                }
            } catch (Exception e) {

                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ================= CREATE ACCOUNT =================

    static void createAccount(User user) {

        System.out.println();
        System.out.println("----- CREATE ACCOUNT -----");

        System.out.print("Enter account number: ");
        long accountNumber =Long.parseLong(sc.nextLine());

        System.out.print("Enter holder name: ");
        String holderName =sc.nextLine();

        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");

        System.out.print("Enter account type: ");
        String type = sc.nextLine();

        System.out.print("Enter opening balance: ");
        double balance =Double.parseDouble(sc.nextLine());

        Account account;

        if (type.equals("1")) {

            account =new SavingsAccount(accountNumber,user.getUserId(),holderName,balance);
        } else if (type.equals("2")) {

            account = new CurrentAccount(accountNumber,user.getUserId(),holderName,balance);
        } else {
            System.out.println("Invalid account type.");
            return;
        }

        accountService.createAccount(account);

        System.out.println("Account created successfully!");
    }

    // ================= VIEW ACCOUNTS =================

    static void viewAccounts(User user) {

        System.out.println();
        System.out.println("----- MY ACCOUNTS -----");

        List<Account> accounts =accountService.getAccounts(user.getUserId());

        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        for (Account account : accounts) {

            System.out.println(account);
        }
    }

    // ================= DEPOSIT =================

    static void deposit()throws InvalidAccountException {

        System.out.println();
        System.out.println("----- DEPOSIT -----");

        System.out.print("Enter account number: ");
        long accountNumber =Long.parseLong(sc.nextLine());

        System.out.print("Enter amount: ");
        double amount =Double.parseDouble(sc.nextLine());

        accountService.deposit(accountNumber,amount);

        System.out.println("Amount deposited successfully!");
    }

    // ================= WITHDRAW =================

    static void withdraw()throws InvalidAccountException,InsufficientFundsException {

        System.out.println();
        System.out.println("----- WITHDRAW -----");

        System.out.print("Enter account number: ");
        long accountNumber =Long.parseLong(sc.nextLine());

        System.out.print("Enter amount: ");
        double amount =Double.parseDouble(sc.nextLine());

        accountService.withdraw(accountNumber,amount);

        System.out.println("Amount withdrawn successfully!");
    }

    // ================= TRANSFER =================

    static void transfer()throws InvalidAccountException,InsufficientFundsException {

        System.out.println();
        System.out.println("----- MONEY TRANSFER -----");

        System.out.print("Enter sender account: ");
        long fromAccount =Long.parseLong(sc.nextLine());

        System.out.print("Enter receiver account: ");
        long toAccount = Long.parseLong(sc.nextLine());

        System.out.print("Enter amount: ");
        double amount =Double.parseDouble(sc.nextLine());

        accountService.transfer(fromAccount,toAccount,amount);

        System.out.println("Money transferred successfully!");
    }

    // ================= BALANCE =================

    static void checkBalance()throws InvalidAccountException {

        System.out.println();
        System.out.println("----- CHECK BALANCE -----");

        System.out.print("Enter account number: ");
        long accountNumber = Long.parseLong(sc.nextLine());

        double balance =accountService.getBalance(accountNumber);

        System.out.println("Current Balance: ₹"+ String.format( "%.2f",balance));
    }

    // ================= TRANSACTION HISTORY =================

    static void transactionHistory() throws InvalidAccountException {

        System.out.println();
        System.out.println("----- TRANSACTION HISTORY -----");

        System.out.print("Enter account number: ");
        long accountNumber =Long.parseLong(sc.nextLine());

        List<Transaction> transactions =accountService.getHistory(accountNumber);

        if (transactions.isEmpty()) {
            System.out.println( "No transactions found.");
            return;
        }
        for (Transaction transaction :transactions) {
            System.out.println(transaction);
        }
    }

    // ================= EXPORT =================

    static void exportStatement()throws InvalidAccountException {

        System.out.println();
        System.out.println("----- EXPORT STATEMENT -----");

        System.out.print("Enter account number: ");
        long accountNumber =Long.parseLong(sc.nextLine());

        List<Transaction> transactions =accountService.getHistory( accountNumber);

        try {
            exportService.exportCSV(accountNumber,transactions);

        } catch (Exception e) {

            System.out.println("Export failed: "+ e.getMessage());
        }
    }

    // ================= CONCURRENT TRANSACTION =================

    static void concurrentTransaction()throws Exception {

        System.out.println();
        System.out.println("----- CONCURRENT TRANSACTION TEST -----");

        System.out.print("Enter account number: ");
        long accountNumber =Long.parseLong(sc.nextLine());

        System.out.print( "Enter deposit amount per thread: ");

        double amount = Double.parseDouble(sc.nextLine());

        System.out.print("Enter number of threads: ");

        int threads =Integer.parseInt(sc.nextLine());

        System.out.println();
        System.out.println("Starting "+ threads + " concurrent transactions...");

        concurrentService.runDepositTest(accountNumber,amount,threads);

        double balance =accountService.getBalance(accountNumber);

        System.out.println();
        System.out.println( "Final Balance: ₹"+ String.format("%.2f",balance));
    }

    // ================= INTEREST =================

    static void calculateInterest(User user)throws InterruptedException {

        System.out.println();
        System.out.println( "----- INTEREST CALCULATION -----");

        // 1% monthly interest for demonstration
        double monthlyRate = 0.01;

        InterestService interestService =new InterestService( accountService, user.getUserId(),monthlyRate);

        Thread interestThread =new Thread(interestService,"InterestThread");

        System.out.println("Interest calculation started...");

        interestThread.start();

        interestThread.join();

        System.out.println("Interest calculation completed.");
    }
}