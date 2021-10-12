package com.example.services;

import java.sql.SQLException;

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
	
}
