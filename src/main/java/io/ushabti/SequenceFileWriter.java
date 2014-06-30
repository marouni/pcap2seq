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
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.util.ReflectionUtils;


public class SequenceFileWriter {
	
	private Configuration configuration;
	private FileSystem fileSystem;
	private Path path;
	
	private IntWritable key = new IntWritable();
	private BytesWritable value = new BytesWritable();
	
	private SequenceFile.Writer writer = null;
	
	public SequenceFileWriter(String sequenceFile, String compressionCodec) throws IOException, ClassNotFoundException{
		configuration = new Configuration();
		fileSystem = FileSystem.get(URI.create(sequenceFile), configuration);
		path = new Path(sequenceFile);
		
		
		if (compressionCodec.equalsIgnoreCase("org.apache.hadoop.io.compress.BZip2Codec")||compressionCodec.equalsIgnoreCase("org.apache.hadoop.io.compress.GzipCodec")) {
			Class<?> codecClass = Class.forName(compressionCodec);
			CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass, configuration);
			writer = SequenceFile.createWriter(fileSystem, configuration, path,key.getClass(), value.getClass(), org.apache.hadoop.io.SequenceFile.CompressionType.BLOCK, codec);
		}
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
