package org.grupolys.samulan.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.grupolys.nlputils.parser.DependencyGraph;
import org.grupolys.nlputils.parser.DependencyNode;
import org.grupolys.samulan.analyser.operation.Operation;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.github.mdr.ascii.layout.GraphLayout;

public class SentimentDependencyGraph extends DependencyGraph {
	
	
	
    private static final String CHILDREN = "children";
    private static final String SEMANTIC_ORIENTATION = "so";
    private static final String IS_NEGATION = "is_negation";
    private static final String IS_INTENSIFIER = "is_intensifier";
    private static final String POSTAG = "postag";
    private static final String WORD = "word";
    private static final String ADDRESS = "address";
    private static final String DEPENDENCY_TYPE = "dependency_type";
	

//	private HashMap<Short, SentimentDependencyNode> nodes;
	
	public SentimentDependencyGraph(HashMap<Short, DependencyNode> nodes) {
		
//		HashMap<Short, DependencyNode> dns = new HashMap<Short,DependencyNode>();
//		
//		for (Short address: nodes.keySet()){
//			
//			dns.put(address, nodes.get(address));
//		}
		
		super(nodes);
		//this.nodes = nodes;
		// TODO Auto-generated constructor stub
	}
	





	public SentimentDependencyGraph(String conll){
		super();
		String line;
		String[] lines;
		String[] columns;
		SentimentDependencyNode dn;
		List<Short> existentDependents;
		short id, head;
		HashMap<Short, DependencyNode> nodes = this.getNodes(); // new HashMap<Short, SentimentDependencyNode>();
		
		lines = conll.split("\n");
		for (String l: lines){
			
			columns = l.split("\t");
			id = Short.parseShort(columns[0]);
			head = Short.parseShort(columns[6]);
			
			existentDependents = new ArrayList<Short>();				
			if (nodes.containsKey(id)){
				existentDependents =nodes.get(id).getDependents();
			}	
			dn = new SentimentDependencyNode(id,columns[1], columns[2],columns[3],columns[4],
					columns[5],columns[7], head, existentDependents, null);
			nodes.put(id, dn);
			
			if (!nodes.containsKey(head)){
				DependencyNode node; 
				if (head == DependencyNode.ROOT_ADDRESS)
					node = new DependencyNode(head, DependencyNode.ROOT_WORD,
							null, DependencyNode.ROOT_POSTAG,DependencyNode.ROOT_POSTAG,
							null, null,new ArrayList<Short>());
				

				else node =  new DependencyNode(new ArrayList<Short>()); 

				nodes.put(head, new SentimentDependencyNode(node,null));
			}
			nodes.get(head).getDependents().add(id);	
		}		
	}
	
	@Override
	public SentimentDependencyNode getNode(short id){
		return (SentimentDependencyNode) this.nodes.get(id);
	}
	
	public void setNodeSentimentInformation(SentimentInformation si){
		short id = si.getSentimentDependencyNode().getAddress();
		((SentimentDependencyNode) this.nodes.get(id)).setSi(si);
	}
	
	
	private TreeNode getTreeNode(short address){
		
		SentimentDependencyNode sdn = (SentimentDependencyNode) this.nodes.get(address);
		if (sdn.isLeaf()){
			return new TreeNode( sdn.toString(), new ArrayList<TreeNode>());
//			return new TreeNode( sdn.toString().concat(sdn.getSi() != null ? sdn.getSi().getType() : ""), new ArrayList<TreeNode>());
		}
		else{
			List<TreeNode> childrenTreeNode = new ArrayList<TreeNode>();
			
//			String text = sdn.toString().concat(sdn.getSi() != null ? sdn.getSi().getType() : "");
			String text = sdn.toString();
			
			TreeNode tn = new TreeNode(text, childrenTreeNode);
			for (Short child :sdn.getDependents()){
				  childrenTreeNode.add(getTreeNode(child));
			}
			return tn;
		}
	}
	
	public void printLandscapeGraph(short address){
	
		TreeNode tn = getTreeNode(address);
		tn.print();
		System.out.println();
	}
	
	
	public JSONObject toJson(short address){
		
		SentimentDependencyNode sdn = this.getNode(address);
		
		JSONObject childrenNode = new JSONObject();
		JSONArray childrenArray = new JSONArray();
//		JSONArray outerArray = new JSONArray();
		JSONObject innerNode = new JSONObject();
//		JSONArray innerArray = new JSONArray();

		float so = sdn.getSi() == null ? 0 :  sdn.getSi().getSemanticOrientation();
		innerNode.put(ADDRESS, address);
		innerNode.put(POSTAG, sdn.getPostag());
		innerNode.put(SEMANTIC_ORIENTATION, so);
		innerNode.put(DEPENDENCY_TYPE, sdn.getDeprel());
		innerNode.put(IS_NEGATION, sdn.getSi() == null ? false : sdn.getSi().getType().equals(Operation.SHIFT));
		innerNode.put(IS_INTENSIFIER,  sdn.getSi() == null ? false : sdn.getSi().getType().equals(Operation.WEIGHT));
		innerNode.put(WORD, sdn.getWord());
		innerNode.put(CHILDREN, false);
		
		
		if (sdn.isLeaf()) {return innerNode;}
		else{
			
			for (Short child: sdn.getDependents()){
				childrenArray.add(this.toJson(child));
			}
			innerNode.put(CHILDREN, childrenArray);
			return innerNode;
		}

		
	}
	
	public String rawRepresentation(short address){
		
		String rawRepresentation = "";
		SentimentDependencyNode  sdn = (SentimentDependencyNode) this.nodes.get(address);
		float so, pos, neg;
		String operationExplained = "";
		
		if (sdn.getSi() == null) {
			so = 0;
			pos = SentimentInformation.SENTISTRENGTH_NEUTRAL;
			neg = SentimentInformation.SENTISTRENGTH_NEUTRAL;
			
		}
		else {
			so = sdn.getSi().getSemanticOrientation();
			pos = sdn.getSi().getPositiveSentiment();
			neg = sdn.getSi().getNegativeSentiment();
			operationExplained = "{"+sdn.getSi().getOperationExplanation()+"} ";
		}
		
		
		
		rawRepresentation = rawRepresentation.concat(sdn.getWord()+"["+pos+","+neg+"]"+operationExplained
				             
							 );
		
		rawRepresentation = rawRepresentation.concat("(");
		for (Short child : sdn.getDependents()){
			
			rawRepresentation = rawRepresentation.concat(rawRepresentation(child));
			
		}
		rawRepresentation = rawRepresentation.concat(")");
		
		return rawRepresentation;
		
	}
	



	@Override
	public String toString(){
		
		String rawGraph ="";
		for (Short id: this.nodes.keySet()){
			float so =  ((SentimentDependencyNode) this.nodes.get(id)).getSi() != null ? ((SentimentDependencyNode) this.nodes.get(id)).getSi().getSemanticOrientation() : 0;  
			rawGraph+= String.valueOf(id)+":("+ this.nodes.get(id)+"["+((SentimentDependencyNode) this.nodes.get(id)).getSi()+"]"+")\n";
		}
		return rawGraph;
	}

}
