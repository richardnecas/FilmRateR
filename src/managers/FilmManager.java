package managers;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class FilmManager {
    ReviewManager revmng = new ReviewManager();
    private List<Film> films = new ArrayList<>();

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public void loadFromDB(Connection conn, DirectorManager dirmng, ActorManager actmng) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectQuery);
        while (rs.next()) {
            Film film;
            if (rs.getInt("isAnimated") == 1) {
                Animated filmA = new Animated();
                filmA.setRecommendedAge(rs.getInt("recomendedAge"));
                film = filmA;
            } else{
                film = new Played();
            }
            film.setId(rs.getInt("id"));
            film.setName(rs.getString("filmName"));
            film.setDirector(dirmng.getDirByID(rs.getInt("director_id")));
            film.setYearOfRelease(rs.getInt("yearOfRelease"));
            film.setActors(actmng.getActorsByFilmID(rs.getInt("id")));
            film.setReviews(revmng.loadReviewsByFilmID(conn,film));
            films.add(film);
        }
    }

    private static final String selectQuery = "SELECT * FROM films ORDER BY filmName ASC";
    private static final String insertQuery = "INSERT INTO films (filmName, director_id, isAnimated) VALUES (%s, %d, %d)";
    private static final String updateQuery = "UPDATE films SET filmName = %s, director_id = %d, isAnimated = %d, isActual = %d WHERE id = %d";
}
