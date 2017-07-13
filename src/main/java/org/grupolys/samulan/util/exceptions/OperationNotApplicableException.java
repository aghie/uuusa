package org.grupolys.samulan.util.exceptions;

public class OperationNotApplicableException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6969158882596046432L;
	public OperationNotApplicableException() { super(); }
	public OperationNotApplicableException(String message) { super(message); }
	public OperationNotApplicableException(String message, Throwable cause) { super(message, cause); }
	public OperationNotApplicableException(Throwable cause) { super(cause); }
}
