package org.grupolys.samulan.analyser.operation;

import java.util.List;
import java.util.stream.Collectors;

import org.grupolys.samulan.util.Dictionary;
import org.grupolys.samulan.util.OperationValue;
import org.grupolys.samulan.util.SentimentInformation;

public class HeadStrategy extends AbstractStrategy implements ScopeStrategy {

	/**
	 * It applies a @see es.udc.fi.dc.lys.Operation to the specified head node.
	 * The children nodes remain unchanged.
	 * @param head
	 * @param children
	 * @param operation
	 * @return a new OperationValue if the operation was successfully applied or
	 * null in case the head had an unexpected value (zero).
	 */
	
	private boolean negateObjective = false;
	
	
	public HeadStrategy(){
		
	}
	
	public HeadStrategy(boolean negateObjective){
		this.negateObjective = negateObjective;
	}
	
	public OperationValue apply(SentimentInformation head,
			List<SentimentInformation> children,
			Operation operation) {
		SentimentInformation auxHead;
		
		if (head.getSemanticOrientation() == 0 && !this.negateObjective) {
			return null;
		}
		else{
			auxHead = new SentimentInformation(head);
			operation.updateSentiment(auxHead);
			return new OperationValue(auxHead,children.stream()
					.map( (SentimentInformation c) -> (new SentimentInformation(c)))
					.collect(Collectors.toList()));
		}
	}
	
	@Override
	public String toString(){
		return HEAD;
	}
}
