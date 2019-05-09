/**
 * 
 */
package com.krupizde.entities;

/**
 * @author Krupicka
 *
 */
public class Language {
	public Language() {
		// TODO Auto-generated constructor stub
	}
	private String name;
	private String shor;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShor() {
		return shor;
	}
	public void setShor(String shor) {
		this.shor = shor;
	}
	public Language(String name, String shor) {
		super();
		this.name = name;
		this.shor = shor;
	}
	
	
	
}
