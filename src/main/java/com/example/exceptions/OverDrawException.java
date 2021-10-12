package com.example.exceptions;

public class OverDrawException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OverDrawException() {
		super("User tried to withdraw more money than is available");
	}

}
