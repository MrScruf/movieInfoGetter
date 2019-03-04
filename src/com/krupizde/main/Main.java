/**
 * 
 */
package com.krupizde.main;

import java.io.IOException;

/**
 * @author Krupicka
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Movie m = new Movie("The Dark Knight");
		InfoGetter ig = new InfoGetter();
		try {
			ig.getMovieInfo(m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
