package org.grupolys.samulan.analyser.sentimentjoiner;

import java.util.List;

import org.grupolys.samulan.util.SentimentInformation;

public interface SentimentJoiner {

	
	public SentimentInformation join(SentimentInformation head, List<SentimentInformation> children);
	
	
}
