pcap2seq
========

Converts pcap files to Hadoop sequence files.
Pcap is a binary file format that stores network traffic capture (using tcpdump or wireshark). The pcap format consists of all the captured packets (up to a certain length) and packet headers. 

Processing pcap files directly with Hadoop is inefficent since pcap files are not splittable, so a single hadoop worker will work on a single file even if the fill spans multiple splits.

Converting pcap to sequence file format creates a splitable file that can be processed using multiple hadoop workers. 


For more info about pcap file format : http://wiki.wireshark.org/Development/LibpcapFileFormat

Build
========
The project can be built with gradle.
To build it, clone the repository then run :


gradle clean jar

Execute
========
The build process creates a jar file in build/libs/


Run the jar using hadoop binary with three arguments :

1 - input pcap file (A local file on the machine)

2 - name of output file (file:///home/user/outputfile.seq for local filesystem or outputfile.seq for HDFS)

3 - compression : you can use block level compression in sequence file by setting this argument to 'gzip' or 'bzip' to use a specific codec.
For no compression set this argument to 'none'

Example :

hadoop jar pcap2seq-1.2.jar file.pcap file.seq gzip

Converts file.pcap to file.seq with block level compression using GZIP. The output file will be stored in HDFS.

Note : You should have native hadoop libraries to use compression. 
