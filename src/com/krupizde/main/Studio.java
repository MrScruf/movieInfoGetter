/**
 * 
 */
package com.krupizde.main;

/**
 * @author Krupicka
 *
 */
public class Studio {

	private final String name;

	public Studio(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Studio [name=" + name + "]";
	}
	
	
}
