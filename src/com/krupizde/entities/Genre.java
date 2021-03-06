/**
 * 
 */
package com.krupizde.entities;

/**
 * @author Krupicka
 *
 */
public class Genre {

	private final String name;

	public Genre(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Genre [name=" + name + "]";
	}
	
	
}
