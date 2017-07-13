package org.grupolys.samulan.analyser.operation;

import java.util.ArrayList;
import java.util.List;

import org.grupolys.samulan.util.OperationValue;
import org.grupolys.samulan.util.SentimentInformation;

/**
 * 
 * @author David Vilares
 *
 */
public class BranchStrategy extends AbstractStrategy implements
		ScopeStrategy {
	
	private String scope;
	
	public BranchStrategy(String scope){
		this.scope = scope;
	}
	
	
	public OperationValue apply(SentimentInformation head,
			List<SentimentInformation> children,
			Operation operation) {
		
		List<SentimentInformation> newChildren = new ArrayList<SentimentInformation>();
		SentimentInformation auxChild;
		boolean branchFound = false;
	
		for (SentimentInformation siChild: children){
			String deprel = siChild.getSentimentDependencyNode().getDeprel();
			auxChild = new SentimentInformation(siChild);
			if (deprel.equals(this.scope)){
				operation.updateSentiment(auxChild);
				branchFound = true;
			}
			newChildren.add(auxChild);
		}
		
		if (!branchFound) return null;
		return new OperationValue(new SentimentInformation(head),newChildren);
		
	}
	
	@Override
	public String toString(){
		return BRANCH+"("+this.scope+")";
	}


}
