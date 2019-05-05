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
		PreparedStatement stm = Database.getConn().prepareStatement("insert into zanr(nazev)values(?)");
		stm.setString(1, g.getName().toLowerCase());
		stm.executeUpdate();
		try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			} else {
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
		stm.setString(1, name);
		ResultSet set = stm.executeQuery();
		if (set.next()) {
			return set.getInt(1);
		}
		return -1;
	}

}
