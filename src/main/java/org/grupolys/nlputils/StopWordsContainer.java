package org.grupolys.nlputils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class StopWordsContainer {
	
	private HashSet<String> stopwords;
	
	public StopWordsContainer(){
		this.stopwords = new HashSet<String>();
	}

	public void add(String path, String encoding){
		BufferedReader br;
		String line;
		String columns[];
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), encoding));
			while ((line = br.readLine()) != null){
				columns = line.replace("\n", "").split("\t");
				for (String c : columns){
					this.stopwords.add(c);
				}
		}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(this.stopwords);
	}
	
	public void add(HashSet<String> stopwords){
		this.stopwords.addAll(stopwords);
	}

	public HashSet<String> getStopwords() {
		return stopwords;
	}

	public void setStopwords(HashSet<String> stopwords) {
		this.stopwords = stopwords;
	}
	
	
	
	
	
	
}
