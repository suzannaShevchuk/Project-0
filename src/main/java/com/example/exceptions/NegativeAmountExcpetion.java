package com.example.exceptions;

public class NegativeAmountExcpetion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NegativeAmountExcpetion() {
		super("Cannot deposit negative amount");
	}

}
