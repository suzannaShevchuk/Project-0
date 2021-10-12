package com.revature;

import java.util.Scanner;

import com.example.dao.AccountDao;
import com.example.dao.AccountDaoDb;
import com.example.dao.TransactionDao;
import com.example.dao.TransactionDaoDb;
import com.example.dao.UserDao;
import com.example.dao.UserDaoDB;
import com.example.models.User;
import com.example.services.AccountServices;
import com.example.services.TransactionServices;
import com.example.services.UserService;

public class BankDriver {

	private static UserDao uDao = new UserDaoDB();
	private static AccountDao aDao = new AccountDaoDb();
	private static TransactionDao tDao = new TransactionDaoDb();
	private static UserService uServ = new UserService(uDao);
	private static AccountServices aServ = new AccountServices(aDao);
	private static TransactionServices tServ = new TransactionServices(tDao);
	
	public BankDriver() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		//System.out.println(uDao.getAllUsers());
		
		//System.out.println(aDao.getAllAcounts());

	Scanner in = new Scanner(System.in);
		
		//This will be used to control our loop
		boolean done = false;
		
		User u = null;
		
		while(!done) {
			
			if(u == null) {
				System.out.println("Login or Signup? Press 1 to login, press 2 to signup");
				int choice = Integer.parseInt(in.nextLine());
				if(choice == 1) {
					System.out.print("Please enter your username: ");
					String username = in.nextLine();
					System.out.print("Please enter you password: ");
					String password = in.nextLine();
					
					try {
						u = uServ.signIn(username, password);
						System.out.println("Welcome " + u.getUsername());
						
					} catch(Exception e) {
						System.out.println("Username or password was incorrect");
					}
				} else {
					System.out.print("Please enter you first name: ");
					String first = in.nextLine();
					System.out.print("Please enter your last name: ");
					String last = in.nextLine();
					System.out.print("Please enter your email: ");
					String email = in.nextLine();
					System.out.print("Please enter your username: ");
					String username = in.nextLine();
					System.out.print("Please enter your password: ");
					String password = in.nextLine();
					System.out.print("Are you Customer, Admin, or Employee: ");
					String userType = in.nextLine();
					
					try {
						u = uServ.signUp(first, last, email, username, password, userType);
						System.out.println("You may now sign in with the username: " + u.getUsername());
						System.out.print("Please enter your username: ");
						String username2 = in.nextLine();
						System.out.print("Please enter you password: ");
						String password2 = in.nextLine();
						
						try {
							u = uServ.signIn(username2, password2);
							System.out.println("Welcome " + u.getUsername());
							
						} catch(Exception e) {
							System.out.println("Username or password was incorrect");
						}
					} catch(Exception e) {
						e.printStackTrace();
						System.out.println("Sorry the username or email is already taken");
						System.out.println("Please try signing up with a different one");
					}
				}
			}
			
		}
		
		
		boolean done2 = true;
		int choice2;
	
		while(!done2)
		{
			
			if(u.getUserType() == "Customer")
			{
				
			}
			
			
			if(u.getUserType() == "Admin")
			{
				
			}
			
			
			if(u.getUserType() == "Employee")
			{
				
			}
			
			
		}
		
		
	}

}
