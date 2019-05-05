package com.krupizde.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.krupizde.entities.Dabing;
import com.krupizde.entities.Language;
import com.krupizde.persistence.interfaces.IDabingDao;
import com.krupizde.persistence.interfaces.ILanguageDao;

public class DabingDao implements IDabingDao {

	private DabingDao() {

	}

	private static DabingDao instance = null;

	public static DabingDao getDao() {
		if (instance == null)
			instance = new DabingDao();
		return instance;
	}

	@Override
	public int addDabing(Dabing d) throws ClassNotFoundException, SQLException {
		PreparedStatement stm = Database.getConn()
				.prepareStatement("insert into dabing(id_jazyk, link, id_film)values(?,?,?)");
		ILanguageDao lanDao = LanguageDao.getDao();
		stm.setInt(1, lanDao.addLanguage(d.getLanguage()));
		stm.setString(2, d.getLink());
		stm.setInt(3, d.getIdFilm());
		stm.executeUpdate();
		try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			} else {
				return -1;
			}
		}
	}

}
