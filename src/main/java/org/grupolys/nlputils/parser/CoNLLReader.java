package org.grupolys.nlputils.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.grupolys.nlputils.parser.DependencyGraph;


public class CoNLLReader implements Reader {
	
	
	public DependencyGraph read(String conll){

		if (!conll.equals("")){
			return new DependencyGraph(conll);
		}
		return null;
		
	}
	

	public List<DependencyGraph> read(String path, String encoding) {
		BufferedReader br;
		String line, columns[];
		String conll="";
		List<DependencyGraph> dgs = new ArrayList<DependencyGraph>();
		
		try {
			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), encoding));
			while ((line = br.readLine()) != null){
				
				if (line.equals("")){
					dgs.add(new DependencyGraph(conll));
					conll = "";
				}
				else{
					conll = conll.concat(line+"\n");
				}
			}
			
			if (!conll.equals("")){
				dgs.add(new DependencyGraph(conll));
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dgs;
		// TODO Auto-generated method stub

	}
	
	

	public void write(List<DependencyGraph> graphs, String path) {
		// TODO Auto-generated method stub
		
	}

}
