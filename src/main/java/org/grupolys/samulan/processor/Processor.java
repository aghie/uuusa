package org.grupolys.samulan.processor;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import cmu.arktweetnlp.Twokenize;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.trees.TreeTokenizerFactory;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.process.WhitespaceTokenizer.WhitespaceTokenizerFactory;
import edu.stanford.nlp.process.LexerTokenizer;
import edu.stanford.nlp.process.LexedTokenFactory;
import edu.stanford.nlp.process.WordTokenFactory;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.grupolys.samulan.processor.parser.ParserI;
import org.grupolys.samulan.processor.tagger.TaggerI;
import org.grupolys.samulan.processor.tokenizer.TokenizeI;
import org.grupolys.samulan.util.SentimentDependencyGraph;
import org.grupolys.samulan.util.TaggedTokenInformation;

public class Processor {
	
	private TokenizeI tokenizer;
	private TaggerI tagger;
	private ParserI parser;
	
	
	public Processor(TokenizeI tokenizer, TaggerI tagger, ParserI parser){
		this.tokenizer = tokenizer;
		this.tagger  = tagger;
		this.parser = parser;
	}
	

	
	public List<SentimentDependencyGraph> process(String text){
		
//		HashMap<String, String> emoLookupTable = new HashMap<String,String>();
		
//		for (String emoticon : emoticons){
//			System.out.println(emoticon);
//			String emouuid = UUID.randomUUID().toString();
//			text.replaceAll(emoticon, emouuid);
//			emoLookupTable.put(emouuid, emoticon);
//		}
		
		List<SentimentDependencyGraph> sdgs = new ArrayList<SentimentDependencyGraph>();
		
		DocumentPreprocessor dp = new DocumentPreprocessor(new StringReader(text.concat(" ")));	
		dp.setTokenizerFactory(PTBTokenizer.factory(new WordTokenFactory(), "ptb3Escaping=false"));
		
	    for (List<HasWord> sentence : dp) {
	    	
	    	List<String> words = sentence.stream().map( w -> w.toString()).collect(Collectors.toList());
	    	//System.out.println("text: "+text);
			List<String> tokens = this.tokenizer.tokenize(String.join(" ", words));
			//System.out.println("tokens: "+tokens);
			List<TaggedTokenInformation> ttis = this.tagger.tag(tokens);
			sdgs.add(this.parser.parse(ttis));
			
	    }
		return sdgs; //this.parser.parse(ttis);
	}

}
