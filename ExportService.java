package com.codegnan.service;
import java.io.FileWriter;
import java.util.List;

import com.codegnan.model.Transaction;

public class ExportService {
	 public void exportCSV(
	            long accountNumber,
	            List<Transaction> transactions)
	            throws Exception {

	        String fileName =
	                "statement_"
	                + accountNumber
	                + ".csv";

	        FileWriter writer =
	                new FileWriter(fileName);

	        writer.write(
	                "Transaction ID,"
	                + "Account No,"
	                + "Type,"
	                + "Amount,"
	                + "Description,"
	                + "Date\n");

	        for (Transaction t : transactions) {

	            writer.write(
	                    t.getTransactionId() + ","
	                    + t.getAccountNumber() + ","
	                    + t.getType() + ","
	                    + t.getAmount() + ","
	                    + "\"" + t.getDescription() + "\","
	                    + t.getDateTime()
	                    + "\n");
	        }

	        writer.close();

	        System.out.println(
	                "Statement exported: "
	                + fileName);
	    }

}
