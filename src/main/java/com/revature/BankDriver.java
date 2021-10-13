package com.revature;

import java.sql.SQLException;
import java.util.Scanner;

import com.example.dao.AccountDao;
import com.example.dao.AccountDaoDb;
import com.example.dao.TransactionDao;
import com.example.dao.TransactionDaoDb;
import com.example.dao.UserDao;
import com.example.dao.UserDaoDB;
import com.example.exceptions.NegativeAmountExcpetion;
import com.example.exceptions.OverDrawException;
import com.example.models.Account;
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
				System.out.println("1. Login with existing account");
				System.out.println("2. Apply for an account");
				int choice = Integer.parseInt(in.nextLine());
				if(choice == 1) {
					System.out.print("Please enter your username: ");
					String username = in.nextLine();
					System.out.print("Please enter you password: ");
					String password = in.nextLine();
					
					try {
						u = uServ.signIn(username, password);
						System.out.println("Welcome " + u.getFirstName());
						done = true;
						
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
						aServ.createAccount(username);
						System.out.println("You may now sign in with the username: " + u.getUsername());
						System.out.println("You applied for an account, allow 24 hours for request to be assesed by bank");
						System.out.println();
						System.out.print("Please enter your username: ");
						String username2 = in.nextLine();
						System.out.print("Please enter you password: ");
						String password2 = in.nextLine();
						
						try {
							u = uServ.signIn(username2, password2);
							System.out.println("Welcome " + u.getFirstName());
							done = true;
						} catch(Exception e) {
							System.out.println("Username or password was incorrect");
							u = null;
						}
					} catch(Exception e) {
						e.printStackTrace();
						System.out.println("Sorry the username or email is already taken");
						System.out.println("Please try signing up with a different one");
						u = null;
					}
				}
			}
			
		}
		
		
		boolean done2 = false;
		
		while(!done2)
		{
			//System.out.println(u.getUserType());
			if(u.getUserType().equals("Customer"))
			{
								
				Account temp = aServ.findAccount(u.getUsername());
				
				if(temp.getStatus().equals("open"))
				{
					System.out.println("The bank has not yet accepted your account request, check again later.");
					break;
				}
				else if(temp.getStatus().equals("approved")){
				System.out.println(aServ.findAccount(u.getUsername()));
				System.out.println(u);
				
				System.out.println("Enter a number for what you want to do.");
				System.out.println("1. View all your transactions");
				System.out.println("2. Deposit");
				System.out.println("3. Withdraw");
				System.out.println("4. Transfer funds");
				System.out.println("5. Update account");
				System.out.println("6. Logout and close");	
				
				int choiceCustomer = Integer.parseInt(in.nextLine());

				
				switch(choiceCustomer) {
				case 1:
					System.out.println(tServ.getAllTransactionsUser(temp.getAccountId()));
					break;
					
				case 2:
					
					System.out.println("Enter the amount you will deposit");
					double choice3 = Double.parseDouble(in.nextLine());
					if(choice3<0) { System.out.println("Cannot deposit negative amount"); }
					else {
					try {
						tServ.deposit(temp, choice3);
					} catch (SQLException e) {
						System.out.println("Unable to deposit, make sure input is valid");
						e.printStackTrace();
					}
					  }
					
					break;
					
				case 3:
					
					System.out.println("Enter the amount you will withdraw");
					double choice4 = Double.parseDouble(in.nextLine());
					if(choice4<0) { System.out.println("Cannot withdraw negative amount"); }
					else {
					try {
						tServ.withdraw(temp, choice4);
					} catch (OverDrawException o) {
						System.out.println("Unable to withdraw more money than in account");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					break;
					
				case 4:
					
					System.out.println("Enter the amount you will transfer");
					double choice5 = Double.parseDouble(in.nextLine());
					if(choice5<0) { System.out.println("Cannot transfer negative amount"); }
					else {
					try {
						tServ.withdraw(temp, choice5);
					} catch (SQLException e) {
						System.out.println("Unable to withdraw, make sure input is valid");
						e.printStackTrace();
					}
					}
					
					System.out.println("Enter the username of the account you want to transfer the funds into");
					String theUser7 = in.nextLine();
					Account deposit2 = aServ.findAccount(theUser7);
					try {
						tServ.deposit(deposit2, choice5);
					} catch (SQLException e) {
						System.out.println("Unable to deposit, make sure input is valid");
						e.printStackTrace();
					}
					
					break;
				case 5:
					System.out.println("1. Edit first name");
					System.out.println("2. Edit last name");
					System.out.println("3. Edit first email");
					System.out.println("4. Edit first password");
					int choiceEdit = Integer.parseInt(in.nextLine());
					switch(choiceEdit) {
					case 1:
						System.out.println("Enter updated first name");
						String newName = in.nextLine();
						u.setFirstName(newName);
						uDao.updateUser(u);
						break;
					case 2:
						System.out.println("Enter updated last name");
						String newLastName = in.nextLine();
						u.setLastName(newLastName);
						uDao.updateUser(u);
						break;
					case 3:
						System.out.println("Enter updated email");
						String newEmail = in.nextLine();
						u.setEmail(newEmail);
						uDao.updateUser(u);
						break;
					case 4:
						System.out.println("Enter updated password");
						String newPass = in.nextLine();
						u.setPassword(newPass);
						uDao.updateUser(u);
						break;
					default:
						System.out.println("Please enter valid choice 1-4");
						break;
					}
					
					break;					
				case 6:
					System.out.println("User logged out.");
					System.exit(0);
					break;
				default:
					System.out.println("Please enter a valid choice 1-6");
					break;
				}
				
				}
				else if(temp.getStatus().equals("denied"))
				{
					System.out.println("Your account has been denied");
					System.exit(0);
				}
			}
			
			
			if(u.getUserType().equals("Admin"))
			{
				
				System.out.println("Enter a number for what you want to do.");
				System.out.println("1. View all accounts");
				System.out.println("2. View all transactions");
				System.out.println("3. View specific account");
				System.out.println("4. View specific accounts transactions");
				System.out.println("5. Update status of an account");
				System.out.println("6. Deposit into an account");
				System.out.println("7. Withdraw from an account");
				System.out.println("8. Transfer from one account to another");
				System.out.println("9. Delete/Cancel account");
				System.out.println("10. Logout and close");
				
				int choice = Integer.parseInt(in.nextLine());
				
				switch(choice)
				{
				case 1:
					System.out.println(aServ.getAllAcounts());
					break;
				case 2:
					System.out.println(tServ.getAllTransactions());
					break;
				case 3:
					System.out.println("Enter the username of the account you want to find");
					String theUser = in.nextLine();
					System.out.println(aServ.findAccount(theUser));
					break;
				case 4:
					System.out.println("Enter the id of the account you want to see transactions for");
					int choice2 = Integer.parseInt(in.nextLine());
					System.out.println(tServ.getAllTransactionsUser(choice2));
					break;
				case 5:
					System.out.println("Enter the username of the account you want to update the status for");
					String theUser2 = in.nextLine();
					Account statsChange = aServ.findAccount(theUser2);
					System.out.println("Type approved or denied.");
					String theChange = in.nextLine();
					aServ.updateStatus(statsChange, theChange);
					System.out.println(statsChange.getStatus());
					break;
				case 6: //deposit
					System.out.println("Enter the username of the account you want to deposit into");
					String theUser4 = in.nextLine();
					Account deposit = aServ.findAccount(theUser4);
					System.out.println("Enter the amount you will deposit");
					double choice3 = Double.parseDouble(in.nextLine());
					if(choice3<0) { System.out.println("Cannot deposit negative amount"); }
					else {
					try {
						tServ.deposit(deposit, choice3);
					} catch (OverDrawException o) {
						System.out.println("Unable to withdraw more money than in account");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					break; 
				case 7:     //withdraw
					System.out.println("Enter the username of the account you want to withdraw from");
					String theUser5 = in.nextLine();
					Account withdraw = aServ.findAccount(theUser5);
					System.out.println("Enter the amount you will withdraw");
					double choice4 = Double.parseDouble(in.nextLine());
					if(choice4<0) { System.out.println("Cannot withdraw negative amount"); }
					else {
					try {
						tServ.withdraw(withdraw, choice4);
					} catch (SQLException e) {
						System.out.println("Unable to wtihdraw, make sure input is valid");
						e.printStackTrace();
					} 
					}
					break;
				case 8: //transfer
					
					System.out.println("Enter the username of the account you want to transfer from");
					String theUser6 = in.nextLine();
					Account withdraw2 = aServ.findAccount(theUser6);
					System.out.println("Enter the amount you will transfer");
					double choice5 = Double.parseDouble(in.nextLine());
					if(choice5<0) { System.out.println("Cannot transfer negative amount"); }
					else {
					try {
						tServ.withdraw(withdraw2, choice5);
					} catch (SQLException e) {
						System.out.println("Unable to wtihdraw, make sure input is valid");
						e.printStackTrace();
					}
					}
					System.out.println("Enter the username of the account you want to transfer the funds into");
					String theUser7 = in.nextLine();
					Account deposit2 = aServ.findAccount(theUser7);
					try {
						tServ.deposit(deposit2, choice5);
					} catch (SQLException e) {
						System.out.println("Unable to deposit, make sure input is valid");
						e.printStackTrace();
					}
					
					break;
				case 9:
					System.out.println("Enter the username of the account you want to delete");
					String theUser3 = in.nextLine();
					Account deleteUser = aServ.findAccount(theUser3);
					aServ.delete(deleteUser);
					break;
				case 10:
					System.out.println("User logged out.");
					System.exit(0);
					break;
				default:
					System.out.println("Please enter a valid choice 1-10");
					break;
				}
				
			}
			
			
			if(u.getUserType().equals("Employee"))
			{
				
				System.out.println("Enter a number for what you want to do.");
				System.out.println("1. View all accounts");
				System.out.println("2. View all transactions");
				System.out.println("3. View specific account");
				System.out.println("4. View specific accounts transactions");
				System.out.println("5. Update status of an account");
				System.out.println("6. View all users");
				System.out.println("7. Logout and close");	

				
				int choiceEmployee = Integer.parseInt(in.nextLine());
				
				switch(choiceEmployee)
				{
				case 1:
					System.out.println(aServ.getAllAcounts());
					break;
				case 2:
					System.out.println(tServ.getAllTransactions());
					break;
				case 3:
					System.out.println("Enter the username of the account you want to find");
					String theUser = in.nextLine();
					System.out.println(aServ.findAccount(theUser));
					break;
				case 4:
					System.out.println("Enter the id of the account you want to see transactions for");
					int choice2 = Integer.parseInt(in.nextLine());
					System.out.println(tServ.getAllTransactionsUser(choice2));
					break;
				case 5:
					System.out.println("Enter the username of the account you want to update the status for");
					String theUser2 = in.nextLine();
					Account statsChange = aServ.findAccount(theUser2);
					System.out.println("Type approved or denied.");
					String theChange = in.nextLine();
					aServ.updateStatus(statsChange, theChange);
					System.out.println(statsChange.getStatus());
					break;
				case 6:
					System.out.println(uDao.getAllUsers());
					break;
				case 7:
					System.out.println("User logged out.");
					System.exit(0);
					break;
				default:
					System.out.println("Please enter a valid choice 1-10");
					break;
				
			    }
			
			}
		}
		
		
	}

}
