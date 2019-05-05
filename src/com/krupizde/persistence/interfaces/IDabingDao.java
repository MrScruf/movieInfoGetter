package com.krupizde.persistence.interfaces;

import java.sql.SQLException;

import com.krupizde.entities.Dabing;

public interface IDabingDao {

	int addDabing(Dabing d)throws ClassNotFoundException, SQLException ;
}
