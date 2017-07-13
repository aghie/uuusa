package org.grupolys.samulan.processor.tagger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.grupolys.samulan.processor.tokenizer.TokenizeI;
import org.grupolys.samulan.util.TaggedTokenInformation;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class MaxentStanfordTagger implements TaggerI {

	public static final String STANFORD_SEPARATOR = "_";
	
	private MaxentTagger tagger;
	
	public MaxentStanfordTagger(String pathTrainedModel){

		this.tagger = new MaxentTagger(pathTrainedModel);


	}
	
//	private String toConll(String taggedText){
//		
//	}
	
	@Override
	public List<TaggedTokenInformation> tag(List<String> tokens) {
		// TODO Auto-generated method stub
		ArrayList<TaggedTokenInformation> ttis = new ArrayList<TaggedTokenInformation>();
		String tag, token;
		String tagged_text = this.tagger.tagTokenizedString(String.join(" ", tokens));
		short i = 1;
		for (String tag_token :tagged_text.split(" ")){
			token = tag_token.substring(0, tag_token.lastIndexOf(STANFORD_SEPARATOR));
			tag = tag_token.substring(tag_token.lastIndexOf(STANFORD_SEPARATOR) + 1);
			ttis.add(new TaggedTokenInformation(i,token,null,tag,tag,null));
			i+=1;
		}
		return ttis;
	}

}
