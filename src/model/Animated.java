package model;

public class Animated extends Film <AnimatedReview> {
    private int recommendedAge;

    public Animated(){};
    public Animated(String name, Director director, int yearOfRelease) {
        super(name, director, yearOfRelease);
    }

    public int getRecommendedAge() {
        return recommendedAge;
    }

    public void setRecommendedAge(int recommendedAge) {
        if(recommendedAge > 0) {
            this.recommendedAge = recommendedAge;
        }
    }
}