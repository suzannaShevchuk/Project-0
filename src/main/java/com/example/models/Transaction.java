package com.example.models;

public class Transaction {

	private int id;
	private int accountId;
	private String transactionType;
	private double money;
	
	public Transaction() {

		
	}

	public Transaction(int id, int accountId, String transactionType, double money) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.transactionType = transactionType;
		this.money = money;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", accountId=" + accountId + ", transactionType=" + transactionType
				+ ", money=" + money + "]";
	}

	
	
}
