package com.example.services;

import java.sql.SQLException;
import java.util.List;

import com.example.dao.AccountDao;
import com.example.exceptions.AccountAlreadyExistsException;
import com.example.exceptions.AccountDoesNotExistException;
import com.example.logging.Logging;
import com.example.models.Account;

public class AccountServices {

	private AccountDao aDao;
	
	public AccountServices(AccountDao aDao) {
		this.aDao = aDao;
	}
	
	public List<Account> getAllAcounts()
	{
		return aDao.getAllAcounts();
	}

//	public List<Account> getAllOpenAcount() {
//		return aDao.getAllOpenAcount();
//	}
	
	public Account findAccount(String username)
	{
		Account a = null;
		
			a = aDao.getAccountByUsername(username);
			if(a.getAccountId()==0) {
				Logging.logger.warn("User tried opening account that doesnt exist");
				throw new AccountDoesNotExistException();
			}	
			else {
				Logging.logger.info("Account attatched to user was found");
				return a;
			}
	}
	
	public Account createAccount(String username) {
		
		Account a = new Account(username);
		
		try {
			aDao.createAccount(a);
			Logging.logger.info("New Account was created");
		} catch (SQLException e) {
			e.printStackTrace();
			Logging.logger.warn("Account created that already exists in the database");
			throw new AccountAlreadyExistsException();
		}
		
		return a;
	}
	
	public void delete(Account a)
	{
		try {
		aDao.deleteAccount(a);
		Logging.logger.info("Account deleted");
		} catch(AccountDoesNotExistException e) {
			e.printStackTrace();
			Logging.logger.warn("Account deleted that didn't exist");

		}
		
		
	}
	
	public void updateAccount(Account a)
	{
		try {
			aDao.updateAccount(a);
			Logging.logger.info("Account updated");
			} catch(AccountDoesNotExistException e) {
				e.printStackTrace();
				Logging.logger.warn("Account updated that didn't exist");

			}
		
	}
	

	public String checkStatus(Account a) {
		if(a.getStatus().equals("open"))
		{
			return "open";
		}
		if(a.getStatus().equals("approved")) {
		return "approved";
		}
		else
		{
			return "denied";
		}
	}
	
	public void updateStatus(Account a, String stat)
	{
		a.setStatus(stat);
		
		aDao.updateAccount(a);
		
	}
	
}
