package edu.cpp.cs499.assignment3.findTop10Movies;

import java.io.IOException;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @author Khanh Nguyen
 */
public class Top10MoviesReduce extends Reducer<IntWritable, FloatWritable, IntWritable, FloatWritable> {
	
	@Override
	protected void reduce(IntWritable key, Iterable<FloatWritable> values, Reducer<IntWritable, FloatWritable, IntWritable, FloatWritable>.Context context) throws IOException, InterruptedException {
		Integer movieId = key.get();
		Float sum = (float) 0;
		Integer counter = 0;
		
		for (FloatWritable value:values) {
			sum = sum + value.get();
			counter = counter + 1;
		}
		
		Float average = (float) sum / counter;
		context.write(new IntWritable(movieId), new FloatWritable(average));
	}
}
