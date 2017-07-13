package org.grupolys.samulan.analyser.operation;
/**
 * @author David Vilares
 */
import java.util.List;

import org.grupolys.samulan.rule.Rule;
import org.grupolys.samulan.util.OperationValue;
import org.grupolys.samulan.util.SentimentInformation;

public class WeightingOperation extends AbstractOperation implements Operation {
	
	private float weightingValue;
	private WeightingOperation successor;
	
	public WeightingOperation(Rule rule, ScopeStrategy strategy, float weightingValue){
		super(rule, strategy);
		this.weightingValue = weightingValue;
	}
	
	
	public WeightingOperation(Rule rule, ScopeStrategy strategy, float weigthingValue,
							  WeightingOperation successor){
		super(rule,strategy);
		this.weightingValue = weigthingValue;
		this.successor = successor;
		}
	
	
	
	public String getOperationName() {
		return WEIGHT;
	}

	
	
	protected OperationValue delegate(SentimentInformation head, List<SentimentInformation> children) {
		return (this.successor != null) ? this.successor.apply(head, children) : new OperationValue(head,children);
	}
//
//
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
	
	
	
	
	

//	public OperationValue apply(SentimentInformation head, List<SentimentInformation> children){
//		OperationValue ov= this.getStrategy().apply(head, children,this);
//		if (ov != null) {
//			ov.setAppliedOperation(this);
//			ov.setAppliedStrategy(this.getStrategy());
//			return  ov;
//		}
//		else return new OperationValue(head,children);
//		
//	}
	
	public void updateSentiment(SentimentInformation si){
		float pSentiment = si.getPositiveSentiment();
		float nSentiment = si.getNegativeSentiment();
		
//		float auxShiftPSentiment = pSentiment == SentimentInformation.SENTISTRENGTH_NEUTRAL ? SentimentInformation.SENTISTRENGTH_NEUTRAL : this.calculate(pSentiment);
//		float auxShiftNSentiment = nSentiment == SentimentInformation.SENTISTRENGTH_NEUTRAL ? SentimentInformation.SENTISTRENGTH_NEUTRAL : this.calculate(nSentiment);

		float auxShiftPSentiment =  this.calculate(pSentiment);
		float auxShiftNSentiment =  this.calculate(nSentiment);
		
		
		float shiftPSentiment, shiftNSentiment;
		
		shiftPSentiment = Math.abs(auxShiftPSentiment * ( auxShiftPSentiment > 0 ? 1 : 0) + auxShiftNSentiment * (auxShiftNSentiment < 0 ? 1 : 0));
		shiftNSentiment = Math.abs(auxShiftNSentiment * ( auxShiftNSentiment > 0 ? 1 : 0) + auxShiftPSentiment * (auxShiftPSentiment < 0 ? 1 : 0));

		
//		System.out.println("auxShiftPSentiment: "+auxShiftPSentiment);
//		System.out.println("auxshiftNSentiment: "+auxShiftNSentiment);
//		
//		System.out.println("shiftPSentiment: "+shiftPSentiment);
//		System.out.println("shiftNSentiment: "+shiftNSentiment);
		
		
		si.setPositiveSentiment(Math.max(shiftPSentiment, SentimentInformation.SENTISTRENGTH_NEUTRAL));
		si.setNegativeSentiment(Math.max(shiftNSentiment, SentimentInformation.SENTISTRENGTH_NEUTRAL));
		si.setSemanticOrientation(shiftPSentiment-shiftNSentiment);
	}
	

	
	public float calculate(float sentiment) {
		
		return sentiment*(1+weightingValue)- SentimentInformation.SENTISTRENGTH_NEUTRAL;
	}
	
	public String toString(){
		String sign  = (this.weightingValue > 0) ? "+" : "" ;
		return WEIGHT+"("+sign+this.weightingValue+")";
	}


	
}
