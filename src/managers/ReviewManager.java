package managers;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewManager {

    public void reviewToDB(Connection conn, List<Review> reviews) throws SQLException {
        for (Review dat : reviews){
            if (dat.getStatus() == DBBase.BaseStatus.created){
                PreparedStatement stmt = conn.prepareStatement(insertQuery);
                stmt.setInt(1, dat.getFilm_id());
                stmt.setString(2, dat.getReview());
                stmt.setInt(3, dat.getPoints());
                stmt.executeUpdate();
            }
        }
    }

    public List<Review> loadReviewsByFilmID(Connection conn, Film film) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement(selectQuery);
        stmt.setInt(1, film.getId());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Review review;
            if (film instanceof Played){
                review = new PlayedReview();
            } else {
                review = new AnimatedReview();
            }
            review.setId(rs.getInt("id"));
            review.setFilm_id(rs.getInt("film_id"));
            review.setReview(rs.getString("review"));
            try {
                review.setPoints(rs.getInt("points"));
            } catch(Exception e){
                System.out.println(e.getMessage());
            }

            reviews.add(review);
        }
        return reviews;
    }

    private static final String selectQuery = "SELECT * FROM reviews WHERE film_id = ?";
    private static final String insertQuery = "INSERT INTO reviews (film_id, review, points) VALUES (?,?,?)";
}
