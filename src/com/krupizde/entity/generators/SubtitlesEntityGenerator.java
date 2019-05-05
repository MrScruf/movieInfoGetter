/**
 * 
 */
package com.krupizde.entity.generators;

import com.krupizde.entities.Language;
import com.krupizde.entities.Movie;
import com.krupizde.entities.Subtitles;

/**
 * @author Krupicka
 *
 */
public class SubtitlesEntityGenerator {

	public void setSubtitles(Movie m) {
		
	}
	
	public Subtitles generate(String movieName, Language l, String linkTemplate, int idFilm) {
		String tempName = movieName.replace(" ", "_");
		Subtitles temp = new Subtitles();
		temp.setIdFilm(idFilm);
		temp.setLanguage(l);
		temp.setLink(linkTemplate+"/"+tempName+"."+l.getShor()+"."+"wav");
		return temp;
	}
}
