package com.example.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.logging.Logging;
import com.example.models.Account;
import com.example.utils.ConnectionUtil;

public class AccountDaoDb implements AccountDao {

	ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();

	
	public AccountDaoDb() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Account> getAllAcounts() {
     List<Account> accountList = new ArrayList<Account>();
		
		try {
			//Make the actual connection to the db
			Connection con = conUtil.getConnection();
			
			//Create a simple statement
			String sql = "SELECT * FROM accounts";
			
			//We need to create a statement with the sql string
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			//We have to loop through the ResultSet and create objects based off the return
			while(rs.next()) {
				accountList.add(new Account(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
			}
			
			return accountList;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Account getAccountByUsername(String username) {

		Account account = new Account();
		
		try {
			Connection con = conUtil.getConnection();
			
			String sql = "SELECT * FROM accounts WHERE accounts.username = '" + username + "'";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				account.setAccountId(rs.getInt(1));
				account.setBalance(rs.getDouble(2));
				account.setUsername(rs.getString(3));
				account.setStatus(rs.getString(4));
			}
			
			return account;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	@Override
//	public List<Account> getAllOpenAcount() {
//     List<Account> accountList = new ArrayList<Account>();
//		
//		try {
//			//Make the actual connection to the db
//			Connection con = conUtil.getConnection();
//			
//			//Create a simple statement
//			String sql = "SELECT * FROM accounts WHERE status = ?";
//			
//			CallableStatement cs = con.prepareCall(sql);
//			cs.setString(1, "open");
//			
//			//We need to create a statement with the sql string
//			Statement s = con.createStatement();
//			ResultSet rs = s.executeQuery(sql);
//			
//			
//			//We have to loop through the ResultSet and create objects based off the return
//			while(rs.next()) {
//				accountList.add(new Account(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
//			}
//			
//			return accountList;
//			
//		} catch(SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
		

	@Override
	public void createAccount(Account a) throws SQLException {
		try {
			Connection con = conUtil.getConnection();
			
			//We first need to set autocommit to false
			con.setAutoCommit(false);
			String sql = "call create_account(?,?,?)";
			
			CallableStatement cs = con.prepareCall(sql);
			
			cs.setDouble(1, a.getBalance());
			cs.setString(2, a.getUsername());
			cs.setString(3, a.getStatus());
			
			cs.execute();
			
			con.setAutoCommit(true);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}		
	}
	

	@Override
	public void updateAccount(Account a) {
		
		try {
			Connection con = conUtil.getConnection();
			String sql = "UPDATE accounts SET balance = ?, username = ?, status = ? WHERE id =  ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
		
			ps.setDouble(1, a.getBalance());
			ps.setString(2, a.getUsername());
			ps.setString(3, a.getStatus());
			ps.setInt(4, a.getAccountId());
			
		    ps.execute();
		    //con.setAutoCommit(true);
			//con.commit();
		    
			Logging.logger.info("account status updated");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateStatus(Account a, String stat)
	{
		a.setStatus(stat);
		
		updateAccount(a);
	}
	
	@Override
	public void deleteAccount(Account a) {

		try {
			Connection con = conUtil.getConnection();
			String sql = "DELETE FROM transactions WHERE account_id IN (SELECT id from accounts);"
					+ "DELETE FROM accounts WHERE accounts.id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, a.getAccountId());
			ps.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	

}
