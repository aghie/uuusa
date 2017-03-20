# uuusa

Java implementation of the formalism described in the article "Universal, Unsupervised (rule-based), Uncovered Sentiment Analysis"



## Jar

Download a standalone version of the jar file [here](http://grupolys.org/software/UUUSA/samulan-0.1.0.jar)

Additional files and manual from the original submission of the paper can be found [here](http://grupolys.org/software/UUUSA/)


## Execution

### Over a raw file

java -Dfile.encoding=UTF-8 -jar -Xmx2g USA_JAR -s EN PATH_SENTIDATA -r PATH_OPERATIONS_XML -i PATH_RAW_TEXT -p PATH_UUUSA_PROPERTIES_FILE -v [true|false]

The input must be formatted as a tsv file, where the last column contains the text to be analized. For example:

A very nice place to stay. The rooms are comfortable (try to get a 1st floor junior suite) with lots of space. 

We had been recommended to "The Inn" and weren't disappointed.

I have huge problems and you do not care about them

He is not very handsome, but he has something that I really like


### Over a CoNLL file
java -Dfile.encoding=UTF-8 -jar -Xmx2g USA_JAR -s EN PATH_SENTIDATA -r PATH_OPERATIONS_XML -c PATH_PARSED_CONLL -p PATH_UUUSA_PROPERTIES_FILE -v [true|false]

## Prerequisites 

Java 8

# References

Vilares, D., Gómez-Rodríguez, C., & Alonso, M. A. (2017). Universal, unsupervised (rule-based), uncovered sentiment analysis. Knowledge-Based Systems, 118, 45-55.

# TODO

Upload source code

