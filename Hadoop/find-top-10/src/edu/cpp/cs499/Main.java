package edu.cpp.cs499;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * @author Khanh Nguyen
 */
public class Main {
	static BufferedReader br;
	static BufferedReader br2;
	static BufferedReader br3;
	
	public static void main(String[] args) {
		HashMap<Integer, Movie> movies = new HashMap<Integer, Movie>();
		HashMap<Integer, UserRating> ratings = new HashMap<Integer, UserRating>();
		
		try {
			br = new BufferedReader(new FileReader("../movies-output/part-r-00000"));
			String line;
			br2 = new BufferedReader(new FileReader("../a3-dataset/movie_titles.txt"));
			String line2;
			
			while ((line = br.readLine()) != null) {
				String[] data = line.split("\\s+");
				Movie movie = new Movie();
				movie.id = Integer.parseInt(data[0]);
				movie.rating = Float.parseFloat(data[1]);
				movies.put(movie.id, movie);
			}
			
			while ((line2 = br2.readLine()) != null) {
				String[] data = line2.split(",");
				if (movies.containsKey(Integer.parseInt(data[0]))) {
					Movie movie = movies.get(Integer.parseInt(data[0]));
					movie.year = Integer.parseInt(data[1]);
					movie.title = data[2];
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		
		Comparator<Movie> comparator = new Comparator<Movie>() {
			@Override
			public int compare(Movie a, Movie b) {
				if (a.rating > b.rating) {
					return -1;
				} else if (a.rating < b.rating) {
					return 1;
				} else {
					return 0;
				}
			}
		};
		
		PriorityQueue<Movie> pq = new PriorityQueue<Movie> (movies.size(), comparator);
		
		for (Integer movieId : movies.keySet()) {
			pq.add(movies.get(movieId));
		}
			
		try {
			FileOutputStream fos = new FileOutputStream(new File("../movies-output/top_10_movies.txt"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			for (int i = 0; i < 10; i++) {
				Movie topMovie = pq.poll();
				bw.write(topMovie.title + ", " + topMovie.year + ", " + topMovie.rating);
				bw.newLine();
			}
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Done!");
		}
		
		try {
			br = new BufferedReader(new FileReader("../users-output/part-r-00000"));
			String line;
			
			while ((line = br.readLine()) != null) {
				String[] data = line.split("\\s+");
				UserRating rating = new UserRating();
				rating.userId = Integer.parseInt(data[0]);
				rating.ratingTimes = Integer.parseInt(data[1]);
				ratings.put(rating.userId, rating);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Comparator<UserRating> comparator2 = new Comparator<UserRating>() {
			@Override
			public int compare(UserRating a, UserRating b) {
				if (a.ratingTimes > b.ratingTimes) {
					return -1;
				} else if (a.ratingTimes < b.ratingTimes) {
					return 1;
				} else {
					return 0;
				}
			}
		};
		
		PriorityQueue<UserRating> pq2 = new PriorityQueue<UserRating> (ratings.size(), comparator2);
		
		for (Integer userId : ratings.keySet()) {
			pq2.add(ratings.get(userId));
		}
		
		try {
			FileOutputStream fos = new FileOutputStream(new File("../users-output/top_10_users.txt"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			for (int i = 0; i < 10; i++) {
				UserRating topUser = pq2.poll();
				bw.write(topUser.userId + ", " + topUser.ratingTimes);
				bw.newLine();
			}
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Done!");
		}
	}
}
