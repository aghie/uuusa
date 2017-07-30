package org.grupolys.nlputils.parser;

import java.util.List;

import org.grupolys.nlputils.parser.DependencyGraph;

public interface Reader {
	
	List<DependencyGraph> read(String path, String encoding);
	void write(List<DependencyGraph> graphs,String path);
	
}
