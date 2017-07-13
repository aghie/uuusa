package org.grupolys.samulan.analyser.sentimentjoiner;

import java.util.List;

import org.grupolys.samulan.util.SentimentInformation;

public class DefaultSentimentJoiner implements SentimentJoiner{

	
	
	@Override
	public SentimentInformation join(SentimentInformation head,
			List<SentimentInformation> children) {
		
		for (SentimentInformation siChild: children){
			head.setPositiveSentiment(head.getPositiveSentiment()+siChild.getPositiveSentiment());
			head.setNegativeSentiment(head.getNegativeSentiment()+siChild.getNegativeSentiment());
		}
		head.setSemanticOrientation(head.getPositiveSentiment()-head.getNegativeSentiment());
		return head;
	}

}
