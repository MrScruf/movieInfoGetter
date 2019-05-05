package com.krupizde.persistence.interfaces;

import java.sql.SQLException;

import com.krupizde.entities.Subtitles;

public interface ISubtitlesDao {

	int addSubtitles(Subtitles s)throws ClassNotFoundException, SQLException ;
}
