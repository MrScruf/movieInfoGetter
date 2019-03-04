package com.krupizde.main;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class InfoGetter {

	/**
	 * Method that loads google search and then filters for elements by class I found using page eplorer in google chrome
	 * before. Returns nothing, just updates movie that was send as parameter.
	 * @param m movie, which will be updated with new information
	 * @throws IOException
	 */
	public void getMovieInfo(Movie m) throws IOException {
		String name = m.getName().replace(' ', '+');
		String search = "https://www.google.com/search?hl=en&q=" + name + "+actors";

		Document doc = Jsoup.connect(search).get();
		System.out.println(doc.title());
		Elements el = doc.getElementsByClass("EDblX DAVP1");
		for (Element ele : el.get(0).getElementsByClass("uais2d")) {
			Elements elements = ele.getElementsByClass("wfg6Pb");
			if(elements.size() == 0)return;
			if (elements.size() == 3) {
				System.out.print(elements.get(0).text()+" ");
				System.out.println(elements.get(1).text());
			}else {
				System.out.println(elements.get(0).text());
			}

		}
	}
}
