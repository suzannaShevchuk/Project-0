package com.example.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.exceptions.NegativeAmountExcpetion;
import com.example.exceptions.OverDrawException;
import com.example.logging.Logging;
import com.example.models.Account;
import com.example.models.Transaction;
import com.example.utils.ConnectionUtil;

public class TransactionDaoDb implements TransactionDao{

	ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();
	
	public TransactionDaoDb() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Transaction> getAllTransactions() {
		List<Transaction> transactionList = new ArrayList<Transaction>();

		try {
			//Make the actual connection to the db
			Connection con = conUtil.getConnection();
			
			//Create a simple statement
			String sql = "SELECT * FROM transactions";
			
			//We need to create a statement with the sql string
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			//We have to loop through the ResultSet and create objects based off the return
			while(rs.next()) {
				transactionList.add(new Transaction(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4)));
			}
			
			return transactionList;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;	
	}

	@Override
	public List<Transaction> getAllUserTransactionsByAccountId(int id) {
		List<Transaction> transactionList = new ArrayList<Transaction>();

		try {
			//Make the actual connection to the db
			Connection con = conUtil.getConnection();
			
			//Create a simple statement
			String sql = "SELECT * FROM transactions WHERE transactions.account_id = '" + id + "'";
			
			//We need to create a statement with the sql string
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			//We have to loop through the ResultSet and create objects based off the return
			while(rs.next()) {
				transactionList.add(new Transaction(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4)));
			}
			
			return transactionList;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;	
	}

	@Override
	public void withdraw(Account a, double cash) {
		
		AccountDaoDb adb = new AccountDaoDb();
		
		double actualBalance = a.getBalance();
		
		if(actualBalance<cash)
		{
			Logging.logger.warn("User tried to withdraw more money than they available in account");
			throw new OverDrawException();
		}
		else {
			double newBal = actualBalance-cash;
			a.setBalance(newBal);
			adb.updateAccount(a);
		}			
	}
	

	@Override
	public void deposit(Account a, double dep) {
		AccountDaoDb adb = new AccountDaoDb();
		
	double actualBalance = a.getBalance();
		
		if(dep<0)
		{
			Logging.logger.warn("User tried to deposit negative amount");
			throw new NegativeAmountExcpetion();
		}
		else {
			double newBal = actualBalance+dep;
			a.setBalance(newBal);
			adb.updateAccount(a);
		}
	}

	@Override
	public void transfer(Account a, double fund, String username) {
		AccountDaoDb adb = new AccountDaoDb();
		
		withdraw(a, fund);
		
		Account too = adb.getAccountByUsername(username);
		
		deposit(too, fund);
		
		adb.updateAccount(a);
		adb.updateAccount(too);
	}

}
