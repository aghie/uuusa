package org.grupolys.samulan.analyser.operation;

import java.util.List;

import org.grupolys.samulan.util.OperationValue;
import org.grupolys.samulan.util.SentimentInformation;

public interface ScopeStrategy {
	
	public static final String BRANCH = "BRANCH";
	public static final String CHILDREN = "CHILDREN";
	public static final String HEAD = "HEAD";
	public static final String N_CHILDREN = "N_CHILDREN";
//	public static final String N_RIGHT_BROTHERS = "RIGHT_BROTHERS";
//	public static final String N_LEFT_BROTHERS = "LEFTBROTHERS";
//	public static final String SUBTREE_FROM_HEAD = "SUBTREE_FROM_HEAD";
	public static final String FIRST_SUBJECTIVE_BRANCH = "FIRST_SUBJECTIVE_BRANCH";
	
	public OperationValue apply(SentimentInformation head, 
			               List<SentimentInformation> children,
			               Operation operation);
	
}
