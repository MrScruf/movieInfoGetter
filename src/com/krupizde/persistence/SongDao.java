package com.krupizde.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.krupizde.entities.Song;
import com.krupizde.persistence.interfaces.IAuthorDao;
import com.krupizde.persistence.interfaces.ISongDao;

public class SongDao implements ISongDao {

	private SongDao() {

	}

	private static SongDao instance = null;

	public static SongDao getDao() {
		if (instance == null)
			instance = new SongDao();
		return instance;
	}

	@Override
	public int addSong(Song s) throws ClassNotFoundException, SQLException {
		int id = getSongId(s);
		if (id != -1)
			return id;
		IAuthorDao autDao = AuthorDao.getDao();
		PreparedStatement stm = Database.getConn()
				.prepareStatement("insert into pisnicka(link,nazev,id_autor)values(?,?,?)");
		stm.setString(1, s.getLink());
		stm.setString(2, s.getName());
		stm.setInt(3, autDao.addAuthor(s.getAuthor()));
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
	public int getSongId(Song s) throws ClassNotFoundException, SQLException {
		return getSongId(s.getName());
	}

	@Override
	public int getSongId(String name) throws ClassNotFoundException, SQLException {
		PreparedStatement stm = Database.getConn().prepareStatement("select id_pisnicka from pisnicka where nazev = ?");
		stm.setString(1, name);
		ResultSet set = stm.executeQuery();
		if (set.next()) {
			return set.getInt(1);
		}
		return -1;
	}

}
