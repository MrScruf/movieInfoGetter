package com.krupizde.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database{

	private static Connection conn = null;
	
	public static Connection getConn() throws SQLException, ClassNotFoundException {
		if(conn == null) {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@oracle.fit.cvut.cz:1521:ORACLE","krupizde","grt57v4f");
		}
		return conn;
	}

}
