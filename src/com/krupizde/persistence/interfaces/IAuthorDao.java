package com.krupizde.persistence.interfaces;

import java.sql.SQLException;

import com.krupizde.entities.Author;

public interface IAuthorDao {

	int addAuthor(Author a)throws ClassNotFoundException, SQLException ;
	int getAuthorId(Author a)throws ClassNotFoundException, SQLException ;
	int getAuthorId(String name, String surname)throws ClassNotFoundException, SQLException ;
	int getAuthorId(String name)throws ClassNotFoundException, SQLException ;
}
