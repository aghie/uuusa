package org.grupolys.samulan.analyser.operation;

import java.util.ArrayList;
import java.util.List;

import org.grupolys.samulan.util.OperationValue;
import org.grupolys.samulan.util.QueuedOperationInformation;
import org.grupolys.samulan.util.SentimentInformation;

public class ChildrenStrategy extends AbstractStrategy implements
		ScopeStrategy {
	
	

	public OperationValue apply(SentimentInformation head,
			List<SentimentInformation> children,
			Operation operation) {
		
		//TODO this is different from the original behaviour of the unsupervised system
		List<SentimentInformation> newChildren = new ArrayList<SentimentInformation>();
		SentimentInformation auxChild;
		SentimentInformation auxHead = new SentimentInformation(head);

		for (SentimentInformation siChild: children){
			//TODO still not doing what it is supposed to do
			auxChild = new SentimentInformation(siChild); //copy constructor
			newChildren.add(auxChild);
		}
		operation.updateSentiment(auxHead);
		return new OperationValue(auxHead,newChildren);
	}
	
	@Override
	public String toString(){
		return CHILDREN;
	}


}
