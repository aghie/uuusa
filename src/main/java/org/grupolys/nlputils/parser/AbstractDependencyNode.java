package org.grupolys.nlputils.parser;
//package es.udc.fi.dc.lys.nlputils.parser;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class AbstractDependencyNode {
//
//	
//	public static final short ROOT_ADDRESS = 0;
//	public static final String ROOT_WORD = "ROOT";
//	public static final String ROOT_POSTAG = "ROOT_POSTAG";
//
//	protected short address;
//	protected String word;
//	protected String lemma;
//	protected String cpostag;
//	protected String postag;
//	protected String feats;
//	protected String deprel;
//	protected short head;
//	protected List<Short> dependents;
//	
//	
//	
//	public AbstractDependencyNode(){
//		this.address = -1;
//		this.word = "";
//		this.lemma = "";
//		this.cpostag = "";
//		this.postag = "";
//		this.feats = "";
//		this.deprel = "";
//		this.head = -1;
//		this.dependents = new ArrayList<Short>();
//	}
//	
//	public AbstractDependencyNode(List<Short> dependents){
//		this.dependents = dependents;
//	}
//	
//	public AbstractDependencyNode(AbstractDependencyNode node){
//		this.address = node.getAddress();
//		this.word = node.getWord();
//		this.lemma = node.getLemma();
//		this.cpostag = node.getCpostag();
//		this.postag = node.getPostag();
//		this.feats = node.getFeats();
//		this.deprel = node.getDeprel();
//		this.head = node.getHead();
//		this.dependents = node.getDependents();
//	}
//	
//	
//	public AbstractDependencyNode(short address, String word, String lemma, String cpostag,
//			String postag, String feats, String deprel,
//			List<Short> dependents) {
//		super();
//		
//		this.address = address;
//		this.word = word;
//		this.lemma = this.isEmptyField(lemma) ? null : lemma;
//		this.cpostag = this.isEmptyField(cpostag) ? null : cpostag;
//		this.postag =  this.isEmptyField(postag) ? null : postag;
//		this.feats = this.isEmptyField(feats) ? null : feats;
//		this.deprel = deprel;
//		this.dependents = dependents;
//		
//	}
//
//	
//	public AbstractDependencyNode(short address, String word, String lemma, String cpostag,
//			String postag, String feats, String deprel, short head,
//			List<Short> dependents) {
//		super();
//		
//		this.address = address;
//		this.word = word;
//		this.lemma = this.isEmptyField(lemma) ? null : lemma;
//		this.cpostag = this.isEmptyField(cpostag) ? null : cpostag;
//		this.postag =  this.isEmptyField(postag) ? null : postag;
//		this.feats = this.isEmptyField(feats) ? null : feats;
//		this.deprel = deprel;
//		this.head = head;
//		this.dependents = dependents;
//		
//	}
//	
//	
//	
//	public short getAddress() {
//		return address;
//	}
//
//	public void setAddress(short address) {
//		this.address = address;
//	}
//
//	public String getLemma() {
//		return lemma;
//	}
//
//	public void setLemma(String lemma) {
//		this.lemma = lemma;
//	}
//
//	public String getWord() {
//		return word;
//	}
//
//	public void setWord(String word) {
//		this.word = word;
//	}
//
//	public String getCpostag() {
//		return cpostag;
//	}
//
//	public void setCpostag(String cpostag) {
//		this.cpostag = cpostag;
//	}
//
//	public String getPostag() {
//		return postag;
//	}
//
//	public void setPostag(String postag) {
//		this.postag = postag;
//	}
//
//	public String getFeats() {
//		return feats;
//	}
//
//	public void setFeats(String feats) {
//		this.feats = feats;
//	}
//
//	public String getDeprel() {
//		return deprel;
//	}
//
//	public void setDeprel(String deprel) {
//		this.deprel = deprel;
//	}
//
//	public short getHead() {
//		return head;
//	}
//
//	public void setHead(short head) {
//		this.head = head;
//	}
//	
//	public List<Short> getDependents() {
//		return dependents;
//	}
//
//	public void setDependents(List<Short> dependents) {
//		this.dependents = dependents;
//	}
//	
//	
//	public boolean isLeaf(){
//		return this.dependents.isEmpty();
//	}
//	
//	public boolean isRoot(){
//		return this.address == this.ROOT_ADDRESS;
//	}
//
//
//	private boolean isEmptyField(String field){
//		if (field == null) return true;
//		return field.equals("_");
//	}
//	
//}
