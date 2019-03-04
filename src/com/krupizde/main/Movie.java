package com.krupizde.main;

import java.util.ArrayList;

/**
 * Object representing movie with its atributes
 * @author Krupicka
 *
 */
public class Movie {

	private final String name;
	private ArrayList<Actor> actors= null;
	private ArrayList<Genre> genres = null;
	private int movieLength = 0;
	private Studio studio = null;
	
	public Movie(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public ArrayList<Actor> getActors() {
		return actors;
	}

	public void addActor(Actor actor) {
		actors.add(actor);
	}

	public ArrayList<Genre> getGenres() {
		return genres;
	}

	public void addGenre(Genre genre) {
		genres.add(genre);
	}

	public int getMovieLength() {
		return movieLength;
	}

	public void setMovieLength(int movieLength) {
		this.movieLength = movieLength;
	}

	public Studio getStudio() {
		return studio;
	}

	public void setStudio(Studio studio) {
		this.studio = studio;
	}
	
}
