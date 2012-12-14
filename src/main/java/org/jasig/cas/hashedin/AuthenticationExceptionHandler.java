package org.jasig.cas.hashedin;

public class AuthenticationExceptionHandler extends Exception {
	
	private String msg;

	public AuthenticationExceptionHandler(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "AuthenticationExceptionHandler [msg=" + msg + "]";
	}


}
