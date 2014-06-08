package io.ushabti;

public class Driver {

    public static void main(String[] args) throws Exception {
    	
    	if(args.length != 3){
    		System.out.println("Usage : input_pcap_file output_sequence_file [none, gzip, bzip]");
    		return;
    	}
    	    	
    	PcapReader testReader = new PcapReader(args[0]);
    	SequenceFileWriter testWriter = new SequenceFileWriter(args[1], args[2]);
    	
    	System.out.println("Converting pcap file to Hadoop sequence file ...");
    	
    	while (testReader.getPacketTimeStamp() != -1){
    		testWriter.write(testReader.getTimeStamp(), testReader.getPacket());
    	}
    	
    	testWriter.close();
    	testReader.close();
    	
    	System.out.println("Converted " + testReader.getTotalPackets() + " packets.");
    	System.out.println("Read a total of " + testReader.getTotalBytes() + " bytes.");
    }
} 