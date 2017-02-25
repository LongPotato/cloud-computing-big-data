package edu.cpp.cs499.assignment3.findTop10Movies;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @author Khanh Nguyen
 */
public class Top10MoviesMap extends Mapper<LongWritable, Text, IntWritable, FloatWritable> {
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, IntWritable, FloatWritable>.Context context)
			throws IOException, InterruptedException {

		String[] fields = value.toString().split(",");
		
		Integer movieId = new Integer(fields[0]);
		Float marks = new Float(fields[2]);
		context.write(new IntWritable(movieId), new FloatWritable(marks));
	}
}
