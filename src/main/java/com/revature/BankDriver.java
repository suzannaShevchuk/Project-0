package com.revature;

import java.util.Scanner;

import com.example.dao.AccountDao;
import com.example.dao.AccountDaoDb;
import com.example.dao.TransactionDao;
import com.example.dao.TransactionDaoDb;
import com.example.dao.UserDao;
import com.example.dao.UserDaoDB;
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
						}
					} catch(Exception e) {
						e.printStackTrace();
						System.out.println("Sorry the username or email is already taken");
						System.out.println("Please try signing up with a different one");
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
				
				System.out.println("1 for Transaction History, 2 for Deposit, 3 for Withdrawl, 4 for Fund Transfer");
				
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
				System.out.println("9. Delete account");
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
				case 6:
					
					break;
				case 7:
					
					break;
				case 8:
					
					break;
				case 9:
					System.out.println("Enter the username of the account you want to delete");
					String theUser3 = in.nextLine();
					Account deleteUser = aServ.findAccount(theUser3);
					aServ.delete(deleteUser);
					

					
					break;
				case 10:
					done2 = true;
					done = false;
					//System.exit(0);
					break;
				default:
					System.out.println("Please enter a valid choice 1-10");
					break;
				}
				
			}
			
			
			if(u.getUserType().equals("Employee"))
			{
				
			}
			
			
		}
		
		
	}

}
