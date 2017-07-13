package org.grupolys.samulan.processor.tokenizer;

import java.util.List;

import cmu.arktweetnlp.Twokenize;

public class ARKTwokenizer implements TokenizeI {

	@Override
	public List<String> tokenize(String text) {

		return Twokenize.tokenize(text);
	}

}
