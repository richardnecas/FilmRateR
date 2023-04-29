import managers.*;
import model.Film;
import ui.UI;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {
        DirectorManager dirmng = new DirectorManager();
        FilmManager filmng = new FilmManager();
        ActorManager actmng = new ActorManager();
        DBConnection.connect();
        try {
            dirmng.loadFromDB(DBConnection.getConn());
            actmng.loadFromDB(DBConnection.getConn());
            filmng.loadFromDB(DBConnection.getConn(), dirmng, actmng);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBConnection.disconnectConn();
        }

        UI ui = new UI(filmng.getFilms());
        filmng.getFilms().add(ui.createFilm());

        DBConnection.connect();
        try {
            dirmng.saveToDB(DBConnection.getConn());
            filmng.saveToDB(DBConnection.getConn());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBConnection.disconnectConn();
        }
    }
}