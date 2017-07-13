package org.grupolys.samulan.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.grupolys.samulan.analyser.operation.Operation;
import org.grupolys.samulan.util.exceptions.OperationNotFoundException;


public class Dictionary {
	
	public static final String EMOTION_LIST = "EmotionLookupTable.txt";
	public static final String BOOSTER_LIST =	"BoosterWordList.txt";
	public static final String LEMMAS_LIST = "LemmasList.txt";
	public static final String EMOTICON_LIST = "EmoticonLookupTable.txt";
	public static final String WORD_LIST = "EnglishWordList.txt";
	public static final String IDIOM_LIST = "IdiomLookupTable.txt";
	public static final String IRONY_LIST = "IronyTerms.txt";
	public static final String NEGATING_LIST = "NegatingWordList.txt";
	public static final String QUESTION_LIST = "QuestionWords.txt";
	public static final String SLANG_LIST = "SlangLookupTable.txt";
	public static final String ADVERSATIVE_LIST ="AdversativeList.txt";
	public static final String LEMMAS_STRIPPERS = "WordToLemmasStrippingList.txt";
	public static final String POSTAG_SEPARATOR = "_";
			
/*
 * It Read and manages the content found in SentiData
 * 
 */

	private HashMap<String,Float> values;
	private TreeMap<String,Float> stemValues;
	private HashMap<String,HashMap<String,Float>> classValues;
	private HashMap<String,String> lemmas;
	private HashMap<String,HashMap<String,String>> classLemmas;
	private HashMap<String,Float> emoticons;
	private Set<String> negatingWords;
	private Set<String> adversativeWords;
	private Set<String> adverbsIntensifiers;
	private HashMap<String,ArrayList<String>> lemmasStrippers;
	private boolean thereIsClassEmotionDict;
	
	public Dictionary(){
		/*Empty dictionary*/
		this.values = new HashMap<String,Float>();
//		this.stemValues = new TreeMap<String,Float>();
		this.stemValues = new TreeMap<String,Float>(new Comparator<String>(){
	        @Override
	        public int compare(String s1, String s2) {
	            if (s1.length() > s2.length()) {
	                return -1;
	            } else if (s1.length() < s2.length()) {
	                return 1;
	            } else {
	                return s1.compareTo(s2);
	            }
	        }
		});
		this.classValues = new HashMap<String,HashMap<String,Float>>();
		this.lemmas = new HashMap<String,String>();
		this.classLemmas = new HashMap<String,HashMap<String,String>>();
		this.emoticons = new HashMap<String,Float>();
		this.negatingWords = new HashSet<String>();
		this.adversativeWords = new HashSet<String>();
		this.adverbsIntensifiers = new HashSet<String>();
		this.lemmasStrippers = new HashMap<String,ArrayList<String>>();
		this.thereIsClassEmotionDict = false;
	}
	
	
	public Dictionary(HashMap<String, Float> values, HashMap<String,String> lemmas){
		this.values = values;
		this.lemmas = lemmas;
		this.stemValues = new TreeMap<String,Float>(new Comparator<String>(){
			@Override
			public int compare(String o1, String o2){
				return o2.length() - o1.length();
			}
		});
		this.classValues = new HashMap<String,HashMap<String,Float>>();
		this.classLemmas = new HashMap<String,HashMap<String,String>>();
		this.emoticons = new HashMap<String, Float>();
		this.negatingWords = new HashSet<String>();
		this.adversativeWords = new HashSet<String>();
		this.adverbsIntensifiers = new HashSet<String>();
		this.lemmasStrippers = new HashMap<String,ArrayList<String>>();
		this.thereIsClassEmotionDict = false;
	}
	
	
	public Dictionary(HashMap<String, Float> values, HashMap<String,String> lemmas,
			HashMap<String, HashMap<String, Float>> classValues,
			HashMap<String,HashMap<String,String>> classLemmas,
			HashMap<String,Float> emoticons,
			Set<String> negatingWords, Set<String> adversativeWords){
		//TODO pass stemValues as parameter
		this.values = values;
		this.lemmas = lemmas;
		this.stemValues = new TreeMap<String,Float>(new Comparator<String>(){
			@Override
			public int compare(String o1, String o2){
				return o2.length() - o1.length();
			}
		});
		this.classValues = classValues;
		this.classLemmas = classLemmas;
		this.emoticons = emoticons;
		this.negatingWords = negatingWords;
		this.adversativeWords = adversativeWords;
		this.adverbsIntensifiers = new HashSet<String>();
		this.lemmasStrippers = new HashMap<String,ArrayList<String>>();
		this.thereIsClassEmotionDict = this.getClassValues().isEmpty() ? false : true;
	}
	
	
	
