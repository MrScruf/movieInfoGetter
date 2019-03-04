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
		Movie m = new Movie("Furious 7");
		InfoGetter ig = new InfoGetter();
		try {
			ig.getMovieInfo(m,new Configurator());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
