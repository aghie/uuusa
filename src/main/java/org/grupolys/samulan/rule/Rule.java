package org.grupolys.samulan.rule;
/**
 * @author David Vilares
 */
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.grupolys.samulan.analyser.operation.Operation;
import org.grupolys.samulan.util.SentimentDependencyGraph;
import org.grupolys.samulan.util.SentimentDependencyNode;

public class Rule {
	
	private Set<Pattern> regexs;
	private Set<String> regexsForms;
	private Set<String> postags;
	private Set<String> dependencies;
	private Set<String> validHead;
	private short levelsup;
	private short priority;
	private Operation operation;

	
	public Rule(Set<Pattern> regexs, Set<String> regexsForms, Set<String> postags, Set<String> dependencies,
			short levelsup, short priority, Set<String> validHead, Operation operation){
		
		this.regexs = regexs;
		this.regexsForms = regexsForms;
		this.postags = postags;
		this.dependencies = dependencies;
		this.levelsup = levelsup;
		this.priority = priority;
		this.operation = operation;
		this.validHead = validHead;
		this.operation.setRule(this);
	}
	
	
	public Rule(HashSet<Pattern> regexs,Set<String> regexsForms, HashSet<String> postags,
			HashSet<String> dependencies, short priority, Set<String> validHead, Operation operation) {
		
		this.regexs = regexs;
		this.regexsForms = regexsForms;
		this.postags = postags;
		this.dependencies = dependencies;
		this.levelsup = 0;
		this.operation = operation;
		this.validHead = validHead;
		this.operation.setRule(this);
	}



	public Set<Pattern> getRegexs() {
		return regexs;
	}


	public void setRegexs(Set<Pattern> regexs) {
		this.regexs = regexs;
	}


	public Set<String> getPostags() {
		return postags;
	}


	public void setPostags(Set<String> postags) {
		this.postags = postags;
	}


	public Set<String> getDependencies() {
		return dependencies;
	}


	public void setDependencies(Set<String> dependencies) {
		this.dependencies = dependencies;
	}
	

	public short getLevelsup() {
		return levelsup;
	}


	public void setLevelsup(short levelsup) {
		this.levelsup = levelsup;
	}


	public Operation getOperation() {
		return operation;
	}


	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	
	
	public Set<String> getValidHead() {
		return validHead;
	}


	public void setValidHead(Set<String> validHead) {
		this.validHead = validHead;
	}


	public short getPriority() {
		return priority;
	}


	public void setPriority(short priority) {
		this.priority = priority;
	}
	

	public boolean patternMatches(String form){
		
		boolean matched = this.regexs.isEmpty() && this.regexsForms.isEmpty(); 
		
		if (matched || this.regexsForms.contains(form)) return true;
		
		for (Pattern p : this.regexs) {
	//		System.out.println(form+" Regex size: "+this.regexs.size()+" "+this.regexs);
			Matcher m = p.matcher(form);
			matched = m.matches();
			if (matched) return true;
		}
		return matched;
	}
	
	/**
	 * Determines if a rule is applicable in the node
	 * @param dn a dependency node of the graph
	 * @see es.udc.fi.dc.lys.nlputils.DependencyNode
	 * @return true if the rule is applicable in that node, false otherwise
	 */
	public boolean match(SentimentDependencyGraph dg,SentimentDependencyNode dn){
		
		String form = dn.getWord().toLowerCase();
		String cpostag = dn.getCpostag();
		String deprel = dn.getDeprel();
		String currentSubGraphString = "";
		boolean validHeadTag =  (dg == null || dg.getNode(dn.getHead()) == null) ? true : this.getValidHead().contains(dg.getNode(dn.getHead()).getPostag());
		boolean complexExpressions = false; //TODO put in a configuration file
		if (dg != null && complexExpressions){
			currentSubGraphString = dg.subgraphToString(dn.getAddress());
		}
		
		boolean nodeMatches  = (
				(this.getPostags().contains(cpostag) || this.getPostags().isEmpty()) &&
				(this.getDependencies().contains(deprel) || this.getDependencies().isEmpty()) &&
				(validHeadTag || this.getValidHead().isEmpty()) && (patternMatches(form)));
		
		boolean subGraphMatches = (dg != null && complexExpressions) && patternMatches(currentSubGraphString) && (currentSubGraphString.split(" ").length >=2); //&& dg !=null;
//		
		//System.out.println("currentSubGraphString: "+currentSubGraphString+" nodeMatches: "+nodeMatches+" subGraphMatches: "+subGraphMatches);
//		
		//if (!nodeMatches){ nodeMatches = subGraphMatches;}
		return nodeMatches;
	}

	private boolean regexsEquals(Set<Pattern> p1, Set<Pattern> p2){
	
		Set<String> patternsFormsp2 = p2.stream().map( (Pattern p) -> p.pattern()).collect(Collectors.toSet());
		Set<String> patternsFormsp1 = p1.stream().map( (Pattern p) -> p.pattern()).collect(Collectors.toSet());
		return patternsFormsp1.equals(patternsFormsp2);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Include operation to determine if they are equal or not
		
	//	System.out.println("Equals "+this.regexs+" "+((Rule) obj).getRegexs());
	//	System.out.println("Equals "+regexsEquals(this.regexs, ((Rule) obj).getRegexs()));
		
		
		return (regexsEquals(this.regexs, ((Rule) obj).getRegexs()) && 
				 this.postags.equals(((Rule) obj).getPostags()) &&
				 this.dependencies.equals(((Rule) obj).getDependencies()));
	}
	
	@Override
	public String toString(){
		return this.regexs.toString()+" "+this.postags.toString()+" "+this.dependencies.toString()+" "+this.levelsup+" "+this.getOperation();	
	}
	
}
