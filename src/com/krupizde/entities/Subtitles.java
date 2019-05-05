package com.krupizde.entities;

/**
 * 
 * @author Krupicka
 *
 */
public class Subtitles {

	private Language language = null;
	private String link = null;
	private int idFilm;

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getIdFilm() {
		return idFilm;
	}

	public void setIdFilm(int idFilm) {
		this.idFilm = idFilm;
	}

}
