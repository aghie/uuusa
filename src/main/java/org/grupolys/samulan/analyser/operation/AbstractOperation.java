package org.grupolys.samulan.analyser.operation;


import java.util.List;

import org.grupolys.samulan.rule.Rule;
import org.grupolys.samulan.util.OperationValue;
import org.grupolys.samulan.util.SentimentInformation;

public abstract class AbstractOperation implements Operation {

	
	private Rule rule;
	private ScopeStrategy strategy;
	
	public AbstractOperation(Rule rule, ScopeStrategy strategy) {
		this.rule = rule;
		this.strategy = strategy;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}
	
	public ScopeStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(ScopeStrategy strategy) {
		this.strategy = strategy;
	}

	
	public int getPriority() {
		return this.rule.getPriority();
	}
	
	
	
	
	protected void checkWarnings(SentimentInformation current){}
	
	public boolean isSemanticOrientationOperation(){
		//TODO now we could remove this method
		return true;
	}
	

	public void updateSentiment(SentimentInformation si){
		si.setSemanticOrientation(this.calculate(si.getSemanticOrientation()));
		si.setPositiveSentiment(Math.max(this.calculate(si.getPositiveSentiment()),SentimentInformation.SENTISTRENGTH_NEUTRAL));
		si.setNegativeSentiment(Math.min(this.calculate(si.getNegativeSentiment()),SentimentInformation.SENTISTRENGTH_NEUTRAL));
	}
	
	public OperationValue apply(SentimentInformation head,
			List<SentimentInformation> children){
		OperationValue ov = new OperationValue(head,children);  
		ov.setAppliedOperation(this);
		ov.setAppliedStrategy(this.getStrategy());
		return ov;
	}
	

	
}