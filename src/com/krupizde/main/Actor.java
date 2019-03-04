/**
 * 
 */
package com.krupizde.main;

/**
 * @author Krupicka
 *
 */
public class Actor {

	private final String jmeno;
	private final  String prijmeni;
	public Actor(String jmeno, String prijmeni) {
		super();
		this.jmeno = jmeno;
		this.prijmeni = prijmeni;
	}
	public String getJmeno() {
		return jmeno;
	}
	public String getPrijmeni() {
		return prijmeni;
	}
	@Override
	public String toString() {
		return "Actor [jmeno=" + jmeno + ", prijmeni=" + prijmeni + "]";
	}
	
}
