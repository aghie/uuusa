package org.grupolys.samulan.util;

import java.util.HashMap;
import java.util.List;

import org.grupolys.nlputils.parser.DependencyNode;
import org.grupolys.samulan.analyser.operation.Operation;


public class SentimentDependencyNode extends DependencyNode {

	private SentimentInformation si = null;

	
	public SentimentDependencyNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SentimentDependencyNode(DependencyNode node, SentimentInformation si) {
		super(node);
		this.si = si;
	}
	
	public SentimentDependencyNode(short address, String word, String lemma,
			String cpostag, String postag, String feats, String deprel,
			List<Short> dependents, SentimentInformation si) {
		super(address, word, lemma, cpostag, postag, feats, deprel, dependents);
		this.si = null;
		// TODO Auto-generated constructor stub
	}


	public SentimentDependencyNode(short address, String word, String lemma,
			String cpostag, String postag, String feats, String deprel,
			short head, List<Short> dependents, SentimentInformation si) {
		super(address, word, lemma, cpostag, postag, feats, deprel, head, dependents);
		this.si = null;
		// TODO Auto-generated constructor stub
	}




	public SentimentInformation getSi() {
		return si;
	}

	public void setSi(SentimentInformation si) {
		this.si = si;
	}
		
	
	@Override
	public String toString(){
		
		
		float negSentiment = (si != null) ? si.getNegativeSentiment() : SentimentInformation.SENTISTRENGTH_NEUTRAL;
		float posSentiment = (si != null) ? si.getPositiveSentiment() : SentimentInformation.SENTISTRENGTH_NEUTRAL;
		float so = (si != null) ? si.getSemanticOrientation() : SentimentInformation.SENTISTRENGTH_NEUTRAL;
		String explanation = (si  != null)? si.getOperationExplanation() : "";
		if (explanation.equals(Operation.DEFAULT)) explanation = "";
		return this.getWord()+" "+"["+this.getAddress()+","+this.getCpostag()+","+this.getDeprel()+"] ("+so+") "+"A("+this.getAddress()+")=["+explanation+"]";
	}
	
}
