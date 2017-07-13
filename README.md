# UUUSA

A Java implementation of the formalism described in the article "Universal, Unsupervised (rule-based), Uncovered Sentiment Analysis"


## Jar

Download a standalone version of the jar file [here](http://grupolys.org/software/UUUSA/samulan-0.1.0.jar)

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

# References

Vilares, D., Gómez-Rodríguez, C., & Alonso, M. A. (2017). Universal, unsupervised (rule-based), uncovered sentiment analysis. Knowledge-Based Systems, 118, 45-55.

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

Vilares, D., Garcia, M., Alonso, M. A., & Gómez-Rodríguez, C. (2017). Towards Syntactic Iberian Polarity Classification. 8th Workshop on Computational Approaches to Subjectivity, Sentiment & Social Media Analysis (WASSA 2017), Copenhagen, Denmark, 2017 (to appear)




