package com.krupizde.persistence.interfaces;

import java.sql.SQLException;

import com.krupizde.entities.Movie;

public interface IMovieDao {

	int saveMovie(Movie m)throws ClassNotFoundException, SQLException ;
	int getMovieId(Movie m)throws ClassNotFoundException, SQLException ;
	int getMovieId(String name)throws ClassNotFoundException, SQLException ;
}
