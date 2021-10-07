package com.example.services;

import java.sql.SQLException;

import com.example.dao.UserDao;
import com.example.exceptions.InvalidCredentialsException;
import com.example.exceptions.UserDoesNotExistException;
import com.example.exceptions.UsernameAlreadyExistsException;
import com.example.logging.Logging;
import com.example.models.User;

public class UserService {

	private UserDao uDao;
	
	public UserService(UserDao u) {
		this.uDao = u;
	}
	
	
	public User signUp(String first, String last, String email, String username, String password, String userType) throws UsernameAlreadyExistsException {
		
		User u = new User(first, last, email, username, password, userType);
		
		try {
			uDao.createUser(u);
			Logging.logger.info("New user was registered");
		} catch (SQLException e) {
			e.printStackTrace();
			Logging.logger.warn("Username created that already exists in the database");
			throw new UsernameAlreadyExistsException();
		}
		
		return u;
	}
	
	
	public User signIn(String username, String password) throws UserDoesNotExistException, InvalidCredentialsException{
		
		User u = uDao.getUserByUsername(username);
		
		if(u.getId() == 0) {
			Logging.logger.warn("User tried logging in that does not exist");
			throw new UserDoesNotExistException();
		}
		else if(!u.getPassword().equals(password)) {
			Logging.logger.warn("User tried to login with invalid credentials");
			throw new InvalidCredentialsException();
		}
		else {
			Logging.logger.info("User was logged in");
			return u;
		}
		
	}
	
	

}
