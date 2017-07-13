package org.grupolys.samulan.processor.tagger;

import java.util.List;

import org.grupolys.samulan.util.TaggedTokenInformation;

public interface TaggerI {
	
	public List<TaggedTokenInformation> tag(List<String> tokens);

}
