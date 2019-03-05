package com.krupizde.main;

import java.util.ArrayList;

/**
 * Object representing movie with its atributes
 * 
 * @author Krupicka
 *
 */
public class Movie {

	private final String name;
	private ArrayList<Actor> actors = null;
	private ArrayList<Genre> genres = null;
	private int movieLength = 0;
	private Studio studio = null;
	private ArrayList<Song> songs = null;

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

	public void setActors(ArrayList<Actor> actors) {
		this.actors = actors;
	}

	public void setGenres(ArrayList<Genre> genres) {
		this.genres = genres;
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

	public ArrayList<Song> getSongs() {
		return songs;
	}

	public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}

	/**
	 * Method, that checks some basic errors from info loading from google. Nothing
	 * is flawless.
	 * 
	 * @param m Movie to repair
	 */
	public static void repairMovie(Movie m) {
		if (m.getStudio() == null)
			m.setStudio(new Studio("Unknown"));
		if (m.getActors() != null) {
			for (int i = 0; i < m.getActors().size(); i++) {
				if (m.getActors().get(i).getJmeno() == null || m.getActors().get(i).getPrijmeni() == null) {
					m.actors.remove(m.actors.get(i));
					i--;
				}

			}
		}

		if (m.genres != null) {
			for (int i = 0; i < m.genres.size(); i++) {
				if (m.genres.get(i).getName().isEmpty()) {
					m.genres.remove(m.genres.get(i));
					i--;
				}
			}
		}
		if (m.getSongs() != null) {
			for (int i = 0; i < m.songs.size(); i++) {
				if (m.songs.get(i).getAuthor() == null) {
					m.songs.get(i).setAuthor(new Author("Unknown", null));
				} else if (m.songs.get(i).getAuthor().getFirstName() == null) {
					m.songs.get(i).setAuthor(new Author("Unknown", null));
				}
			}
		}

	}

}
