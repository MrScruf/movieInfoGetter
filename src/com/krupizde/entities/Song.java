/**
 * 
 */
package com.krupizde.entities;

/**
 * @author Krupicka
 *
 */
public class Song {

	private String name;
	private Author author;
	private String link;
	public Song(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return "Songs [name=" + name + ", author=" + author + ", link=" + link + "]";
	}
	
	
	
	
}
