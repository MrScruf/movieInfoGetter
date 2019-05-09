package com.krupizde.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.krupizde.entities.Actor;
import com.krupizde.entities.Dabing;
import com.krupizde.entities.Genre;
import com.krupizde.entities.Movie;
import com.krupizde.entities.Subtitles;
import com.krupizde.entity.generators.DabingEntityGenerator;
import com.krupizde.entity.generators.SubtitlesEntityGenerator;
import com.krupizde.main.BasicData;
import com.krupizde.persistence.interfaces.IActorDao;
import com.krupizde.persistence.interfaces.IGenreDao;
import com.krupizde.persistence.interfaces.IMovieDao;
import com.krupizde.persistence.interfaces.ISongDao;
import com.krupizde.persistence.interfaces.IStudioDao;

public class MovieDao implements IMovieDao {

	private MovieDao() {
		// TODO Auto-generated constructor stub
	}

	private static MovieDao instance = null;

	public static MovieDao getDao() {
		if (instance == null)
			instance = new MovieDao();
		return instance;
	}

	@Override
	public int saveMovie(Movie m) throws ClassNotFoundException, SQLException {
		int id = getMovieId(m);
		if (id != -1)
			return id;
		IStudioDao stDao = StudioDao.getDao();
		int idStudio = stDao.addStudio(m.getStudio());
		PreparedStatement stm = Database.getConn()
				.prepareStatement("insert into film(id_studio, nazev,link, delka_stopy)values(?,?,?,?)",new String [] {"id_film"});
		stm.setInt(1, idStudio);
		stm.setString(2, m.getName());
		stm.setString(3, m.getPath());
		stm.setInt(4, m.getMovieLength());
		stm.executeUpdate();
		try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				id = (int)generatedKeys.getLong(1);
			} else {
				stm.close();
				return -1;
			}
		}
		try {
			addGenresToMovie(m, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		stm.close();
		addDabingsToMovie(m, id);
		addSubtitlesToMovie(m, id);
		addSongsToMovie(m, id);
		addActorsToMovie(m, id);
		return id;
	}

	private void addGenresToMovie(Movie m, int id) throws ClassNotFoundException, SQLException {
		if(m.getGenres() == null || m.getGenres().isEmpty())return;
		PreparedStatement stm = Database.getConn()
				.prepareStatement("insert into film_zanr_vazba(id_zanr,id_film)values(?,?)");
		IGenreDao genDao = GenreDao.getDao();
		stm.setInt(2, id);
		for (Genre g : m.getGenres()) {
			stm.setInt(1, genDao.addGenre(g));
			stm.executeUpdate();
		}
		stm.close();
	}

	private void addDabingsToMovie(Movie m, int id) throws ClassNotFoundException, SQLException {
		Random rand = new Random();
		int num = rand.nextInt(8);
		DabingEntityGenerator gen = new DabingEntityGenerator();
		DabingDao dDao = DabingDao.getDao();
		Set<Integer> seen = new HashSet<Integer>();
		for (int i = 0; i < num; i++) {
			int lang = rand.nextInt(BasicData.langs.length);
			if(seen.contains(lang)) {
				i--;
				continue;
			}
			seen.add(lang);
			Dabing dab = gen.generate(m.getName(), BasicData.pathTemplateDab, BasicData.langs[lang], id);
			dDao.addDabing(dab);
		}
	}

	private void addSubtitlesToMovie(Movie m, int id) throws ClassNotFoundException, SQLException {
		Random rand = new Random();
		SubtitlesEntityGenerator gen = new SubtitlesEntityGenerator();
		SubtitlesDao sDao = SubtitlesDao.getDao();
		Set<Integer> seen = new HashSet<Integer>();
		int num = rand.nextInt(8);
		for (int i = 0; i < num; i++) {
			int lang = rand.nextInt(BasicData.langs.length);
			if(seen.contains(lang)) {
				i--;
				continue;
			}
			seen.add(lang);
			Subtitles sub = gen.generate(m.getName(), BasicData.langs[lang], BasicData.pathTemplateSubs, id);
			sDao.addSubtitles(sub);
		}
	}

	private void addSongsToMovie(Movie m, int id) throws ClassNotFoundException, SQLException {
		if(m.getSongs() == null || m.getSongs().isEmpty())return;
		PreparedStatement stm = Database.getConn()
				.prepareStatement("insert into vazba_pisen_film(id_film, id_pisnicka, cas_zacatek)values(?,?,?)");
		ISongDao songDao = SongDao.getDao();
		stm.setInt(1, id);
		for (int i = 0; i < m.getSongs().size(); i++) {
			stm.setInt(2, songDao.addSong(m.getSongs().get(i)));
			stm.setInt(3, (m.getMovieLength() / m.getSongs().size()) * i);
			stm.executeUpdate();
		}
		stm.close();
	}

	private void addActorsToMovie(Movie m, int id) throws ClassNotFoundException, SQLException {
		if(m.getActors() == null || m.getActors().isEmpty())return;
		PreparedStatement stm = Database.getConn()
				.prepareStatement("insert into film_herec_vazba(id_film,id_herec)values(?,?)");
		IActorDao actDao = ActorDao.getDao();
		stm.setInt(1, id);
		for (Actor a : m.getActors()) {
			int cis = actDao.addActor(a);
			stm.setInt(2, cis);
			stm.executeUpdate();
		}
		stm.close();
	}

	@Override
	public int getMovieId(Movie m) throws ClassNotFoundException, SQLException {
		return getMovieId(m.getName());
	}

	@Override
	public int getMovieId(String name) throws ClassNotFoundException, SQLException {
		PreparedStatement stm = Database.getConn().prepareStatement("select id_film from film where nazev = ?");
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
