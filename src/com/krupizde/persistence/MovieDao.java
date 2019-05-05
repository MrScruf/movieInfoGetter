package com.krupizde.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.krupizde.entities.Movie;
import com.krupizde.persistence.interfaces.IMovieDao;
import com.krupizde.persistence.interfaces.IStudioDao;

public class MovieDao implements IMovieDao {

	private MovieDao() {
		// TODO Auto-generated constructor stub
	}

	private static MovieDao instance = null;

	public static MovieDao getDao() {
		if (instance == null)
			instance = new MovieDao();
		return instance;
	}

	@Override
	public int saveMovie(Movie m) throws ClassNotFoundException, SQLException {
		int id = getMovieId(m);
		if (id != -1)
			return id;
		IStudioDao stDao = StudioDao.getDao();
		int idStudio = stDao.addStudio(m.getStudio());
		PreparedStatement stm = Database.getConn()
				.prepareStatement("insert into film(id_studio, nazev, delka_stopy)values(?,?,?)");
		stm.setInt(1, idStudio);
		stm.setString(2, m.getName());
		stm.setInt(3, m.getMovieLength());
		stm.executeUpdate();
		try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				id = generatedKeys.getInt(1);
			} else {
				return -1;
			}
		}
		Random rand = new Random();
		return id;
	}

	@Override
	public int getMovieId(Movie m) throws ClassNotFoundException, SQLException {
		return getMovieId(m.getName());
	}

	@Override
	public int getMovieId(String name) throws ClassNotFoundException, SQLException {
		PreparedStatement stm = Database.getConn().prepareStatement("select id_film from film where nazev = ?");
		stm.setString(1, name);
		ResultSet set = stm.executeQuery();
		if (set.next()) {
			return set.getInt(1);
		}
		return -1;
	}

}
