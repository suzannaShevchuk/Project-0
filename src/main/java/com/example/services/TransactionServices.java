package com.example.services;


import java.sql.SQLException;
import java.util.List;

import com.example.dao.TransactionDao;
import com.example.logging.Logging;
import com.example.models.Account;
import com.example.models.Transaction;

public class TransactionServices {

	private TransactionDao tDao;
	
	public TransactionServices(TransactionDao tDao) {
		this.tDao = tDao;
	}

	public List<Transaction> getAllTransactions() {
		return tDao.getAllTransactions();
	}
	
	public List<Transaction> getAllTransactionsUser(int id){
		return tDao.getAllUserTransactionsByAccountId(id);
	}
	
	public void deposit(Account a, double deposit) throws SQLException
	{
		tDao.deposit(a, deposit);
		Logging.logger.info("User successfully deposited");
	}
	
	public void withdraw(Account a, double withdraw) throws SQLException
	{
		tDao.withdraw(a, withdraw);
		Logging.logger.info("User withdrew from account successfully");		
	}
	
	public void transfer(Account a, double fund, String username) throws SQLException {
		tDao.transfer(a, fund, username);
		Logging.logger.info("User " + a.getUsername() + "transferred succesfully to " + username);
	}
	
	
	
	
}
