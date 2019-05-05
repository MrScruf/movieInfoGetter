/**
 * 
 */
package com.krupizde.main;

import java.sql.Connection;
import java.sql.SQLException;

import com.krupizde.persistence.Database;

/**
 * @author Krupicka
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Connection c = Database.getConn();
			System.out.println("Jdu");
			c.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//@SuppressWarnings("unused")
		//Form f = new Form();
	}

}
