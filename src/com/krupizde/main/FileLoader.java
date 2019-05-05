/**
 * 
 */
package com.krupizde.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.krupizde.entities.Movie;

/**
 * @author Krupicka
 *
 */
public class FileLoader {

	/**
	 * Method that loads movie names from file.
	 * 
	 * @return Arraylist of movie objects with loaded movie names
	 * @throws IOException 
	 */
	public ArrayList<Movie> loadMovies(String path) throws IOException {
		ArrayList<Movie> temp = new ArrayList<Movie>();
		File f = new File(path);
		if(!f.exists())return null;
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = "";
		while((line=br.readLine())!=null) {
			temp.add(new Movie(line));
		}
		return temp;
	}
}
