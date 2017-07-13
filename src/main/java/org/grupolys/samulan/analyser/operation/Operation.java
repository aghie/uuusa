package org.grupolys.samulan.analyser.operation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.grupolys.samulan.rule.Rule;
import org.grupolys.samulan.util.OperationValue;
import org.grupolys.samulan.util.SentimentInformation;

public interface Operation {

	public static final String DEFAULT = "DEFAULT";
	public static final String SHIFT = "SHIFT";
	public static final String WEIGHT = "WEIGHT";
	
	public static final short DEFAULT_PRIORITY = 0;
	public static final short SHIFT_PRIORITY = 2;
	public static final short WEIGHT_PRIORITY = 3;
	
	//public SentimentInformation calculate(SentimentInformation head,List<SentimentInformation> children);
	public OperationValue apply(SentimentInformation head,List<SentimentInformation> children);
	public void updateSentiment(SentimentInformation si);
	public float calculate(float semanticOrientation);
	public String getOperationName();
	public int getPriority();
	public Rule getRule();
	public void setStrategy(ScopeStrategy strategy);
	public ScopeStrategy getStrategy();
	public void setRule(Rule rule);
	public boolean isSemanticOrientationOperation();
}
