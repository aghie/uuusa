package org.grupolys.samulan.util;

import java.util.List;

import org.grupolys.samulan.analyser.operation.AbstractStrategy;
import org.grupolys.samulan.analyser.operation.Operation;
import org.grupolys.samulan.analyser.operation.ScopeStrategy;

public class OperationValue {

	private SentimentInformation head;
	private List<SentimentInformation> children;
	private Operation appliedOperation;
	private ScopeStrategy appliedStrategy;
	
	public OperationValue(SentimentInformation head,
			List<SentimentInformation> children) {
		super();
		this.head = head;
		this.children = children;
		this.appliedOperation = null;
		this.appliedStrategy  = null;
	}
	
	
	public SentimentInformation getHead() {
		return head;
	}
	
	public void setHead(SentimentInformation head) {
		this.head = head;
	}
	
	public List<SentimentInformation> getChildren() {
		return children;
	}
	
	public void setChildren(List<SentimentInformation> children) {
		this.children = children;
	}
	
	public Operation getAppliedOperation() {
		return appliedOperation;
	}

	public void setAppliedOperation(Operation appliedOperation) {
		this.appliedOperation = appliedOperation;
	}

	public ScopeStrategy getAppliedStrategy() {
		return appliedStrategy;
	}

	public void setAppliedStrategy(ScopeStrategy appliedStrategy) {
		this.appliedStrategy = appliedStrategy;
	}

	public String appliedOperation(){
		if (this.appliedOperation != null && this.appliedOperation.toString() != Operation.DEFAULT){
			return this.appliedOperation+" to "+this.appliedStrategy;
		}
		else return null;
			
	}

	public String toString(){
		return this.head+" "+this.children;
	}
	
}
