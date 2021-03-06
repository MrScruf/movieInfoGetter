package com.krupizde.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.krupizde.entities.Studio;
import com.krupizde.persistence.interfaces.IStudioDao;

public class StudioDao implements IStudioDao {

	private StudioDao() {
		
	}
	
	private static StudioDao instance = null;

	public static StudioDao getDao() {
		if (instance == null)
			instance = new StudioDao();
		return instance;
	}

	@Override
	public int addStudio(Studio s) throws ClassNotFoundException, SQLException {
		int id = getStudioId(s);
		System.out.println("Já jsem ID ---->>>>>>" + id);
		if (id != -1)
			return id;
		PreparedStatement stm = Database.getConn().prepareStatement("insert into studio(nazev)values(?)",new String [] {"id_studio"});
		stm.setString(1, s.getName());
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
	public int getStudioId(Studio s) throws ClassNotFoundException, SQLException {
		return getStudioId(s.getName());
	}

	@Override
	public int getStudioId(String name) throws ClassNotFoundException, SQLException {
		PreparedStatement stm = Database.getConn().prepareStatement("select id_studio from studio where nazev = ?");
		stm.setString(1, name);
		ResultSet set = stm.executeQuery();
		if(set.next()) {
			int ret = set.getInt(1);
			stm.close();
			return ret;
		}
		stm.close();
		return -1;
	}

}
