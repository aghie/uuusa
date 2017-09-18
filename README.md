# UUUSA

A Java implementation of the formalism described in the article "Universal, Unsupervised (rule-based), Uncovered Sentiment Analysis"

## Prerequisites 

Java 8

Maven (if you are compiling from source code)

You also will need [MaltParser](http://maltparser.org/) and the [Stanford tagger](https://nlp.stanford.edu/software/tagger.shtml) if you plan to train a parser or a tagger to plug it into the system.

# Build the JAR

## Building from source coude

	unzip uuusa-master.zip
	cd uuusa-master
	mvn assembly:assembly

If everything goes fine, you should see something like at the end of the log:

	[INFO] ------------------------------------------------------------------------
	[INFO] BUILD SUCCESS
	[INFO] ------------------------------------------------------------------------
	[INFO] Total time: 7.779 s
	[INFO] Finished at: 2017-08-30T13:28:24+02:00
	[INFO] Final Memory: 58M/1028M
	[INFO] ------------------------------------------------------------------------

This will create a folder /target inside uuusa-master. Inside you should see two .jar files:
	
	samulan-0.1.1.jar
	samulan-0.1.1-jar-with-dependencies.jar

Take the file samulan-0.1.1-jar-with-dependencies.jar to execute uuusa as a standalone application.

## Pre-built versions

We provide some versions of UUUSA, already as JAR's, so you do not need to build them:

[samulan 0.1.0.jar](http://grupolys.org/software/UUUSA/samulan-0.1.0.jar) This version is the system as used in Vilares et al. (2017a). Related data and resources used can be also found in our local repository: http://grupolys.org/software/UUUSA/samulan-0.1.0.jar


## Data

Data/Resources used for our UUUSA model can be found [here](http://grupolys.org/software/UUUSA/)

Data/Resources used for our SISA (Syntactic Iberian Polarity classification) model can be found [here](http://grupolys.org/software/UUUSA/sisa-data.zip)

### Obtaining a trained tagger or parser

We only provided a small set of pretrained taggers and parsers. You might want to use your own. To do so, you must consider some things:

To train a tagger:

Samulan supports models trained using the [Stanford-tagger](https://nlp.stanford.edu/software/tagger.shtml)

Locate the trained model (.tagger) inside your PATH_SENTIDATA/

To train a parser:

Samulan supports parsers trained using [Maltparser-1.7.1.](http://maltparser.org/)

Locate the trained model (.mco) and the features xml inside the PATH_SENTIDATA/maltparser/




## Execute the JAR file


### Over a raw file

java -Dfile.encoding=UTF-8 -jar -Xmx2g USA_JAR -s EN PATH_SENTIDATA -r PATH_OPERATIONS_XML -i PATH_RAW_TEXT -p PATH_UUUSA_PROPERTIES_FILE -v [true|false]

The input must be formatted as a tsv file, where the last column contains the text to be analized.

### Over a CoNLL file
java -Dfile.encoding=UTF-8 -jar -Xmx2g USA_JAR -s EN PATH_SENTIDATA -r PATH_OPERATIONS_XML -c PATH_PARSED_CONLL -p PATH_UUUSA_PROPERTIES_FILE -v [true|false]

### Execution options

	-i	Path to the raw file. Cannot be used together with -c and viceversa.
	-c	Path to a CoNLL file containing the parsed files. You must specify an identifier above the first conll 	 graph of each text (### IDENTIFIER\n"). Check http://grupolys.org/software/UUUSA/en_parsed.conll for an example
															 
	-s	Path to the Sentidata directory. Check http://grupolys.org/software/UUUSA/EN-SentiData/ for an example.
	-e	Encoding. Default utf-8
	-r	Path to the .xml file containing the rules
	-o	Path to the output file with the predictions
	-v	VERBOSE. true|false
	-sc	Selects the type of classification. trinary|binary|so
	-p	Path to the properties file
	-spf	Path to the file where the parsed sentences in CoNLL format will be saved. Useful if you plan to run many experiments. Added in version 0.1.1.


# References

Vilares, D., Gómez-Rodríguez, C., & Alonso, M. A. (2017a). Universal, unsupervised (rule-based), uncovered sentiment analysis. Knowledge-Based Systems, 118, 45-55.

	@article{vilares2017universal,
	  title={Universal, unsupervised (rule-based), uncovered sentiment analysis},
	  author={Vilares, David and G{\'o}mez-Rodr{\'\i}guez, Carlos and Alonso, Miguel A},
	  journal={Knowledge-Based Systems},
	  volume={118},
	  pages={45--55},
	  year={2017},
	  publisher={Elsevier}
	}


If you use SISA, please also cite:

Vilares, D., Garcia, M., Alonso, M. A., & Gómez-Rodríguez, C. (2017b). Towards Syntactic Iberian Polarity Classification. 8th Workshop on Computational Approaches to Subjectivity, Sentiment & Social Media Analysis (WASSA 2017), Copenhagen, Denmark, 2017 (to appear)




