package model;

public class AnimatedReview extends Review{
    private final int MAX_POINTS = 10;
    @Override
    protected int getMaxPoints() {
        return MAX_POINTS;
    }

}
