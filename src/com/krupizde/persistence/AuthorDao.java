package com.krupizde.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.krupizde.entities.Author;
import com.krupizde.persistence.interfaces.IAuthorDao;

public class AuthorDao implements IAuthorDao {

	private AuthorDao() {
		// TODO Auto-generated constructor stub
	}

	private static AuthorDao instance = null;

	public static AuthorDao getDao() {
		if (instance == null)
			instance = new AuthorDao();
		return instance;
	}

	@Override
	public int addAuthor(Author a) throws ClassNotFoundException, SQLException {
		int id = getAuthorId(a);
		if (id != -1)
			return id;
		PreparedStatement stm = Database.getConn().prepareStatement("insert into autor(jmeno, prijmeni)values(?,?)",new String [] {"id_autor"});
		stm.setString(1, a.getFirstName());
		stm.setString(2, a.getLastName());
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
	public int getAuthorId(Author a) throws ClassNotFoundException, SQLException {
		return a.getLastName() == null || a.getLastName().isEmpty() ? getAuthorId(a.getFirstName())
				: getAuthorId(a.getFirstName(), a.getLastName());
	}

	@Override
	public int getAuthorId(String name, String surname) throws ClassNotFoundException, SQLException {
		PreparedStatement stm = Database.getConn()
				.prepareStatement("select id_autor from autor where jmeno = ? and prijmeni = ?");
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

	@Override
	public int getAuthorId(String name) throws ClassNotFoundException, SQLException {
		PreparedStatement stm = Database.getConn().prepareStatement("select id_autor from autor where jmeno = ?");
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
