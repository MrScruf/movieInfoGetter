package com.krupizde.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.krupizde.entities.Language;
import com.krupizde.persistence.interfaces.ILanguageDao;

public class LanguageDao implements ILanguageDao {

	private LanguageDao() {

	}

	private static LanguageDao instance = null;

	public static LanguageDao getDao() {
		if (instance == null)
			instance = new LanguageDao();
		return instance;
	}

	public int addLanguage(Language l) throws ClassNotFoundException, SQLException {
		int id = getLanguageId(l);
		if (id != -1)
			return id;
		PreparedStatement stm = Database.getConn().prepareStatement("insert into jazyk(nazev,zkratka)values(?,?)",new String [] {"id_jazyk"});
		stm.setString(1, l.getName());
		stm.setString(2, l.getShor());
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

	public int getLanguageId(Language l) throws ClassNotFoundException, SQLException {
		return getLanguageId(l.getName());
	}

	public int getLanguageId(String name) throws ClassNotFoundException, SQLException {
		PreparedStatement stm = Database.getConn().prepareStatement("select id_jazyk from jazyk where nazev = ?");
		stm.setString(1, name);
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
