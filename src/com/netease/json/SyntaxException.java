package com.netease.json;
public class SyntaxException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3280162605911298279L;

	public SyntaxException() {
		// TODO Auto-generated constructor stub
	}

	public SyntaxException(String msg) {
		super(msg);
	}

	public SyntaxException(Throwable cause) {
		super(cause);
	}

	public SyntaxException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
