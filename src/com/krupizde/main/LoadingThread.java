/**
 * 
 */
package com.krupizde.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.krupizde.entities.Movie;
import com.krupizde.entity.generators.InfoGetter;

/**
 * @author Krupicka
 *
 */
public class LoadingThread extends Thread{

	ArrayList<File> loadedFiles = null;
	public LoadingThread(ArrayList<File> loadedFiles) {
		this.loadedFiles=loadedFiles;
	}
	
	public void run() {
		InfoGetter ig = new InfoGetter();
		FileLoader fl = new FileLoader();
		ArrayList<Movie> loaded = new ArrayList<Movie>();
		for(File f : loadedFiles) {
			System.out.println("Loading file: " + f.getName());
			try {
				loaded.addAll(fl.loadMovies(f.getAbsolutePath()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(Movie m : loaded) {
			System.out.println("Starting movie: "+ m.getName());
			try {
				ig.getMovieInfo(m);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
