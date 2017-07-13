package org.grupolys.samulan.analyser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.grupolys.samulan.analyser.sentimentjoiner.DefaultSentimentJoiner;
import org.grupolys.samulan.analyser.sentimentjoiner.SentimentJoiner;
import org.grupolys.samulan.util.exceptions.SentimentJoinerNotFoundException;

/**
 * Manages the configuration of a Sentiment Analyser specified at a .properties file
 * 
 *
 */
//TODO Add here intensification of CAP words
public class AnalyserConfiguration {

	public static String ALWAYS_SHIFT= "alwaysShift";
	public static String RELAXED_EMOTION_SEARCH = "relaxedEmotionSearch";
	public static String JOINER = "joiner";
	public static String NEGATIVE_WEIGHTING = "negativeWeighting";
	public static String POSITIVE_WEIGHTING = "positiveWeighting";
	public static String DEFAULT_JOINER = "default";
	public static String BINARY_NEUTRAL_AS_NEGATIVE = "binaryNeutralAsNegative";
	
	private float negativeWeigthing;
	private float positiveWeigthing;
	private boolean alwaysShift;
	private boolean relaxedEmotionSearch;
	private boolean binaryNeutralAsNegative;
	private SentimentJoiner sentimentJoiner;
	
	public AnalyserConfiguration(){
		
		this.negativeWeigthing = 1;
		this.positiveWeigthing = 1;
		this.alwaysShift =true;
		this.relaxedEmotionSearch =false;
		this.binaryNeutralAsNegative = true;
		this.sentimentJoiner = new DefaultSentimentJoiner();
		
		
	}

	public AnalyserConfiguration(String pathProperties) throws IOException, SentimentJoinerNotFoundException{
		
		
		InputStream inputStream;
		
		Properties properties = new Properties();
		inputStream = new FileInputStream(pathProperties);
		
		properties.load(inputStream);
		
		this.alwaysShift = Boolean.parseBoolean((String) properties.get(ALWAYS_SHIFT));
		this.relaxedEmotionSearch = Boolean.parseBoolean((String) properties.get(RELAXED_EMOTION_SEARCH));
		this.binaryNeutralAsNegative = Boolean.parseBoolean((String) properties.get(BINARY_NEUTRAL_AS_NEGATIVE));
		this.negativeWeigthing = Float.parseFloat(properties.getProperty(NEGATIVE_WEIGHTING));
		this.positiveWeigthing = Float.parseFloat(properties.getProperty(POSITIVE_WEIGHTING));
		
		String joiner = properties.getProperty(JOINER);
		if (joiner.equals(DEFAULT_JOINER)){ this.sentimentJoiner = new DefaultSentimentJoiner();}
		else {
			throw new SentimentJoinerNotFoundException("ERROR: Sentiment joiner "+joiner+" not valid");
		}

	}
	
	
	public float getNegativeWeigthing() {
		return negativeWeigthing;
	}
	public void setNegativeWeigthing(float negativeWeigthing) {
		this.negativeWeigthing = negativeWeigthing;
	}
	
	
	public float getPositiveWeigthing() {
		return positiveWeigthing;
	}
	public void setPositiveWeigthing(float positiveWeigthing) {
		this.positiveWeigthing = positiveWeigthing;
	}

	public boolean isAlwaysShift() {
		return alwaysShift;
	}

	public void setAlwaysShift(boolean alwaysShift) {
		this.alwaysShift = alwaysShift;
	}

	public boolean isRelaxedEmotionSearch() {
		return relaxedEmotionSearch;
	}

	public void setRelaxedEmotionSearch(boolean relaxedEmotionSearch) {
		this.relaxedEmotionSearch = relaxedEmotionSearch;
	}

	public boolean isBinaryNeutralAsNegative() {
		return binaryNeutralAsNegative;
	}

	public void setBinaryNeutralAsNegative(boolean binaryNeutralAsNegative) {
		this.binaryNeutralAsNegative = binaryNeutralAsNegative;
	}

	public SentimentJoiner getSentimentJoiner() {
		return sentimentJoiner;
	}

	public void setSentimentJoiner(SentimentJoiner joiner) {
		this.sentimentJoiner = joiner;
	}
	
	
	
	
}
