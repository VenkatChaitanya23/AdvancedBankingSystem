package com.codegnan.model;

public class SavingsAccount extends Account{

	 public SavingsAccount(long accountNumber,int userId,String holderName,double balance) {
                super(accountNumber, userId, holderName, balance);
}
@Override
public String getAccountType() {
return "SAVINGS";
}

}
