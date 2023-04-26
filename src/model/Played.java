package model;

public class Played extends Film <PlayedReview>{
    public Played(){};

    public Played(String name, Director director, int yearOfRelease) {
        super(name, director, yearOfRelease);
    }

}