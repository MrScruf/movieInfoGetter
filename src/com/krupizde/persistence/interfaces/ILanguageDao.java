package com.krupizde.persistence.interfaces;

import java.sql.SQLException;

import com.krupizde.entities.Language;

public interface ILanguageDao {

	int addLanguage(Language l)throws ClassNotFoundException, SQLException ;
	int getLanguageId(Language l)throws ClassNotFoundException, SQLException ;
	int getLanguageId(String name)throws ClassNotFoundException, SQLException ;
}
