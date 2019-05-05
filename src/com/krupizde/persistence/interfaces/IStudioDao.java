package com.krupizde.persistence.interfaces;

import java.sql.SQLException;

import com.krupizde.entities.Studio;

public interface IStudioDao {

	int addStudio(Studio s)throws ClassNotFoundException, SQLException ;
	int getStudioId(Studio s)throws ClassNotFoundException, SQLException ;
	int getStudioId(String name)throws ClassNotFoundException, SQLException ;
}
