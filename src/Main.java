import managers.*;
import model.Film;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DirectorManager dirmng = new DirectorManager();
        FilmManager filmmng = new FilmManager();
        ActorManager actmng = new ActorManager();
        DBConnection.connect();
        try {
            dirmng.loadFromDB(DBConnection.getConn());
            actmng.loadFromDB(DBConnection.getConn());
            filmmng.loadFromDB(DBConnection.getConn(), dirmng, actmng);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBConnection.disconnectConn();
        }


        dirmng.addDirector("rgwea", "brqea");

        DBConnection.connect();
        try {
            dirmng.saveToDB(DBConnection.getConn());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBConnection.disconnectConn();
        }
    }
}