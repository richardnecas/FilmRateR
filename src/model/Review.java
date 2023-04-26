package model;

import java.util.ArrayList;

public abstract class Review extends DBBase{
    private int film_id;
    protected int points;
    private String review;
    private final int MIN_POINTS = 1;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) throws Exception{
        if (points < 1 || points > getMaxPoints()){
            throw new Exception("Cislo je mimo");
        }
        this.points = points;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    protected abstract int getMaxPoints();

}
