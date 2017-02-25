package edu.cpp.cs499.assignment3.findTop10Users;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @author Khanh Nguyen
 */
public class Top10UsersMap extends Mapper<LongWritable, Text, IntWritable, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
    
    @Override
    protected void map(LongWritable key, Text value,Context context) throws IOException, InterruptedException {
    	String[] fields = value.toString().split(",");
		
		Integer userId = new Integer(fields[1]);
		context.write(new IntWritable(userId), one);
	}
}
