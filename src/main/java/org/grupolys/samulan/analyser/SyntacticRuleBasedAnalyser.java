package org.grupolys.samulan.analyser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import org.grupolys.samulan.analyser.AnalyserConfiguration;
import org.grupolys.samulan.analyser.operation.Operation;
import org.grupolys.samulan.analyser.sentimentjoiner.DefaultSentimentJoiner;
import org.grupolys.samulan.analyser.sentimentjoiner.SentimentJoiner;
import org.grupolys.samulan.rule.RuleManager;
import org.grupolys.samulan.util.OperationValue;
import org.grupolys.samulan.util.QueuedOperationInformation;
import org.grupolys.samulan.util.SentimentDependencyGraph;
import org.grupolys.samulan.util.SentimentDependencyNode;
import org.grupolys.samulan.util.SentimentInformation;

/**
 * Universal Unsupervised Uncovered Syntactic Analyser
 * @author David Vilares
 *
 */
public class SyntacticRuleBasedAnalyser extends RuleBasedAnalyser implements Analyser  {

	
	
	public SyntacticRuleBasedAnalyser(AnalyserConfiguration ac, RuleManager rm){
		super(ac,rm);
	}
	

	
	private float weightSemanticOrientation(float so){
		if (so > 0) return so*this.ac.getPositiveWeigthing();
		else return so*this.ac.getNegativeWeigthing();
	}
		


	private float getSemanticOrientation(SentimentDependencyNode node,
            Operation operation){

		float value = 0;
		float dictionaryValue = 0;
		String predictedFormNoDuplicated = null;
		String form = (node.getLemma() != null) ? node.getLemma() : node.getWord();
		
		String cpostag = node.getCpostag();
		dictionaryValue = this.rm.getD().getValue(cpostag,  this.rm.getD().getLemma(node.getCpostag(),node.getWord()),false);
		
		
		if (dictionaryValue == 0 && !this.rm.getD().getAdverbsIntensifiers().contains(form)){
			//TODO replaceAll should not be here
			predictedFormNoDuplicated = this.rm.getD().getLemma(node.getCpostag(),node.getWord().replaceAll("(.)\\1{1,}", "$1")); 
			dictionaryValue = this.rm.getD().getValue(cpostag, predictedFormNoDuplicated,this.ac.isRelaxedEmotionSearch());
		}

		if (dictionaryValue == 0){
			String strippedLemma = this.rm.getD().getStrippedLemma(node.getCpostag(), node.getWord());
			dictionaryValue = this.rm.getD().getValue(cpostag, strippedLemma, this.ac.isRelaxedEmotionSearch());

		}
		int isDefault =  operation.isSemanticOrientationOperation() ? 1 : 0;
		value = dictionaryValue*(1+(float) 0.0*(form.toUpperCase().equals(form) ? 1: 0))*isDefault;
		
		return (weightSemanticOrientation(value));
	}
	
	
	
