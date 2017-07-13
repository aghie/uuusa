package org.grupolys.samulan.util;

import java.util.ArrayList;
import java.util.List;

import org.grupolys.nlputils.parser.DependencyGraph;
import org.grupolys.nlputils.parser.DependencyNode;
import org.grupolys.samulan.analyser.operation.Operation;

public class SentimentInformation {
	
	public static final String WHOLE_TEXT = "WHOLE_TEXT";
	public static final String POSITIVE_STRENGTH = "POSITIVE_STRENGTH";
	public static final String NEGATIVE_STRENGTH = "NEGATIVE_STRENGTH";
	public static final String SEMANTIC_ORIENTATION = "SEMANTIC_ORIENTATION";
	public static final float SENTISTRENGTH_NEUTRAL = 0;
	private float semanticOrientation;
	private float positiveSentiment;
	private float negativeSentiment;
	private String type;
	
	private SentimentDependencyNode sentimentDependencyNode;
	private SentimentDependencyGraph sentimentDependencyGraph; //A reference to the dependencygraph to set the semantic orientation of the nodes
	private List<QueuedOperationInformation> queuedOperations;
	private String operationExplanation;
	
	public SentimentInformation(){
		
		this.semanticOrientation = 0;
		this.negativeSentiment = SENTISTRENGTH_NEUTRAL;
		this.positiveSentiment = SENTISTRENGTH_NEUTRAL;
		this.sentimentDependencyNode = null;
		this.queuedOperations = null;
		this.sentimentDependencyGraph = null;
		this.operationExplanation = "";
		this.type = Operation.DEFAULT;
	}
	
	public SentimentInformation(float semanticOrientation,
			SentimentDependencyNode sentimentDependencyNode, SentimentDependencyGraph dg){
		this.semanticOrientation = semanticOrientation;
		this.positiveSentiment = this.isPositiveSentiment(semanticOrientation) ? semanticOrientation : SENTISTRENGTH_NEUTRAL;
		this.negativeSentiment = this.isNegativeSentiment(semanticOrientation) ? semanticOrientation : SENTISTRENGTH_NEUTRAL;
		this.sentimentDependencyNode = sentimentDependencyNode;
		this.queuedOperations = new ArrayList<QueuedOperationInformation>();
		this.sentimentDependencyGraph = dg;
		this.type = (sentimentDependencyNode != null && sentimentDependencyNode.getSi() != null) ? sentimentDependencyNode.getSi().type : Operation.DEFAULT;
	}
		

	public SentimentInformation(float semanticOrientation,
								SentimentDependencyNode sentimentDependencyNode,
								SentimentDependencyGraph dg,
			                    List<QueuedOperationInformation> queuedOperations){
		this.semanticOrientation = semanticOrientation;
		this.positiveSentiment = this.isPositiveSentiment(semanticOrientation) ? semanticOrientation : SENTISTRENGTH_NEUTRAL;
		this.negativeSentiment = this.isNegativeSentiment(semanticOrientation) ? semanticOrientation : SENTISTRENGTH_NEUTRAL;
		this.sentimentDependencyNode = sentimentDependencyNode;
		this.queuedOperations = queuedOperations;
		this.sentimentDependencyGraph = dg;
	//	System.out.println("constructor sentimentInformation: "+sentimentDependencyNode+sentimentDependencyNode.getSi().type);
		this.type = sentimentDependencyNode.getSi().type;
	}
	
	
	public SentimentInformation(SentimentInformation si){
	/*
	 * Copy constructor	
	 * @param: 
	 */
		this.semanticOrientation = si.getSemanticOrientation();
		this.positiveSentiment = si.getPositiveSentiment();
		this.negativeSentiment = si.getNegativeSentiment();
		this.sentimentDependencyNode = si.getSentimentDependencyNode(); //shallow copy
		this.sentimentDependencyGraph = si.getSentimentDependencyGraph();
		this.queuedOperations = new ArrayList<QueuedOperationInformation>(); //deep copy
		for (QueuedOperationInformation qoi : si.queuedOperations){
			this.queuedOperations.add(new QueuedOperationInformation(qoi));
		}
		this.type = si.type;
		
		
	}
	
	
	public void setSentimentInformationInGraph(){
		this.getSentimentDependencyGraph().setNodeSentimentInformation(this);
	}
	
	public SentimentDependencyGraph getSentimentDependencyGraph() {
		return sentimentDependencyGraph;
	}

	public void setSentimentDependencyGraph(
			SentimentDependencyGraph sentimentDependencyGraph) {
		this.sentimentDependencyGraph = sentimentDependencyGraph;
	}

	public float getSemanticOrientation() {
		return semanticOrientation;
	}

	public void setSemanticOrientation(float semanticOrientation) {
		this.semanticOrientation = semanticOrientation;
	}

	public SentimentDependencyNode getSentimentDependencyNode() {
		return sentimentDependencyNode;
	}

	public void setSentimentDependencyNode(SentimentDependencyNode sentimentDependencyNode) {
		this.sentimentDependencyNode = sentimentDependencyNode;
	}
	

	public List<QueuedOperationInformation> getQueuedOperations() {
		return queuedOperations;
	}

	public void setQueuedOperations(
			List<QueuedOperationInformation> queuedOperations) {
		this.queuedOperations = queuedOperations;
	}
	
	public void addQueueOperation(QueuedOperationInformation qoi){
		this.queuedOperations.add(qoi);
	}
	
	public void addAllQeueOperations(List<QueuedOperationInformation> qois){
		this.queuedOperations.addAll(qois);
	}

	public float getPositiveSentiment() {
		return positiveSentiment;
	}


	public void setPositiveSentiment(float positiveSentiment) {
		this.positiveSentiment = positiveSentiment;
	}


	public float getNegativeSentiment() {
		return negativeSentiment;
	}


	public void setNegativeSentiment(float negativeSentiment) {
		this.negativeSentiment = negativeSentiment;
	}
	
	
	public String getOperationExplanation() {
		return operationExplanation;
	}

	public void setOperationExplanation(String operationExplanation) {
		this.operationExplanation = operationExplanation;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private boolean isPositiveSentiment(float so){
		return so > 0;
	}
	
	private boolean isNegativeSentiment(float so){
		return so < 0;
	}

	
	
	@Override
	public String toString(){
		
		String word;
		if (this.getSentimentDependencyNode() == null)
			word = WHOLE_TEXT;
		else 
			word = this.getSentimentDependencyNode().getWord();
		
		return  "("+word+") "
				+this.semanticOrientation+" (pSentiment: "+ this.getPositiveSentiment()
				+", nSentiment:"+this.getNegativeSentiment()+")"+" "+this.queuedOperations;	
	}
	
	
	
	
	
}
