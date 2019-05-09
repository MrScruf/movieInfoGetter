/**
 * 
 */
package com.krupizde.main;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krupizde.entities.Movie;
import com.krupizde.entity.generators.InfoGetter;
import com.krupizde.persistence.MovieDao;
import com.krupizde.persistence.interfaces.IMovieDao;

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
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("STARTING INSERTING TO DATABASE");
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		IMovieDao movDao = MovieDao.getDao();
		for(Movie m : loaded) {
			try {
				movDao.saveMovie(m);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("FINISHED RUNNING");
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
}
