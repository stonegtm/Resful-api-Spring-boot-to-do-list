package com.codestone.mytodo;

public class TodoUnAuthenException extends RuntimeException {

	public TodoUnAuthenException() {
		super("token Invalid");
	}

}
