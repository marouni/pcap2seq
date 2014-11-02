Overview
========

Converts [pcap files](http://wiki.wireshark.org/Development/LibpcapFileFormat) to Hadoop Sequence files.

Processing pcap files with Hadoop MapReduce is inefficent since pcap files are not splittable, so a single hadoop worker processes the whole pcap file even if the file spans multiple blocks.

Converting pcap to sequence file format creates a splittable and compressable file that can be processed using multiple hadoop workers.

The converter does not require any external jars, so it can be run with the hadoop binary with the default classpath.


Build
========
The project requires [gradle](http://www.gradle.org/downloads).
To build the project, clone the repository then run :
```
gradle clean jar
```
Execute
========
The build process creates a jar file in build/libs/

Run the jar using hadoop binary with three arguments :

1 - input pcap file (A local file on the machine)

2 - name of output file (file:///home/user/outputfile.seq for local filesystem or outputfile.seq for HDFS)

3 - compression : you can use block level compression in sequence file by setting this argument to 'org.apache.hadoop.io.compress.GzipCodec' or 'org.apache.hadoop.io.compress.BZip2Codec' to use a specific codec.
For no compression set this argument to 'none'

Example :
```
hadoop jar pcap2seq-1.2.jar file.pcap file.seq org.apache.hadoop.io.compress.BZip2Codec
```

Converts file.pcap to file.seq with block level compression using GZIP. The output file will be stored in HDFS.

Note : You should have native hadoop libraries to use compression. 
