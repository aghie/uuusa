package org.grupolys.samulan.processor.parser;

import java.util.List;

import org.grupolys.samulan.util.SentimentDependencyGraph;
import org.grupolys.samulan.util.TaggedTokenInformation;

public interface ParserI {

	
	public SentimentDependencyGraph parse(List<TaggedTokenInformation> ttis);
}
