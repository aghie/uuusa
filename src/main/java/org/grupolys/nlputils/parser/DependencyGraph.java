package org.grupolys.nlputils.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class DependencyGraph {
	
	protected HashMap<Short, DependencyNode> nodes;

	
	public DependencyGraph(HashMap<Short, DependencyNode> nodes){
		this.nodes = nodes;
	}
	

	public DependencyGraph() {
		this.nodes = new HashMap<Short,DependencyNode>();
	}


	public DependencyGraph(String conll){
	
		String line;
		String[] lines;
		String[] columns;
		DependencyNode dn;
		List<Short> existentDependents;
		short id, head;
		this.nodes = new HashMap<Short,DependencyNode>();
		
		lines = conll.split("\n");
		for (String l: lines){
			
			columns = l.split("\t");
			id = Short.parseShort(columns[0]);
			head = Short.parseShort(columns[6]);
			
			existentDependents = new ArrayList<Short>();				
			if (this.nodes.containsKey(id)){
				existentDependents = this.nodes.get(id).getDependents();
			}	
			dn = new DependencyNode(id,columns[1], columns[2],columns[3],columns[4],
					columns[5],columns[7], head, existentDependents);
			this.nodes.put(id, dn);
			
			if (!this.nodes.containsKey(head)){
				DependencyNode node; 
				if (head == DependencyNode.ROOT_ADDRESS)
					node = new DependencyNode(head, DependencyNode.ROOT_WORD,
							null, DependencyNode.ROOT_POSTAG,DependencyNode.ROOT_POSTAG,
							null, null,new ArrayList<Short>());

				else node=  new DependencyNode(new ArrayList<Short>()); 

				this.nodes.put(head,node);
			}
			this.nodes.get(head).getDependents().add(id);	
		}		
	}
	
	
	
	
	public HashMap<Short, DependencyNode> getNodes() {
		return nodes;
	}


	public void setNodes(HashMap<Short, DependencyNode> nodes) {
		this.nodes = nodes;
	}


	public DependencyNode getNode(short id){
		return this.nodes.get(id);
	}
	
	
	/***
	 * Obtain the list of children given the address of the parent node
	 * @param parent: The address of the current node
	 * @return The list of children of the node (@see es.udc.fi.dc.lys.nlputil.parser.DependencyNode) 
	 */
	public List<DependencyNode> getChildren(short parent){
		//TODO change to java 8
		List<DependencyNode> children = new ArrayList<DependencyNode>();
		DependencyNode childNode;
		for (short id : this.nodes.keySet()){
			childNode = (DependencyNode) this.nodes.get(id);
			if (childNode.getHead() == parent) children.add(childNode);
		}
		return children;
	}
	
	/***
	 * Obtains the ancester node at "levelsup" of the current node
	 * @param currentAddress: The address of the current node
	 * @param levelsup: An integer to obtain the ancester at "levelsup"
	 * @return A @see es.udc.fi.dc.lys.nlputils.Dependencynode instance or null if there is no ancester at "levelsup"
	 */
	public DependencyNode getAncester(short currentAddress,int levelsup){

		DependencyNode dn = this.nodes.get(currentAddress);
		
		if (dn != null && levelsup == 0) return dn; 
		if (dn == null || (dn.isRoot() && levelsup > 0)) return null;
		else{
			return getAncester(dn.getHead(),levelsup-1);
		}

	}
	
	public String toConll(){
		
		String conll = "";
		for (Short id=1; id < this.nodes.size(); id++){
			conll+= id+"\t"+(this.nodes.get(id).getWord() != null ? this.nodes.get(id).getWord() : "_" )
					+"\t"+(this.nodes.get(id).getLemma() != null ? this.nodes.get(id).getLemma() : "_" )
					+"\t"+(this.nodes.get(id).getCpostag() != null ? this.nodes.get(id).getCpostag() : "_") 
					+"\t"+(this.nodes.get(id).getPostag() != null ? this.nodes.get(id).getPostag() : "_") 
					+"\t"+(this.nodes.get(id).getFeats() != null ? this.nodes.get(id).getFeats() : "_") 
					+"\t"+(this.nodes.get(id).getHead())
					+"\t"+(this.nodes.get(id).getDeprel() != null ? this.nodes.get(id).getDeprel() : "_")+"\n"
					;
		}
		return conll;
	}
	
	private List<DependencyNode> subgraphToListDependencyNode(short address){
		
		DependencyNode dn = (DependencyNode) this.getNode(address);
		List<Short> dependents = dn.getDependents();
		List<DependencyNode> nodes = new ArrayList<DependencyNode>();
		
		if (dn.isLeaf()){
			nodes.add(dn);
			return nodes;
		}
		if (!dn.isRoot())
			nodes.add(dn);
		for (Short d: dependents){
			nodes.addAll(this.subgraphToListDependencyNode(d));
		}
		return nodes;
	}
	
	/**
	 * @param address of the node of the dependencyGraph
	 * @return A string with a sorted subtree by ID for the nodes depending from address (included)
	 */
	public String subgraphToString(short address){
		
		List<DependencyNode> dns = subgraphToListDependencyNode(address);
		Collections.sort(dns, new Comparator<DependencyNode>() {

			public int compare(DependencyNode dn1, DependencyNode dn2) {
				return dn1.getAddress() > dn2.getAddress() ? 1 : -1;
			}
	    });
		
		List<String> dnsForms = dns.stream().map(dn -> dn.getWord()).collect(Collectors.toList());
		return String.join(" ", dnsForms);
	}
	
	
	@Override
	public String toString(){
		
		String rawGraph ="";
		for (Short id: this.nodes.keySet()){
			rawGraph+= String.valueOf(id)+":("+ this.nodes.get(id)+")\n";
		}
		return rawGraph;
	}
	
}
