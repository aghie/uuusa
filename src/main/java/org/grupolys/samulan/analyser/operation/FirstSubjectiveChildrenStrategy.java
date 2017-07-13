package org.grupolys.samulan.analyser.operation;

import java.util.ArrayList;
import java.util.List;

import org.grupolys.samulan.util.OperationValue;
import org.grupolys.samulan.util.SentimentInformation;

public class FirstSubjectiveChildrenStrategy implements ScopeStrategy {
	
	private short reference = 0;
	private boolean rightSide = false;
	
	public FirstSubjectiveChildrenStrategy(boolean rightSide){
		this.rightSide = rightSide;
	}
	

	public short getReference() {
		return reference;
	}


	public void setReference(short reference) {
		this.reference = reference;
	}

	
	private boolean isOnRightSide(short address){
		
		if (this.rightSide) return address > this.reference;
		return address < this.reference;
	}

	@Override
	public OperationValue apply(SentimentInformation head, List<SentimentInformation> children, Operation operation) {
		List<SentimentInformation> newChildren = new ArrayList<SentimentInformation>();
		SentimentInformation auxChild;
		boolean branchFound = false;
		//System.out.println("Entra FirstSubjective");
		for (SentimentInformation siChild: children){
			auxChild = new SentimentInformation(siChild);
			if (!branchFound){
			//	System.out.println("auxChild: "+auxChild);
				if ((siChild.getSemanticOrientation() != 0) && (isOnRightSide(siChild.getSentimentDependencyNode().getAddress() ))){
				//	System.out.println("matched: "+auxChild);
					operation.updateSentiment(auxChild);
				//	System.out.println("matched later: "+auxChild);
					branchFound = true;
				}
				//System.out.println("matched later2: "+auxChild);
			}
			newChildren.add(auxChild);
		}
		
		if (!branchFound) return null;
		return new OperationValue(new SentimentInformation(head),newChildren);
		
	}
	
	
	@Override
	public String toString(){
		return FIRST_SUBJECTIVE_BRANCH+"(from address:  "+this.reference+")";
	}

}
