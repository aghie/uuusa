package org.grupolys.samulan.processor.parser;

import org.grupolys.samulan.util.SentimentDependencyGraph;
import org.grupolys.samulan.util.TaggedTokenInformation;
import org.maltparser.MaltParserService;
import org.maltparser.core.exception.MaltChainedException;
import org.maltparser.core.syntaxgraph.DependencyStructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaltParserWrapper implements ParserI {

	private MaltParserService parser;
	
	
	
	private String getOptions(String pathOptions) throws IOException{
		
		String options = "";
		Pattern pattern = Pattern.compile("\\((.*?)\\)");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(pathOptions));
			try {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
					Matcher match = pattern.matcher(line);
					match.find();
//					System.out.println();
			    	
			    	options = options.concat(match.group(1)+" "+line.split(":")[1]+" ");
			        line = br.readLine();
			    }
			} finally {
			    br.close();
			}			
			return options;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return options;
	}


	public MaltParserWrapper(String pathConf){
		//"-c model0 -m parse -w . -lfi parser.log"
		String featuresName= "";
		String modelName = null;
		String fileName;
		String workingDir, options = ""; 
		
		File folder = new File(pathConf);
		File[] listOfFiles = folder.listFiles();
		workingDir = folder.getAbsolutePath();
		for (File file : listOfFiles) {
				
//				try {
				fileName = file.getAbsolutePath().toString();
				if ( file.isFile() && fileName.endsWith(".xml") ) {
					featuresName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
				}
				    
				if (fileName.endsWith(".mco") ) {
					//pathModel = fileName.replace(".mco", ""); //fileName.substring(fileName.lastIndexOf(File.pathSeparator) + 1);
					modelName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
				}
				    
				if (fileName.endsWith(".conf") ) {
					//pathConf = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
					try {
						options = this.getOptions(fileName);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

			}
			   
			//running dummy sentence to force MaltParser to load all its parameters
			try {
				this.parser = new MaltParserService();
				String command = "-c "+modelName.replace(".mco", "")+" -m parse"+" -w "+workingDir+" -lfi parser.log"+" "+options;//+" -lfi parser.log";
				this.parser.initializeParserModel(command);
				String[] tokens = new String[11];
				tokens[0] = "1\tGrundavdraget\t_\tN\tNN\tDD|SS";
				tokens[1] = "2\tupphör\t_\tV\tVV\tPS|SM";
				tokens[2] = "3\talltså\t_\tAB\tAB\tKS";
				tokens[3] = "4\tvid\t_\tPR\tPR\t_";
				tokens[4] = "5\ten\t_\tN\tEN\t_";
				tokens[5] = "6\ttaxerad\t_\tP\tTP\tPA";
				tokens[6] = "7\tinkomst\t_\tN\tNN\t_";
				tokens[7] = "8\tpå\t_\tPR\tPR\t_";
				tokens[8] = "9\t52500\t_\tR\tRO\t_";
				tokens[9] = "10\tkr\t_\tN\tNN\t_";
				tokens[10] = "11\t.\t_\tP\tIP\t_";
				this.parser.parseTokens(tokens);
			} catch (MaltChainedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	
	public SentimentDependencyGraph parse(List<TaggedTokenInformation> ttis){
		
		SentimentDependencyGraph sdg = null;
		String[] tokens = new String[ttis.size()];
		
//		System.out.println("MaltParserWrapper parse");
		int i=0;
		for (TaggedTokenInformation tti: ttis){
			tokens[i] = tti.toConll();
			i+=1;
		}
		// Parses the Swedish sentence above
		String[] outputTokens;
		try {
			outputTokens = this.parser.parseTokens(tokens);
			sdg = new SentimentDependencyGraph(String.join("\n", outputTokens));
//			// Outputs the with the head index and dependency type information
//			for (int j = 0; j < outputTokens.length; j++) {
//				System.out.println(outputTokens[j]);
//			}
		} catch (MaltChainedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Terminates the parser model
		try {
			this.parser.terminateParserModel();
		} catch (MaltChainedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdg;
		
	}


}