	public HashMap<String, Float> getValues() {
		return values;
	}

	public void setValues(HashMap<String, Float> values) {
		this.values = values;
	}

	public HashMap<String, HashMap<String, Float>> getClassValues() {
		return classValues;
	}

	public void setClassValues(HashMap<String, HashMap<String, Float>> classValues) {
		this.classValues = classValues;
	}

	public HashMap<String, String> getLemmas() {
		return lemmas;
	}

	public void setLemmas(HashMap<String, String> lemmas) {
		this.lemmas = lemmas;
	}

	public HashMap<String, HashMap<String, String>> getClassLemmas() {
		return classLemmas;
	}

	public void setClassLemmas(HashMap<String, HashMap<String, String>> classLemmas) {
		this.classLemmas = classLemmas;
	}

	
	public HashMap<String, Float> getEmoticons() {
		return emoticons;
	}


	public void setEmoticons(HashMap<String, Float> emoticons) {
		this.emoticons = emoticons;
	}

	

	public Set<String> getAdversativeWords() {
		return adversativeWords;
	}


	public void setAdversativeWords(Set<String> adversativeWords) {
		this.adversativeWords = adversativeWords;
	}

	

	public Set<String> getAdverbsIntensifiers() {
		return adverbsIntensifiers;
	}


	public void setAdverbsIntensifiers(Set<String> adverbsIntensifiers) {
		this.adverbsIntensifiers = adverbsIntensifiers;
	}


	private float getValue(String lemma){
		/**
		 * @return The semantic orientation of the lemma or zero if the word has no subjectivity.
		 */
		Float value = values.get(lemma);
		if (value == null){
			//TODO this is not efficient
			for (String stem : this.stemValues.keySet()){
//				System.out.println("lemma: "+lemma+" stem: "+stem+" "+lemma.startsWith(stem));
				if (lemma.startsWith(stem)){
//					System.out.println("Entra lemma: "+lemma+" stem: "+stem);
					return this.stemValues.get(stem);
				}
			}
			return 0;
		}
		return value;
	}
	


