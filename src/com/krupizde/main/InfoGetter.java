package com.krupizde.main;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class InfoGetter {

	/**
	 * Returns nothing, just updates movie that was send as parameter.
	 * 
	 * @param m movie, which will be updated with new information
	 * @throws IOException
	 */
	public boolean getMovieInfo(Movie m, Configurator conf) throws IOException {
		/*System.out.println(getActors(m.getName()).toString());
		System.out.println(getStudio(m.getName()).toString());
		System.out.println(getLength(m.getName()));*/
		getSongsAndAutors(m.getName());
		return true;
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
				} else {
					temp.add(new Actor(elements.get(0).text().split(" ")[0], elements.get(0).text().split(" ")[1]));
				}

			}
		}

		return temp;
	}

	private Studio getStudio(final String movieName) throws IOException {
		Studio temp = null;
		String name = movieName.replace(' ', '+');
		String search = "https://www.google.com/search?hl=en&q=" + name + "+production";
		Document doc = Jsoup.connect(search).get();
		System.out.println(doc.title());
		Elements el = doc.getElementsByClass("EDblX DAVP1");
		if (el.size() != 0) {
			Elements stud = el.get(0).getElementsByClass("h998We mlo-c");
			if(stud.size() == 0)return null;
			Elements std = stud.get(0).getElementsByClass("title");
			if(std.size() == 0)return null;
			temp = new Studio(std.get(0).text());
		}

		return temp;
	}
	
	private int getLength(String movieName) throws IOException {
		int length = -1;
		String name = movieName.replace(' ', '+');
		String search = "https://www.google.com/search?hl=en&q=" + name + "+movie";
		Document doc = Jsoup.connect(search).get();
		System.out.println(doc.title());
		Elements el = doc.getElementsByClass("wwUB2c kno-fb-ctx");
		if(el.size() == 0)return -1;
		String text = el.get(0).getAllElements().get(0).text();
		String cas = text.split("‧")[2];
		cas = cas.trim();
		String hod = cas.split(" ")[0];
		String min = cas.split(" ")[1];
		length = (Integer.parseInt(hod.substring(0,1))*60)+Integer.parseInt(min.substring(0,min.indexOf("m")));
		return length;
	}
	
	private ArrayList<Song> getSongsAndAutors(String movieName) throws IOException {
		ArrayList<Song> temp = new ArrayList<Song>();
		String url = getSongsUrl(movieName);
		if(url==null)return null;
		Document doc = Jsoup.connect("https://www.google.com"+url).userAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)").get();
		System.out.println(doc.title());
		Element el = doc.getElementById("Db7kif");
		if(el == null)return null;
		Elements ele = el.getElementsByClass("ED44Kd");
		if(ele.size() == 0)return null;
		boolean prvni = true;
		for(Element elem : ele) {
			String songName = "";
			if(prvni) {
				songName  = elem.text();
				prvni = false;
			}else {
				songName = elem.text().substring(2);
			}
			
			Song s = new Song(songName);
			temp.add(s);
			getAuthorAndLink(s,movieName);
			if(s.getAuthor() == null) {
				System.out.println(s);
			}
			
		}
		return temp;
	}
	
	private boolean getAuthorAndLink(Song song, String movName) throws IOException {
		String name = song.getName().replace(" ", "+");
		if(name.contains("["))
			name = name.substring(0,name.indexOf("["));
		if(name.contains("("))
			name = name.substring(0,name.indexOf("("));
		name = name.trim();
		String movieName = movName.replace(" ", "+");
		String search = "https://www.google.com/search?hl=en&q=" + name +" " + movieName+ "+song";
		Document doc = Jsoup.connect(search).get();
		System.out.println(doc.title());
		Elements el = doc.getElementsByClass("QBl4oe");
		if(el.size() == 0)return false;
		Elements ele = el.get(0).getElementsByClass("iUh30");
		if(ele.size() == 0)return false;
		Elements elem = doc.getElementsByClass("SALvLe farUxc mJ2Mod");
		if(ele.size() == 0)return false;
		Elements eleme = elem.get(0).getElementsByClass("fl");
		if(eleme.size() <= 1)return false;
		if(eleme.get(1).text().split(" ").length == 2) {
			song.setAuthor(new Author(eleme.get(1).text().split(" ")[0],eleme.get(1).text().split(" ")[1]));
		}else {
			song.setAuthor(new Author(eleme.get(1).text(),""));
		}
		
		song.setLink(ele.get(0).text());
		return true;
	}
	
	private String getSongsUrl(String movieName) throws IOException {
		String name = movieName.replace(' ', '+');
		String search = "https://www.google.com/search?hl=en&q=" + name + "+songs";
		Document doc = Jsoup.connect(search).get();
		System.out.println(doc.title());
		Elements el = doc.getElementsByClass("ErlEM");
		if(el.size()== 0)return null;
		Elements ele = el.get(0).getElementsByClass("P7Vl4c");
		if(ele.size()==0)return null;
		return ele.get(0).attr("href");
	}
}