	private void updateLevelsUp(List<QueuedOperationInformation> qois){
		for (QueuedOperationInformation qoi: qois){
			if (qoi.getLevelsUp() >0)
				qoi.setLevelsUp((short) (qoi.getLevelsUp()-1));
		}
	}

	
	private void queueNodeOperations(List<Operation> operations, SentimentInformation si, 
									 SentimentDependencyNode node){
		
		for (Operation o: operations){
			short levelToApply = o.getRule().getLevelsup();	
			if (o.getOperationName().equals(Operation.DEFAULT)){
				si.setSemanticOrientation(getSemanticOrientation(node,o));
				int isPositiveSentiment = si.getSemanticOrientation() > 0 ? 1 : 0;
				int isNegativeSentiment = si.getSemanticOrientation() < 0 ? 1 : 0;
				si.setPositiveSentiment(Math.max(Math.abs(si.getSemanticOrientation())*(isPositiveSentiment), SentimentInformation.SENTISTRENGTH_NEUTRAL));
				si.setNegativeSentiment(Math.max(Math.abs(si.getSemanticOrientation())*(isNegativeSentiment), SentimentInformation.SENTISTRENGTH_NEUTRAL));		
			}
			si.addQueueOperation(new QueuedOperationInformation(levelToApply,o));
		}
	}
	
	
	private final boolean isPendingOperation(QueuedOperationInformation qoi){
				
		return qoi.getLevelsUp() > 0;
	}
	
	
	private List<QueuedOperationInformation> getOperationsToQueue(List<QueuedOperationInformation> qois){
		List <QueuedOperationInformation> pendingOperations = new ArrayList<QueuedOperationInformation>();
		for (QueuedOperationInformation qoi: qois){
			if (isPendingOperation(qoi)) pendingOperations.add(qoi);
		}
		return pendingOperations;
	}
	
	
	private PriorityQueue<QueuedOperationInformation> getOperationsToApply(List<QueuedOperationInformation> qois){
		
		Comparator<QueuedOperationInformation> comparator = new Comparator<QueuedOperationInformation>() {
			public int compare(QueuedOperationInformation qoi1, QueuedOperationInformation qoi2) {
				return  qoi2.getOperation().getPriority() - qoi1.getOperation().getPriority();
			}
		};
		
		PriorityQueue <QueuedOperationInformation> queuedOperations = new PriorityQueue<QueuedOperationInformation>(qois.size()+1,comparator);
		for (QueuedOperationInformation qoi: qois){
			if (!isPendingOperation(qoi)) {
				queuedOperations.add(qoi);
			}
		}
		return queuedOperations;	
	}
	
	
	private List<QueuedOperationInformation> getAllQueuedOperations(SentimentInformation head, 
			List<SentimentInformation> children){
		
		List<QueuedOperationInformation> allQueuedOperations = new ArrayList<QueuedOperationInformation>(head.getQueuedOperations());
		for (SentimentInformation siChild: children){

			
			for (QueuedOperationInformation oChild : siChild.getQueuedOperations()){
				//Nesting weighting operations
				//TODO only supports double nesting
				short headAddress =siChild.getSentimentDependencyNode().getHead();
				SentimentDependencyGraph sdgChild  = siChild.getSentimentDependencyGraph();
				SentimentDependencyNode headNode = sdgChild.getNode(headAddress);
				String headLemma = this.rm.getD().getLemma(headNode.getCpostag(), headNode.getWord());
			
				SentimentDependencyNode grandPaNode = sdgChild.getNode(headNode.getHead());
				String grandPaLemma = this.rm.getD().getLemma(grandPaNode.getCpostag(), grandPaNode.getWord());
				boolean grandPaIsSubjective = this.rm.getD().getValue(grandPaNode.getCpostag(), grandPaLemma, true) != 0;
				if (this.rm.getD().isWeight(headLemma) && grandPaIsSubjective ){ 
					oChild.setLevelsUp((short) (oChild.getLevelsUp()+1));
				}
				allQueuedOperations.add(oChild);
			}
		}
		return allQueuedOperations;
	}
	
	
	/**
	 * Given the SentimentInformation of a head term and its children, it computes the merged SentimentInformation 
	 * that results after computing all operations to be applied at that stage.
	 * @param head: SentimentInformation object corresponding to the head (as a single node).
	 * @param children: List of SentimentInformation objects corresponding to the computed/merged SentimentInformation rooted at each child of the head term
	 * @return A new SentimentInformation corresponding to the computed/merged SentimentInformation rooted at the head term
	 */
	public SentimentInformation calculate(SentimentInformation head,
			List<SentimentInformation> children) {
		
		List<QueuedOperationInformation> allOperations, qOperations;
		PriorityQueue<QueuedOperationInformation> aOperations;
		QueuedOperationInformation i;
		OperationValue ov;
		SentimentInformation newHead = new SentimentInformation(head);
		
		

		allOperations = getAllQueuedOperations(newHead, children);	
		qOperations = getOperationsToQueue(allOperations);
		aOperations = getOperationsToApply(allOperations);

		String appliedOperations = "";
		while ( (i = aOperations.poll()) != null ){
			ov = i.getOperation().apply(newHead,children);
			//Logging the applied operation at node i
			//TODO: Improve how we track.
			appliedOperations = appliedOperations.concat( ov.appliedOperation() == null ? "" : ov.appliedOperation()+",");
			newHead = ov.getHead();
			children = ov.getChildren();
		}
		this.ac.getSentimentJoiner().join(newHead, children);
		newHead.setSentimentInformationInGraph(); //newHead has the reference to its graph
		newHead.setOperationExplanation(appliedOperations);
		
		//We add q(eued)Operations comming from the children to the head, to spread them through the tree
		for (QueuedOperationInformation pd : qOperations){
			if (!newHead.getQueuedOperations().contains(pd)){
				newHead.getQueuedOperations().add(pd);
			}
		}

		
		List<QueuedOperationInformation> aux = new ArrayList<QueuedOperationInformation>();
		for (QueuedOperationInformation pd : newHead.getQueuedOperations()){
			if (isPendingOperation(pd)){
				aux.add(pd);
			}
		}
		newHead.setQueuedOperations(aux);

		updateLevelsUp(newHead.getQueuedOperations());

		return newHead;
	}
	
	/**
	 * It computes the SentimentInformation of sentence represented as a SentimentDependencyGraph
	 * @param dg: The sentence represented as a SentimentDependencyGraph
	 * @param address: (usually the dummy root, id=0)
	 * @return The SentimentInformation for the branch of dg rooted at address
	 */
	public SentimentInformation analyse(SentimentDependencyGraph dg, short address) {
		
		List<Operation> operations = this.rm.getOperations(dg, address);	
		SentimentInformation siHead;
		SentimentDependencyNode node = (SentimentDependencyNode) dg.getNode(address);
		siHead = new SentimentInformation(0,node, dg, new ArrayList<QueuedOperationInformation>());

		//Queing operations in node
		queueNodeOperations(operations,siHead,node);
		if (node.isLeaf()){
			siHead = this.calculate(siHead,new ArrayList<SentimentInformation>());
			return siHead;
		}
		else{
			List<Short> children = node.getDependents();
			List<SentimentInformation> siChildren = new ArrayList<SentimentInformation>();
			for (Short child: children){
				SentimentInformation siChild = this.analyse(dg, (short) child);
				siChildren.add(siChild);
			}
			siHead = this.calculate(siHead,siChildren);
			return siHead;
		}
	}

	@Override
	/**
	 * It computes the final SentimentInformation of a sample, given a list of SentimentInformation objects.
	 * It is intended for running document-level sentiment classification.
	 * @param sis: One SentimentInformation object per sentence.
	 * @return A SentimentInformation object resulting for summing the SentimentInformation coming from each sentence.
	 */
	public SentimentInformation merge(List<SentimentInformation> sis) {

		SentimentInformation si = new SentimentInformation();
	

		float posSentiment = 0;
		float negSentiment = 0;
		
		for (SentimentInformation siAux : sis){
			posSentiment+= siAux.getPositiveSentiment();
			negSentiment+= siAux.getNegativeSentiment();
		}
			
		si.setNegativeSentiment(negSentiment);
		si.setPositiveSentiment(posSentiment);
		si.setSemanticOrientation(posSentiment-negSentiment);
		return si;
	}

		

}
