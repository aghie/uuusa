package org.grupolys.samulan.analyser;

import java.util.List;

import org.grupolys.nlputils.parser.DependencyGraph;
import org.grupolys.samulan.util.SentimentDependencyGraph;
import org.grupolys.samulan.util.SentimentInformation;

/**
 * Interface for Sentiment Analyser
 * @author David Vilares
 *
 */
public interface Analyser {

	public SentimentInformation analyse(SentimentDependencyGraph dg, short address);
	public SentimentInformation merge(List<SentimentInformation> sis);
}
