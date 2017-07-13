package org.grupolys.samulan.analyser.sentimentjoiner;

import java.util.List;

import org.grupolys.samulan.util.SentimentInformation;

public class MaximumFromBranchSentimentJoiner implements SentimentJoiner{

	
	@Override
	public SentimentInformation join(SentimentInformation head,
			List<SentimentInformation> children) {
		
		//System.out.println("Maximum joiner");
		for (SentimentInformation siChild: children){
			if (siChild.getPositiveSentiment() > head.getPositiveSentiment()){
				head.setPositiveSentiment(siChild.getPositiveSentiment());	
			}
			if (siChild.getNegativeSentiment() > head.getNegativeSentiment()){
				head.setNegativeSentiment(siChild.getNegativeSentiment());
			}
			
			//head.setSemanticOrientation(head.getSemanticOrientation()+siChild.getSemanticOrientation());
//			System.out.println("head: "+head);
//			System.out.println("child: "+siChild);
			head.setSemanticOrientation(head.getPositiveSentiment()- head.getNegativeSentiment());
							
//			if (siChild.getSemanticOrientation() > head.getSemanticOrientation()){
//				head.setSemanticOrientation(siChild.getSemanticOrientation());
//			}
			
		}
		return head;
	}
}
