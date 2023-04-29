package managers;
import model.*;

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
            cross.setId(rs.getInt("id"));
            cross.setFilm_id(rs.getInt("film_id"));
            cross.setActor(getActorByID(rs.getInt("artists_id")));
            crossList.add(cross);
        }
    }

    public ArrayList<Actor> getActorsByFilmID(int id) {
        ArrayList<Actor> actualActors = new ArrayList<>();
        for (Cross dat : crossList){
            if (dat.getFilm_id() == id && dat.getStatus() != DBBase.BaseStatus.deleted){
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

    public Actor addActor(String name, String surname, Film film){
        Actor act = addOrCreateActor(name, surname, film);
        addCrossList(act, film.getId());
        return act;
    }

    private Actor addOrCreateActor(String name, String surname, Film film){
        Actor act = actors.stream().filter(director -> director.getName().equals(name) && director.getSurname().equals(surname)).findFirst().orElse(null);
        if (act == null){
            act = new Actor();
            act.setName(name);
            act.setSurname(surname);
            act.setStatus(DBBase.BaseStatus.created);
            actors.add(act);
        }
        return act;
    }

    public Actor editActor(Actor oldActor, Film film, String name, String surname){
        Cross cross = findActorComb(oldActor, film);
        Actor newActor = addActor(name, surname, film);
        if (cross != null){
            cross.setActor(newActor);
            cross.setStatus(DBBase.BaseStatus.edited);
        } else {
            addCrossList(newActor, film.getId());
        }
        return newActor;
    }

    private Cross findActorComb(Actor act, Film film){
        for(Cross dat : crossList){
            if(dat.getActor() == act && film.getId() == dat.getFilm_id()){
               return dat;
            }
        }
        return null;
    }

    public void deleteActor(Actor actor, Film film){
        Cross cross = findActorComb(actor, film);
        if (cross != null) {
            cross.setStatus(DBBase.BaseStatus.deleted);
        }
    }

    private void addCrossList(Actor actor, int id){
        Cross cross = new Cross();
        cross.setFilm_id(id);
        cross.setActor(actor);
        cross.setStatus(DBBase.BaseStatus.created);
        crossList.add(cross);
    }

    private static class Cross extends DBBase{
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
    private final String selectCrossQuery = "SELECT * FROM crossArtists WHERE isDeleted = 0";
    private final String insertQuery = "INSERT INTO artists(name, surname) VALUES(?, ?)";
    //private final String editQuery = "UPDATE artists SET name = ?, surname = ? WHERE id = ?";
    private static final String insertCrossQuery = "INSERT INTO crossArtists(film_id, artist_id) VALUES (?, ?)";
    private static final String editCrossQuery = "UPDATE crossArtists SET artist_id = ? WHERE id = ?";
    private static final String deleteCrossQuery = "UPDATE crossArtists SET isDeleted = 1 WHERE id = ?";
}
