package com.krupizde.entity.generators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.krupizde.entities.Actor;
import com.krupizde.entities.Author;
import com.krupizde.entities.Genre;
import com.krupizde.entities.Movie;
import com.krupizde.entities.Song;
import com.krupizde.entities.Studio;

/**
 * 
 * @author Krupicka
 *
 */
public class InfoGetter {

	/**
	 * Returns nothing, just updates movie that was send as parameter.
	 * 
	 * @param m movie, which will be updated with new information
	 * @throws IOException
	 */
	public void getMovieInfo(Movie m) throws IOException {
		m.setActors(getActors(m.getName()));
		m.setStudio(getStudio(m.getName()));
		m.setMovieLength(getLength(m.getName()));
		m.setSongs(getSongsAndAutors(m.getName()));
		m.setGenres(getMovieGenres(m.getName()));
		Movie.repairMovie(m);
		System.out.println(m.getGenres());
		System.out.println("------------------------------------------------------");
		System.out.println("Actors:" + m.getActors().toString());
		System.out.println("------------------------------------------------------");
		System.out.println("Studio:" + m.getStudio());
		System.out.println("------------------------------------------------------");
		System.out.println("Movie length: " + m.getMovieLength());
		System.out.println("------------------------------------------------------");
		System.out.println("Songs: " + m.getSongs());
		System.out.println("------------------------------------------------------");
	}

	/**
	 * Method that loads google search and then filters for elements by class I
	 * found using page eplorer in google chrome before.
	 * 
	 * @param movieName
	 * @return List of actors that are (by google) part of this movie
	 * @throws IOException
	 */
	private ArrayList<Actor> getActors(final String movieName) throws IOException {
		ArrayList<Actor> temp = new ArrayList<Actor>();
		String name = movieName.replace(' ', '+');
		String search = "https://www.google.com/search?hl=en&q=" + name + "+actors";
		Document doc = Jsoup.connect(search).get();
		System.out.println(doc.title());
		Elements el = doc.getElementsByClass("EDblX DAVP1");
		if (el.size() != 0) {
			for (Element ele : el.get(0).getElementsByClass("uais2d")) {
				Elements elements = ele.getElementsByClass("wfg6Pb");
				if (elements.size() == 0)
					break;
				if (elements.size() == 3) {
					temp.add(new Actor(elements.get(0).text(), elements.get(1).text()));
				} else if (elements.size() == 2) {
					try {
						temp.add(new Actor(elements.get(0).text().split(" ")[0], elements.get(0).text().split(" ")[1]));
					} catch (java.lang.ArrayIndexOutOfBoundsException e) {
						System.out.println("Cant load actor: " + elements.get(0).text());
					}

				}

			}
		}

		return temp;
	}

	/**
	 * Method that loads studio from web page generated after google search
	 * 
	 * @param movieName name of movie thats studio i search for
	 * @return new Studio with set name from the search
	 * @throws IOException
	 */
	private Studio getStudio(final String movieName) throws IOException {
		Studio temp = null;
		String name = movieName.replace(' ', '+');
		String search = "https://www.google.com/search?hl=en&q=" + name + "+production";
		Document doc = Jsoup.connect(search).get();
		System.out.println(doc.title());
		Elements el = doc.getElementsByClass("EDblX DAVP1");
		if (el.size() != 0) {
			Elements stud = el.get(0).getElementsByClass("h998We mlo-c");
			if (stud.size() == 0)
				return getStudio(movieName, doc);
			Elements std = stud.get(0).getElementsByClass("title");
			if (std.size() == 0)
				return getStudio(movieName, doc);
			temp = new Studio(std.get(0).text());
		}

		return temp;
	}

	private Studio getStudio(final String movieName, Document doc) {
		Elements el = doc.getElementsByClass("EDblX DAVP1");
		if (el.size() == 0)
			return getStudioAgain(movieName, doc);
		Elements ele = el.get(0).getElementsByClass("uais2d");
		if (ele.size() == 0)
			return getStudioAgain(movieName, doc);
		Elements elem = ele.get(0).getElementsByClass("wfg6Pb");
		if (elem.size() == 0)
			return getStudioAgain(movieName, doc);
		String s = "";
		for (Element e : elem) {
			s += e.text() + " ";
		}
		s = s.trim();
		return new Studio(s);
	}

	private Studio getStudioAgain(final String movieName, Document doc) {
		Elements el = doc.getElementsByClass("oo0Xme");
		if (el.size() == 0)
			return null;
		Elements ele = el.get(0).getElementsByClass("uais2d");
		if (ele.size() == 0)
			return null;
		Elements elem = ele.get(0).getElementsByClass("wfg6Pb");
		if (elem.size() == 0)
			return null;
		String s = "";
		for (Element e : elem) {
			s += e.text();
		}
		s = s.trim();

		return null;
	}

	/**
	 * Gets length of the movie in minutes from google search web page
	 * 
	 * @param movieName name of movie thats length i search for
	 * @return length of the movie in minutes or -1
	 * @throws IOException
	 */
	private int getLength(final String movieName) throws IOException {
		int length = -1;
		String name = movieName.replace(' ', '+');
		String search = "https://www.google.com/search?hl=en&q=" + name + "+movie";
		Document doc = Jsoup.connect(search).get();
		System.out.println(doc.title());
		Elements el = doc.getElementsByClass("wwUB2c kno-fb-ctx");
		if (el.size() == 0)
			return -1;
		String text = el.get(0).getAllElements().get(0).text();
		String cas = text.split("‧")[2];
		cas = cas.trim();
		String hod = cas.split(" ")[0];
		String min = cas.split(" ")[1];
		length = (Integer.parseInt(hod.substring(0, 1)) * 60) + Integer.parseInt(min.substring(0, min.indexOf("m")));
		return length;
	}

