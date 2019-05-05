package com.krupizde.persistence.interfaces;

import java.sql.SQLException;

import com.krupizde.entities.Actor;

public interface IActorDao {

	int addActor(Actor a)throws ClassNotFoundException, SQLException ;
	int getActorId(Actor a)throws ClassNotFoundException, SQLException ;
	int getActorId(String name, String surname) throws ClassNotFoundException, SQLException;
}
