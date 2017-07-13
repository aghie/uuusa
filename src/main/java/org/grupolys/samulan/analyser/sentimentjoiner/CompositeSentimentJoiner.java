package org.grupolys.samulan.analyser.sentimentjoiner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.grupolys.samulan.util.SentimentInformation;

public class CompositeSentimentJoiner implements SentimentJoiner {

	
	private List<SentimentJoiner> joiners;
	
	public CompositeSentimentJoiner(){
		this.joiners = new ArrayList<SentimentJoiner>();
	}
	
	public CompositeSentimentJoiner(List<SentimentJoiner> joiners){
		this.joiners = joiners;
	}
	
	@Override
	public SentimentInformation join(SentimentInformation head,
			List<SentimentInformation> children) {
		
		List<SentimentInformation> auxChildren;
		
		auxChildren = children.stream().map((SentimentInformation c) -> new SentimentInformation(c)).collect(Collectors.toList());
		
		for (SentimentJoiner sj: joiners){
			//System.out.println(sj);
			//Each child is joined as a single node, to avoid replications
			auxChildren = auxChildren.stream()
									 .map((SentimentInformation c) -> sj.join(c, new ArrayList<SentimentInformation>()))
									 .collect(Collectors.toList());
			//The head is 'joined' separately as well
			head = sj.join(head, new ArrayList<SentimentInformation>());
						
			//Joining head and children
			head = sj.join(head, auxChildren);
		}
		return head;
	}
	
	/**
	 * 
	 * @param joiner
	 */
	public void add(SentimentJoiner joiner){
		this.joiners.add(joiner);
	}
	
	/**
	 * 
	 * @param joiner
	 */
	public void remove(SentimentJoiner joiner){
		this.joiners.remove(joiner);
	}

	public List<SentimentJoiner> getJoiners() {
		return joiners;
	}
	
	

}
