package org.grupolys.samulan.util;

public class TaggedTokenInformation {
	
	public static final String EMPTYCONLLVALUE = "_";

	private Short id;
	private String word;
	private String lemma;
	private String cpostag;
	private String postag;
	private String feats;
	
	
	public TaggedTokenInformation(short id,String word, String lemma,String cpostag,
						String postag, String feats){
		this.id  = id;
		this.word = word;
		this.lemma = lemma;
		this.cpostag = cpostag;
		this.postag = postag;
		this.feats = feats;
	}


	public String getWord() {
		return word;
	}


	public void setWord(String word) {
		this.word = word;
	}

	
	public String getLemma() {
		return lemma;
	}


	public void setLemma(String lemma) {
		this.lemma = lemma;
	}


	public String getCpostag() {
		return cpostag;
	}


	public void setCpostag(String cpostag) {
		this.cpostag = cpostag;
	}


	public String getPostag() {
		return postag;
	}


	public void setPostag(String postag) {
		this.postag = postag;
	}


	public String getFeats() {
		return feats;
	}


	public void setFeats(String feats) {
		this.feats = feats;
	}
	
	public String toConll(){
		String[] conll  = new String[6];
		conll[0] = id != null ? Short.toString(id) : EMPTYCONLLVALUE;
		conll[1] = word != null ? word : EMPTYCONLLVALUE;
		conll[2] = lemma != null ? lemma : EMPTYCONLLVALUE;
		conll[3] = cpostag != null ? cpostag: EMPTYCONLLVALUE;
		conll[4] = postag != null ? postag: EMPTYCONLLVALUE;
		conll[5] = feats != null ? feats : EMPTYCONLLVALUE;
		return String.join("\t", conll);
	}
	
}
