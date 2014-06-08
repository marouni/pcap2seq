package io.ushabti;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.GzipCodec;


public class SequenceFileWriter {
	
	private Configuration configuration;
	private FileSystem fileSystem;
	private Path path;
	
	private IntWritable key = new IntWritable();
	private BytesWritable value = new BytesWritable();
	private BZip2Codec bzip2Codec;
	private GzipCodec gzipCodec;
	
	private SequenceFile.Writer writer = null;
	
	public SequenceFileWriter(String sequenceFile, String compressionSupport) throws IOException{
		configuration = new Configuration();
		fileSystem = FileSystem.get(URI.create(sequenceFile), configuration);
		path = new Path(sequenceFile);
		
		if (compressionSupport.equalsIgnoreCase("gzip"))
			writer = SequenceFile.createWriter(fileSystem, configuration, path,key.getClass(), value.getClass(), org.apache.hadoop.io.SequenceFile.CompressionType.BLOCK, gzipCodec);
		else if (compressionSupport.equalsIgnoreCase("bzip2"))
			writer = SequenceFile.createWriter(fileSystem, configuration, path,key.getClass(), value.getClass(), org.apache.hadoop.io.SequenceFile.CompressionType.BLOCK, bzip2Codec);
		else
			writer = SequenceFile.createWriter(fileSystem, configuration, path,key.getClass(), value.getClass());	
	}
	
	public void write(int pcapkey, byte[] pcapValue) throws IOException{
		
		key.set(pcapkey);
		value.set(pcapValue, 0, pcapValue.length);
		
		writer.append(key, value);
	}
	
	public void close(){
		IOUtils.closeStream(writer);
	}
}
