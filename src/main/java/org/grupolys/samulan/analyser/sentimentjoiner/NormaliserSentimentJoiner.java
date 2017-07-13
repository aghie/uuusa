package org.grupolys.samulan.analyser.sentimentjoiner;

import java.util.List;

import org.grupolys.samulan.util.SentimentInformation;

public class NormaliserSentimentJoiner implements SentimentJoiner{

	
	private float threshold;
	
	public NormaliserSentimentJoiner(float threshold){
		this.threshold = threshold;
	}
	
	private float normalise(float value){
		return Math.abs(value) > Math.abs(threshold) ? (value < 0 ? -this.threshold : this.threshold) : value;
	}
	
	@Override
	public SentimentInformation join(SentimentInformation head,
			List<SentimentInformation> children) {
		
		float nPositiveSentiment = 0;
		float nNegativeSentiment = 0;
		
		head.setPositiveSentiment(normalise(head.getPositiveSentiment()));
		head.setNegativeSentiment(normalise(head.getNegativeSentiment()));

		
		for (SentimentInformation siChild: children){
			nPositiveSentiment= normalise(nPositiveSentiment + siChild.getPositiveSentiment());
			nNegativeSentiment= normalise(nNegativeSentiment + siChild.getNegativeSentiment());
		}
		
		head.setPositiveSentiment(normalise(head.getPositiveSentiment()+nPositiveSentiment));
		head.setNegativeSentiment(normalise(head.getNegativeSentiment()+nNegativeSentiment));
		head.setSemanticOrientation(head.getPositiveSentiment()-head.getNegativeSentiment());
		
		return head;
	}

}
