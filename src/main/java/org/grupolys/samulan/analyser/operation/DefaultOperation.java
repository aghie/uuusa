package org.grupolys.samulan.analyser.operation;

import org.grupolys.samulan.rule.Rule;



public class DefaultOperation extends AbstractOperation implements Operation {


	
	public DefaultOperation(Rule rule){
		super(rule,null);
	}

	public String getOperationName() {
		return DEFAULT;
	}

	public int getPriority() {
		return DEFAULT_PRIORITY;
	}
	


	public float calculate(float sentiment) {
		System.out.println("DefaultOperation: calculate");
		return sentiment;
	}

	@Override
	public boolean equals(Object obj) {
		return (this.getOperationName().equals(((Operation) obj).getOperationName()));
	}
	
	@Override
	public String toString(){
		return DEFAULT;
	}

}
