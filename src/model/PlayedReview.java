package model;

public class PlayedReview extends Review {
    private final int MAX_POINTS = 5;
    @Override
    protected int getMaxPoints() {
        return MAX_POINTS;
    }
}