package org.grupolys.samulan.analyser.operation;

import java.util.List;

import org.grupolys.samulan.rule.Rule;
import org.grupolys.samulan.util.OperationValue;
import org.grupolys.samulan.util.SentimentInformation;


public class ShiftOperation extends AbstractOperation implements
		Operation {

	public static final float DEFAULT_SHIFT_VALUE = 4;
	public static final String SHIFT = "SHIFT";
	private ShiftOperation successor;
	private float shiftValue;
	private boolean alwaysShift = true;
	
	
	public ShiftOperation(Rule rule, ScopeStrategy strategy, float shiftValue){
		super(rule, strategy);
		this.shiftValue = Math.abs(shiftValue);
		this.successor = null;
	}
	
	
	public ShiftOperation(Rule rule, ScopeStrategy strategy, ShiftOperation successor,
			              float shiftValue){
		super(rule,strategy);
		this.successor = successor;
		this.shiftValue = Math.abs(shiftValue);
	}

	

	public ShiftOperation getSuccessor() {
		return successor;
	}


	public void setSuccessor(ShiftOperation successor) {
		this.successor = successor;
	}

	
	public float getShiftValue() {
		return shiftValue;
	}


	public void setShiftValue(float shiftValue) {
		this.shiftValue = shiftValue;
	}


	public String getOperationName() {
		return SHIFT;
	}
	

	public boolean getAlwaysShift() {
		return alwaysShift;
	}


	public void setAlwaysShift(boolean alwaysShift) {
		this.alwaysShift = alwaysShift;
	}


	protected OperationValue delegate(SentimentInformation head, List<SentimentInformation> children) {
		//System.out.println("ShiftOperation delegate ");
		return (this.successor != null) ? this.successor.apply(head, children) : new OperationValue(head,children);
	}


	public OperationValue apply(SentimentInformation head,
			List<SentimentInformation> children) {
		
		OperationValue ov = this.getStrategy().apply(head, children,this);
		//System.out.println("ShiftOperation ov: "+ov);
		if (ov != null) {
			ov.setAppliedOperation(this);
			ov.setAppliedStrategy(this.getStrategy());
			return ov;
		}
		return delegate(head,children);
	}


	public String sucessors(){
		return this.getOperationName()+" "+this.getStrategy()+", sucessor: "+this.getSuccessor();
	}
	
	
	public void updateSentiment(SentimentInformation si){
		
		float shiftPSentiment, shiftNSentiment;
		
		float pSentiment = si.getPositiveSentiment();
		float nSentiment = si.getNegativeSentiment();
		float so = si.getSemanticOrientation();

		if (!alwaysShift && (pSentiment == 0 && nSentiment == 0)){} //At the moment does nothing
		else{
		
			if (pSentiment == 0 && nSentiment == 0){
				shiftPSentiment = 0;
				shiftNSentiment = this.getShiftValue();
			}
			else{
			
				float auxShiftPSentiment = pSentiment == 0 ? 0 : this.calculate(pSentiment);
				float auxShiftNSentiment = nSentiment == 0 ? 0 : this.calculate(nSentiment);
				
				shiftPSentiment = auxShiftPSentiment * ( auxShiftPSentiment > 0 ? 1 : 0) + Math.abs(auxShiftNSentiment) * (auxShiftNSentiment < 0 ? 1 : 0);
				shiftNSentiment = auxShiftNSentiment * ( auxShiftNSentiment > 0 ? 1 : 0) + Math.abs(auxShiftPSentiment) * (auxShiftPSentiment < 0 ? 1 : 0);
				
			
		}
		
		si.setPositiveSentiment(Math.max(Math.abs(shiftPSentiment),SentimentInformation.SENTISTRENGTH_NEUTRAL));
		si.setNegativeSentiment(Math.max(Math.abs(shiftNSentiment),SentimentInformation.SENTISTRENGTH_NEUTRAL));
		si.setSemanticOrientation(si.getPositiveSentiment() - si.getNegativeSentiment());
		}
	} 
	
	/**
	 * If the semantic orientation of the word is zero, the word will be switched to obtain
	 * a negative value.
	 * @param so The semantic orientation to be modified
	 */
	public float calculate(float sentiment) {
		return sentiment - (this.shiftValue);
	}
	
	@Override
	public String toString(){
		return SHIFT;
	}

}
