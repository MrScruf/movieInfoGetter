/**
 * 
 */
package com.krupizde.entity.generators;

import com.krupizde.entities.Dabing;
import com.krupizde.entities.Language;

/**
 * @author Krupicka
 *
 */
public class DabingEntityGenerator {

	
	public Dabing generate(String movieName, String linkTemplate, Language l, int idFilm) {
		String tempName = movieName.replace(" ", "_");
		Dabing temp = new Dabing();
		temp.setIdFilm(idFilm);
		temp.setLanguage(l);
		temp.setLink(linkTemplate+"/"+tempName+"."+l.getShor()+"."+"srt");
		return temp;
	}
}
