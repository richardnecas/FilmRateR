package managers;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import model.DBBase;
import model.Director;

public class DirectorManager {
    private List<Director> directors = new ArrayList<>();

    public void loadFromDB(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectQuery);
        while (rs.next()) {
            Director director = new Director();
            director.setId(rs.getInt("id"));
            director.setName(rs.getString("name"));
            director.setSurname(rs.getString("surname"));
            directors.add(director);
        }
    }

    public Director getDirByID(int id){
        for (Director dat:directors){
            if (dat.getId() == id){
                return dat;
            }
        }
        return null;
    }

    public Director addDirector(String name, String surname){
        Director dir = directors.stream().filter(director -> director.getName().equals(name) && director.getSurname().equals(surname)).findFirst().orElse(null);
        if (dir == null){
            dir = new Director();
            dir.setName(name);
            dir.setSurname(surname);
            dir.setStatus(DBBase.BaseStatus.created);
            directors.add(dir);
        }
        return dir;
    }

    public void saveToDB(Connection conn) throws SQLException {
        for (Director dat : directors){
            if (dat.getStatus() == DBBase.BaseStatus.created){
                PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, dat.getName());
                stmt.setString(2, dat.getSurname());
                stmt.executeUpdate();
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dat.setId(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
        }
    }

    private static final String selectQuery = "SELECT * FROM directors";
    private static final String insertQuery = "INSERT INTO directors(name, surname) VALUES(?, ?)";
}
