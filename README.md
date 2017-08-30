# UUUSA

A Java implementation of the formalism described in the article "Universal, Unsupervised (rule-based), Uncovered Sentiment Analysis"

# Build the JAR

## Building from source coude

	unzip uuusa-master.zip
	cd uuusa-master
	mvn assembly:assembly

If nothing goes wrong, you should see something like at the end of the log:

	[INFO] ------------------------------------------------------------------------
	[INFO] BUILD SUCCESS
	[INFO] ------------------------------------------------------------------------
	[INFO] Total time: 7.779 s
	[INFO] Finished at: 2017-08-30T13:28:24+02:00
	[INFO] Final Memory: 58M/1028M
	[INFO] ------------------------------------------------------------------------

This will create a folder /target inside uuusa-master. Inside it you should see two .jars:
	
	samulan-0.1.1.jar
	samulan-0.1.1-jar-with-dependencies.jar

Take the file samulan-0.1.1-jar-with-dependencies.jar to execute uuusa as a standalone application.

## Pre-built versions

We provide some versions of UUUSA, already as JAR's, so you do not need to build them:

[samulan 0.1.0.jar](http://grupolys.org/software/UUUSA/samulan-0.1.0.jar) 

This version is the system as used in Vilares et al. (2017a). Related data and resources used can be also found in our local repository: http://grupolys.org/software/UUUSA/samulan-0.1.0.jar


## Data

Data/Resources used for our UUUSA model can be found [here](http://grupolys.org/software/UUUSA/)

Data/Resources used for our SISA (Syntactic Iberian Polarity classification) model can be found [here](http://grupolys.org/software/UUUSA/sisa-data.zip)

## Execute the JAR file


### Over a raw file

java -Dfile.encoding=UTF-8 -jar -Xmx2g USA_JAR -s EN PATH_SENTIDATA -r PATH_OPERATIONS_XML -i PATH_RAW_TEXT -p PATH_UUUSA_PROPERTIES_FILE -v [true|false]

The input must be formatted as a tsv file, where the last column contains the text to be analized.

### Over a CoNLL file
java -Dfile.encoding=UTF-8 -jar -Xmx2g USA_JAR -s EN PATH_SENTIDATA -r PATH_OPERATIONS_XML -c PATH_PARSED_CONLL -p PATH_UUUSA_PROPERTIES_FILE -v [true|false]

## Prerequisites 

Java 8

Maven (if you are compiling from source code)

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




