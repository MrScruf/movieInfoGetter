package com.krupizde.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.krupizde.entities.Subtitles;
import com.krupizde.persistence.interfaces.ILanguageDao;
import com.krupizde.persistence.interfaces.ISubtitlesDao;

public class SubtitlesDao implements ISubtitlesDao {

	private SubtitlesDao() {
		// TODO Auto-generated constructor stub
	}

	private static SubtitlesDao instance = null;

	public static SubtitlesDao getDao() {
		if (instance == null)
			instance = new SubtitlesDao();
		return instance;
	}

	@Override
	public int addSubtitles(Subtitles s) throws ClassNotFoundException, SQLException {
		PreparedStatement stm = Database.getConn().prepareStatement("insert into titulky(id_jazyk, link, id_film)values(?,?,?)",new String [] {"id_titulky"});
		ILanguageDao lanDao = LanguageDao.getDao();
		stm.setInt(1, lanDao.addLanguage(s.getLanguage()));
		stm.setString(2, s.getLink());
		stm.setInt(3, s.getIdFilm());
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

}
