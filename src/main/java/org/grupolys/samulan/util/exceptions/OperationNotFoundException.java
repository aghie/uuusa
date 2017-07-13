package org.grupolys.samulan.util.exceptions;

public class OperationNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4444170956775778423L;
	public OperationNotFoundException() { super(); }
	public OperationNotFoundException(String message) { super(message); }
	public OperationNotFoundException(String message, Throwable cause) { super(message, cause); }
	public OperationNotFoundException(Throwable cause) { super(cause); }

}

