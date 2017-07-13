package org.grupolys.samulan.analyser;


import org.grupolys.samulan.analyser.sentimentjoiner.SentimentJoiner;
import org.grupolys.samulan.rule.RuleManager;

public abstract class RuleBasedAnalyser implements Analyser {
	
	protected AnalyserConfiguration ac;
	protected RuleManager rm;
	
	public RuleBasedAnalyser(AnalyserConfiguration ac, RuleManager rm) {
		this.ac = ac;
		this.rm = rm;
	}

	public AnalyserConfiguration getAc() {
		return ac;
	}

	public void setAc(AnalyserConfiguration ac) {
		this.ac = ac;
	}

	public RuleManager getRm() {
		return rm;
	}

	public void setRm(RuleManager rm) {
		this.rm = rm;
	}
	
}
