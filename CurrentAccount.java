package com.codegnan.model;

public class CurrentAccount extends Account{

	public CurrentAccount(long accountNumber,int userId,String holderName,double balance) {
                super(accountNumber, userId, holderName, balance);
}
@Override
public String getAccountType() {
return "CURRENT";
}
}
