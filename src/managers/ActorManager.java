package managers;
import model.Actor;
import model.Artist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ActorManager {
    private List<Actor> actors = new ArrayList<>();
    private List<Cross> crossList = new ArrayList<>();

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public void loadFromDB(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectQuery);
        while (rs.next()) {
            Actor actor = new Actor();
            actor.setId(rs.getInt("id"));
            actor.setName(rs.getString("name"));
            actor.setSurname(rs.getString("surname"));
            actors.add(actor);
        }
        rs = stmt.executeQuery(selectCrossQuery);
        while (rs.next()) {
            Cross cross = new Cross();
            cross.setFilm_id(rs.getInt("film_id"));
            cross.setActor(getActorByID(rs.getInt("artists_id")));
            crossList.add(cross);
        }
    }

    public ArrayList<Actor> getActorsByFilmID(int id) {
        ArrayList<Actor> actualActors = new ArrayList<>();
        for (Cross dat : crossList){
            if (dat.getFilm_id() == id){
                actualActors.add(dat.getActor());
            }
        }
        return actualActors;
    }

    private Actor getActorByID(int id){
        for (Actor dat : actors){
            if (dat.getId() == id){
                return dat;
            }
        }
        return null;
    }

    private static class Cross{
        private int film_id;
        private Actor actor;

        public int getFilm_id() {
            return film_id;
        }

        public void setFilm_id(int film_id) {
            this.film_id = film_id;
        }

        public Actor getActor() {
            return actor;
        }

        public void setActor(Actor actor) {
            this.actor = actor;
        }
    }

    private final String selectQuery = "SELECT * FROM artists";
    private final String selectCrossQuery = "SELECT * FROM crossArtists";
    private final String insertQuery = "INSERT INTO artists(name, surname) VALUES(%s, %s)";
    private final String editQuery = "EDIT artists SET name = %s, surname = %s WHERE id = %d";
}
