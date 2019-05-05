package com.krupizde.persistence.interfaces;

import java.sql.SQLException;

import com.krupizde.entities.Genre;

public interface IGenreDao {

	int addGenre(Genre g)throws ClassNotFoundException, SQLException ;
	int getGenreId(Genre g)throws ClassNotFoundException, SQLException ;
	int getGenreId(String name)throws ClassNotFoundException, SQLException ;
}
