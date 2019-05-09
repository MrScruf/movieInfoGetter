package com.krupizde.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

	private static Connection conn = null;

	public static Connection getConn() throws SQLException, ClassNotFoundException {
		if (conn == null) {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Properties prop = new Properties();
			try {
				prop.load(new FileInputStream(new File("config.prop")));
			} catch (IOException e) {
				e.printStackTrace();
				return conn;
			}
			conn = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@oracle.fit.cvut.cz:1521:ORACLE",
					prop.getProperty("id"), prop.getProperty("password"));
		}
		return conn;
	}

}
