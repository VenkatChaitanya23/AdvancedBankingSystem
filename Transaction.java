package com.codegnan.model;

import java.time.LocalDateTime;

public class Transaction {

	 private long transactionId;
	    private long accountNumber;
	    private String type;
	    private double amount;
	    private String description;
	    private LocalDateTime dateTime;

	    public Transaction(long transactionId,long accountNumber,String type, double amount,String description) {

	        this.transactionId = transactionId;
	        this.accountNumber = accountNumber;
	        this.type = type;
	        this.amount = amount;
	        this.description = description;
	        this.dateTime = LocalDateTime.now();
	    }

	    public long getTransactionId() {
	        return transactionId;
	    }

	    public long getAccountNumber() {
	        return accountNumber;
	    }

	    public String getType() {
	        return type;
	    }

	    public double getAmount() {
	        return amount;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public LocalDateTime getDateTime() {
	        return dateTime;
	    }

	    @Override
	    public String toString() {

	        return "Transaction ID: " + transactionId
	                + " | Account No: " + accountNumber
	                + " | Type: " + type
	                + " | Amount: " + String.format("%.2f", amount)
	                + " | Description: " + description
	                + " | Date: " + dateTime;
	    }
}
