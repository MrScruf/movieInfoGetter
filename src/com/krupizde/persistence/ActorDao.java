package com.krupizde.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.krupizde.entities.Actor;
import com.krupizde.persistence.interfaces.IActorDao;

public class ActorDao implements IActorDao {

	private ActorDao() {
		// TODO Auto-generated constructor stub
	}

	private static ActorDao instance = null;

	public static ActorDao getDao() {
		if (instance == null)
			instance = new ActorDao();
		return instance;
	}

	@Override
	public int addActor(Actor a) throws ClassNotFoundException, SQLException {
		int id = getActorId(a);
		if (id != -1)
			return id;
		PreparedStatement stm = Database.getConn().prepareStatement("insert into herec(jmeno,prijmeni)values(?,?)",new String [] {"id_herec"});
		stm.setString(1, a.getJmeno());
		stm.setString(2, a.getPrijmeni());
		stm.executeUpdate();
		try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				int ret = (int)generatedKeys.getLong(1);
				stm.close();
				return ret;
			} else {
				stm.close();
				return -1;
			}
		}
	}

	@Override
	public int getActorId(Actor a) throws ClassNotFoundException, SQLException {
		return getActorId(a.getJmeno(), a.getPrijmeni());
	}

	@Override
	public int getActorId(String name, String surname) throws ClassNotFoundException, SQLException {
		PreparedStatement stm = Database.getConn()
				.prepareStatement("select id_herec from herec where jmeno = ? and prijmeni = ?");
		stm.setString(1, name);
		stm.setString(2, surname);
		ResultSet set = stm.executeQuery();
		if (set.next()) {
			int ret = set.getInt(1);
			stm.close();
			return ret;
		}
		stm.close();
		return -1;
	}

}
