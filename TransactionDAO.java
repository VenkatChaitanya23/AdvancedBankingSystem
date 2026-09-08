package com.codegnan.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.codegnan.model.Transaction;


public class TransactionDAO {
	private List<Transaction> transactions =new ArrayList<>();
    
    public synchronized void save(Transaction transaction) { // Save transaction
        transactions.add(transaction);
    }
    public synchronized List<Transaction>  findByAccount(long accountNumber) {
        return transactions.stream().filter(t ->t.getAccountNumber()== accountNumber).collect(Collectors.toList());
    }
    public synchronized List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
    public synchronized List<Transaction>findByType(String type) {

        return transactions.stream()
                .filter(t ->
                        t.getType()
                        .equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }
}
