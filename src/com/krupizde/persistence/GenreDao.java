package com.krupizde.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.krupizde.entities.Genre;
import com.krupizde.persistence.interfaces.IGenreDao;

public class GenreDao implements IGenreDao {

	private GenreDao() {
		// TODO Auto-generated constructor stub
	}

	private static GenreDao instance = null;

	public static GenreDao getDao() {
		if (instance == null)
			instance = new GenreDao();
		return instance;
	}

	@Override
	public int addGenre(Genre g) throws ClassNotFoundException, SQLException {
		int id = getGenreId(g);
		if (id != -1)
			return id;
		PreparedStatement stm = Database.getConn().prepareStatement("insert into zanr(nazev)values(?)",
				new String[] { "id_zanr" });
		stm.setString(1, g.getName().toLowerCase());
		stm.executeUpdate();
		try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				int ret = (int) generatedKeys.getLong(1);
				stm.close();
				return ret;
			} else {
				stm.close();
				return -1;
			}
		}
	}

	@Override
	public int getGenreId(Genre g) throws ClassNotFoundException, SQLException {
		return getGenreId(g.getName().toLowerCase());
	}

	@Override
	public int getGenreId(String name) throws ClassNotFoundException, SQLException {
		PreparedStatement stm = Database.getConn().prepareStatement("select id_zanr from zanr where nazev = ?");
		stm.setString(1, name.toLowerCase());
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