	private BufferedReader getBufferedReader(String path, String encoding){
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), encoding));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return br;
		
	}
	
	private void readSentiDataList(String pathToSentiDataFile, String classValue){
		BufferedReader br;
		String line;
		HashMap svalues = new HashMap<String,Float>();
		br = getBufferedReader(pathToSentiDataFile, "utf-8");
		try {
			while ((line = br.readLine()) != null){
				if (!line.isEmpty()){
					String[] ls = line.split("\t");
					if (ls.length >= 2){
						String word = ls[0]; 
						try{
						float so = Float.parseFloat(ls[1]);
						//It priorizes the existing value
//						System.out.println("word: "+word+" endswith(*): "+word.endsWith("*")+" so:"+so+" length dict: "+this.stemValues.size());
//						System.out.println("aux salues size: "+svalues.size());
						if (!word.endsWith("*")){
							this.values.put(word, so);
						}
						else{
//							System.out.println("...adding: "+word.replace("*",""));
//							System.out.println(this.stemValues);
//							svalues.put(word.replace("*",""), so);
//							if (this.stemValues.isEmpty()){
//								System.out.println("...adding: "+word.replace("*",""));
//							}
							this.stemValues.put(word.replace("*",""),so);
						}
						if (classValue != null){
							if (this.classValues.containsKey(classValue))
								this.classValues.get(classValue).put(word,so);
							else{
								HashMap<String,Float> aux = new HashMap<String,Float>();
								aux.put(word, so);
								this.classValues.put(classValue,aux );
							}
						}
						} catch (NumberFormatException e){
							//ERROR reading file with two columns, but second column is not a float
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void readBoosterList(String pathToSentiDataFile, String classValue){
		BufferedReader br;
		String line;
		
		br = getBufferedReader(pathToSentiDataFile, "utf-8");
		try {
			while ((line = br.readLine()) != null){
				if (!line.isEmpty()){
					String[] ls = line.split("\t");
					if (ls.length >= 2){
						String word = ls[0]; 
						try{
						float so = Float.parseFloat(ls[1]);
						if (classValue != null){
							if (this.classValues.containsKey(classValue))
								this.classValues.get(classValue).put(word,so);
							else{
								HashMap<String,Float> aux = new HashMap<String,Float>();
								aux.put(word, so);
								this.classValues.put(classValue,aux );
							}
						}
						} catch (NumberFormatException e){
							//ERROR reading file with two columns, but second column is not a float
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	private void readLemmaList(String pathToSentiDataFile, String classValue){
		BufferedReader br;
		String line;
		
		br = getBufferedReader(pathToSentiDataFile, "utf-8");
		try {
			while ((line = br.readLine()) != null){
				if (!line.isEmpty()){
					String[] ls = line.split("\t");
					if (ls.length == 2){
						String word = ls[0];
						String lemma = ls[1];
						this.lemmas.put(word, lemma);
					}
					else {
						if (ls.length == 3){
							
							String postag = ls[0];
							String word = ls[1];
							String lemma = ls[2];	
							HashMap<String,String> aux = new HashMap<String,String>();
							if (this.classLemmas.containsKey(postag)){
								this.classLemmas.get(postag).put(word, lemma);
							}
							else{
								aux.put(word, lemma);
								this.classLemmas.put(postag, aux);
							}
						}
						else { System.err.println("Non standard number of columns Lemmas_list file");}
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void readEmoticonList(String pathToSentiDataFile){
		
		BufferedReader br;
		String line;
		
		br = getBufferedReader(pathToSentiDataFile, "utf-8");
		try {
			while ((line = br.readLine()) != null){
				if (!line.isEmpty()){
					String[] ls = line.split("\t");
					if (ls.length == 2){
						String emoticon = ls[0];
						Float value =  Float.valueOf(ls[1]);
						
						this.emoticons.put(emoticon, value);
						
					}
					else { System.err.println("Non standard number of columns on Emoticon_list file");}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void readLemmasStrippers(String pathToWordToLemmasStrippingList){
		
		BufferedReader br;
		String line;
		
		br = getBufferedReader(pathToWordToLemmasStrippingList, "utf-8");
		try {
			while ((line = br.readLine()) != null){
				if (!line.isEmpty()){
					String[] ls = line.split("\t");
					if (ls.length == 2){
						String emoticon = ls[0];
						ArrayList<String> strippers =  new ArrayList<String>(Arrays.asList(ls[1].split(",")));
						this.lemmasStrippers.put(emoticon, strippers);
					}
					else { System.err.println("Non standard number of columns on Emoticon_list file");}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void readNegatingWordsList(String pathToSentiDataFile){
		
		BufferedReader br;
		String line;
		br = getBufferedReader(pathToSentiDataFile, "utf-8");
		try {
			while ((line = br.readLine()) != null){
				if (!line.isEmpty()){
					String[] ls = line.split("\t");
					if (ls.length == 1){
						String negatingWord = ls[0];
						this.negatingWords.add(negatingWord);
					}
					else { System.err.println("Non standard number of columns NegatingWordList.txt file");}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Files inside SentiData must be in utf-8 format.
	 * 
	 */
	public void readSentiData(String pathToSentiData){
		
		File sentiData = new File(pathToSentiData);
		File[] sentiDataFiles = sentiData.listFiles();
		for (File f: sentiDataFiles){
			if (f.isFile()){
				
				if (f.getName().equals(EMOTION_LIST)){
					readSentiDataList(f.getAbsolutePath(),null);
				}
				else if (f.getName().endsWith(EMOTION_LIST)) {
					this.thereIsClassEmotionDict = true;
					readSentiDataList(f.getAbsolutePath(),f.getName().split(POSTAG_SEPARATOR)[0]);
				}
				
				if (f.getName().equals(BOOSTER_LIST)) {
					readBoosterList(f.getAbsolutePath(),Operation.WEIGHT);}
				
				
				if (f.getName().equals(NEGATING_LIST)){
					readNegatingWordsList(f.getAbsolutePath());
				}
				if (f.getName().equals(LEMMAS_LIST)) {
					readLemmaList(f.getAbsolutePath(),LEMMAS_LIST);
				}
				if (f.getName().equals(EMOTICON_LIST)){
					readEmoticonList(f.getAbsolutePath());
				}
				if (f.getName().equals(ADVERSATIVE_LIST)){
					readNegatingWordsList(f.getAbsolutePath());
				}
				if (f.getName().equals(LEMMAS_STRIPPERS)){
					readLemmasStrippers(f.getAbsolutePath());
				}
			}
			else{
				//TODO show missing files
			}
		}
		//this.getAdverbsIntensfiers();
	}
	
	
	public boolean isWeight(String lemma){
	//	System.out.println(lemma+" "+this.classValues.get(Operation.WEIGHT).get(lemma));
		if (this.classValues.get(Operation.WEIGHT) == null) return false;
		return this.classValues.get(Operation.WEIGHT).get(lemma) != null;
	}
	
	

	
	public float getValue(String classWord, String lemma, boolean relaxed){
		/**
		 * @return The semantic orientation of the lemma or zero if the word has no subjectivity.
		 */
		Float value = null;
		String lowerCaseLemma = lemma.toLowerCase();
		HashMap<String,Float> values = classValues.get(classWord);
		if (values != null){
			value = values.get(lowerCaseLemma);
			if (this.adverbsIntensifiers.contains(lowerCaseLemma)){
		//		System.out.println("Entra getValue: "+lowerCaseLemma);
			//	value = (float) 0;
			}
		}
		
		if ( ((values == null || value == null) && relaxed) || ((values == null || value == null) && !this.thereIsClassEmotionDict) )
		{
			value = getValue(lowerCaseLemma);
		}
		
	
		if (this.emoticons.containsKey(lowerCaseLemma)){
			value = this.emoticons.get(lowerCaseLemma);
		}
		
		if (value == null) return 0;
		return value;
	}
	
	
	public Set<String> getNegatingWords() {
		return negatingWords;
	}

	

	public void setNegatingWords(Set<String> negatingWords) {
		this.negatingWords = negatingWords;
	}


	/**
	 * @return The lemma of the word or the word itself if no lemma was found
	 */
	private String getLemma(String word){
		String lemma = lemmas.get(word);
		if (lemma == null) return word;
		return lemma;
	}
	
	/**
	 * @return The lemma of the word or the word itself if no lemma was found
	 */	
	
	public String getLemma(String postag, String word){
		//TODO this functions is the one converting words to lowercase, is there a better option?
		String wordLowerCase = word.toLowerCase();
		String lemma;
		HashMap<String,String> lemmas = classLemmas.get(postag);
		if (lemmas == null) return getLemma(wordLowerCase);
		lemma = lemmas.get(wordLowerCase); 
		if (lemma == null) return wordLowerCase;
		return lemma;
	}
	
	/**
	 * Gets a possible lemma by stripping the end of the word, based on substrings that represent suffixes
	 * @param postag Part-of-speech tag of the word
	 * @param word
	 * @return
	 */
	public String getStrippedLemma(String postag, String word){
		String wordLowerCase = word.toLowerCase();
		String lemma = null;
		ArrayList<String> postagLemmasStrippers = this.lemmasStrippers.get(postag);
		if (postagLemmasStrippers != null){
			for (String pls : postagLemmasStrippers){
				if (word.endsWith(pls)){
					lemma = word.substring(0, word.length()-pls.length());
				}
			}
		}
		//System.out.println("word: "+wordLowerCase+" lemmaStripped: "+lemma);
		if (lemma == null) return wordLowerCase;
		return lemma;
		
	}
	
	
	public void addHashMapValues(HashMap<String, Float> values){
		this.values.putAll(values);
	}
	
	public void addHashMapLemmas(HashMap<String, String> lemmas){
		this.lemmas.putAll(lemmas);
	}
	
	public void addPostagHashMapValues(String operation, HashMap<String, Float> values){
		/**
		 *@param postag
		 *@param values
		 *
		 */
		if (!operation.equals(Operation.WEIGHT)){
			this.thereIsClassEmotionDict = true;
		}
		classValues.put(operation, values);
	}
	
	public void addPostagHashMapLemmas(String postag, HashMap<String,String> lemmas){
		/**
		 * @param postag
		 * @param lemmas
		 */
		classLemmas.put(postag, lemmas);
	}
	
}
