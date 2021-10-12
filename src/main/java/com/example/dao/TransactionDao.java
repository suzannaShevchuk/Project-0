package com.example.dao;

import java.util.List;

import com.example.models.Account;
import com.example.models.Transaction;

public interface TransactionDao {

	List<Transaction> getAllTransactions();
	
	List<Transaction> getAllUserTransactionsByAccountId(int id);
	
	public void withdraw(Account a, double cash);
	
	public void deposit(Account a, double dep);
	
	public void transfer(Account a, double fund, String username);

}
