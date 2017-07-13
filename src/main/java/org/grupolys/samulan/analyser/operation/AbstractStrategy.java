package org.grupolys.samulan.analyser.operation;

import java.util.List;

import org.grupolys.samulan.util.SentimentInformation;

public abstract class AbstractStrategy implements ScopeStrategy {

	/**
	 * It modifies the sentiment information of a @see es.udc.fi.dc.lys.samulan.util.SentimentInformation object
	 * given an @see es.udc.fi.dc.lys.samulan.analyser.Operation instance.
	 * @param operation
	 * @param si Their sentiment information will be updated after running the function.
	 */
//	protected void updateSentiment(Operation operation, SentimentInformation si){
//		//System.out.println("updateBefore "+si);
//		si.setSemanticOrientation(operation.calculate(si.getSemanticOrientation()));
//		si.setPositiveSentiment(Math.max(operation.calculate(si.getPositiveSentiment()),0));
//		System.out.println("antes "+si.getNegativeSentiment());
//		si.setNegativeSentiment(Math.min(operation.calculate(si.getNegativeSentiment()),0));
//		System.out.println("despues "+si.getNegativeSentiment());
//		//System.out.println("updateAfter "+si);
//	}

}
