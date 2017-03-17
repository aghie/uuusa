# uuusa

Java implementation of the formalism described in the article "Universal, Unsupervised (rule-based), Uncovered Sentiment Analysis"



# uuusa a a jar

[jar](http://grupolys.org/software/UUUSA/samulan-0.1.0.jar)

Additional files and manual from the original submission of the paper can be found [here](http://grupolys.org/software/UUUSA/)


# Execution

## Over a raw file

java -Dfile.encoding=UTF-8 -jar -Xmx2g USA_JAR -s EN PATH_SENTIDATA -r PATH_OPERATIONS_XML -i PATH_RAW_TEXT -p PATH_UUUSA_PROPERTIES_FILE -v [true|false]

## Over a CoNLL file
java -Dfile.encoding=UTF-8 -jar -Xmx2g USA_JAR -s EN PATH_SENTIDATA -r PATH_OPERATIONS_XML -c PATH_PARSED_CONLL -p PATH_UUUSA_PROPERTIES_FILE -v [true|false]

# Prerequisites 

Java 8

# References

Vilares, D., Gómez-Rodríguez, C., & Alonso, M. A. (2017). Universal, unsupervised (rule-based), uncovered sentiment analysis. Knowledge-Based Systems, 118, 45-55.

# TODO

Upload source code