	/**
	 * Method first searches for google search url with all songs from the movie.
	 * Then the page is parsed and songs are loaded from the page. Then is every
	 * song googled again, just to get its author.
	 * 
	 * @param movieName name of movie thats songs i search for
	 * @return list of songs from the movie
	 * @throws IOException
	 */
	private ArrayList<Song> getSongsAndAutors(final String movieName) throws IOException {
		ArrayList<Song> temp = new ArrayList<Song>();
		String url = getSongsUrl(movieName);
		if (url == null)
			return null;
		Document doc = Jsoup.connect("https://www.google.com" + url)
				.userAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)").get();
		System.out.println(doc.title());
		Element el = doc.getElementById("Db7kif");
		if (el == null)
			return null;
		Elements ele = el.getElementsByClass("ED44Kd");
		if (ele.size() == 0)
			return null;
		boolean prvni = true;
		for (Element elem : ele) {
			String songName = "";
			if (prvni) {
				songName = elem.text();
				prvni = false;
			} else {
				songName = elem.text().substring(2);
			}

			Song s = new Song(songName);
			temp.add(s);
			getAuthorAndLink(s, movieName);

		}
		return temp;
	}

	/**
	 * Method to set author and link to song.
	 * 
	 * @param song    song to get link and author to
	 * @param movName name of movie this song is part of
	 * @return true if link and author are both found, otherwise false, but still,
	 *         if link is found, it is set
	 * @throws IOException
	 */
	private boolean getAuthorAndLink(Song song, final String movName) throws IOException {
		String name = song.getName().replace(" ", "+");
		if (name.contains("["))
			name = name.substring(0, name.indexOf("["));
		if (name.contains("("))
			name = name.substring(0, name.indexOf("("));
		name = name.trim();
		String movieName = movName.replace(" ", "+");
		String search = "https://www.google.com/search?hl=en&q=" + name + " " + movieName + "+song";
		Document doc = Jsoup.connect(search).get();
		System.out.println("Song: " + doc.title());
		Elements el = doc.getElementsByClass("QBl4oe");
		if (el.size() == 0)
			return false;
		Elements ele = el.get(0).getElementsByClass("iUh30");
		if (ele.size() == 0)
			return false;
		song.setLink(ele.get(0).text());
		Elements elem = doc.getElementsByClass("SALvLe farUxc mJ2Mod");
		if (ele.size() == 0)
			return false;
		Elements eleme = elem.get(0).getElementsByClass("fl");
		if (eleme.size() <= 1)
			return false;
		if (eleme.get(1).text().split(" ").length == 2) {
			song.setAuthor(new Author(eleme.get(1).text().split(" ")[0], eleme.get(1).text().split(" ")[1]));
		} else {
			song.setAuthor(new Author(eleme.get(1).text(), ""));
		}

		return true;
	}

	/**
	 * Method that searches for google url of all songs fro the movie
	 * 
	 * @param movieName name of movie thats songs i am searching for
	 * @return google url with the songs search
	 * @throws IOException
	 */
	private String getSongsUrl(final String movieName) throws IOException {
		String name = movieName.replace(' ', '+');
		String search = "https://www.google.com/search?hl=en&q=" + name + "+songs";
		Document doc = Jsoup.connect(search).get();
		System.out.println(doc.title());
		Elements el = doc.getElementsByClass("ErlEM");
		if (el.size() == 0)
			return null;
		Elements ele = el.get(0).getElementsByClass("P7Vl4c");
		if (ele.size() == 0)
			return null;
		return ele.get(0).attr("href");
	}

	/**
	 * Method searches for genres using google search and then parses the web page
	 * 
	 * @param movieName name of movie thats genres i am searching for
	 * @return list of genres of the movie
	 * @throws IOException
	 */
	private ArrayList<Genre> getMovieGenres(final String movieName) throws IOException {
		ArrayList<Genre> temp = new ArrayList<Genre>();
		String name = movieName.replace(' ', '+');
		String search = "https://www.google.com/search?hl=en&q=" + name + "+genres";
		Document doc = Jsoup.connect(search).get();
		System.out.println(doc.title());
		Elements el = doc.getElementsByClass("gic rl_center");
		if (el.size() == 0)
			return (ArrayList<Genre>) getMovieGenres(movieName, doc);
		Elements ele = el.get(0).getElementsByClass("rlc__slider-page");
		if (ele.size() == 0)
			return (ArrayList<Genre>) getMovieGenres(movieName, doc);
		for (Element elem : ele) {
			Elements eleme = elem.getElementsByClass("title");
			if (eleme.size() == 0)
				continue;
			String text = eleme.get(0).text();
			if (text.contains("film")) {
				text = text.substring(0, text.indexOf("film"));
			}
			if (text.contains("Film")) {
				text = text.substring(0, text.indexOf("Film"));
			}
			text = text.trim();
			temp.add(new Genre(text));
		}
		return temp;
	}

	private List<Genre> getMovieGenres(final String movieName, Document doc) {
		List<Genre> temp = new ArrayList<Genre>();
		Elements el = doc.getElementsByClass("NFQFxe viOShc LKPcQc EfDVh mod");
		if (el.size() == 0)
			return null;
		Elements ele = el.get(0).getElementsByClass("Z0LcW");
		if (ele.size() == 0)
			return null;
		String[] genres = ele.get(0).text().split("/");
		for (String s : genres) {
			temp.add(new Genre(s));
		}
		return temp;
	}
}
