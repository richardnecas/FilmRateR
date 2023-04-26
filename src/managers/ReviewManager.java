package managers;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewManager {

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
    private static final String insertQuery = "INSERT INTO ";
    private static final String editQuery = "UPDATE ";
}
