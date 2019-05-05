package com.krupizde.persistence.interfaces;

import java.sql.SQLException;

import com.krupizde.entities.Song;

public interface ISongDao {

	int addSong(Song s)throws ClassNotFoundException, SQLException ;
	int getSongId(Song s)throws ClassNotFoundException, SQLException ;
	int getSongId(String name)throws ClassNotFoundException, SQLException ;
}
