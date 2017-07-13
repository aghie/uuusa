package org.grupolys.samulan.util;

import org.grupolys.samulan.analyser.operation.Operation;

public class QueuedOperationInformation {
	
	private short levelsUp;
	private Operation operation;
	
	
	public QueuedOperationInformation(short levelsup, Operation operation){
		this.levelsUp = levelsup;
		this.operation = operation;
	}
	
	public QueuedOperationInformation(QueuedOperationInformation qoi){
		/**
		 * Copy constructor
		 */
		this.levelsUp = qoi.levelsUp;
		this.operation = qoi.operation; //shallow copy
	}
	
	public short getLevelsUp() {
		return levelsUp;
	}
	public void setLevelsUp(short levelsUp) {
		this.levelsUp = levelsUp;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public String toString(){
		return operation+": levelsup = "+levelsUp;
	}
	
	
}
