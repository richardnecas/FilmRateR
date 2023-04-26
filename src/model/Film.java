package model;

import java.util.List;

public abstract class Film <R extends Review> extends DBBase{
    private String name;
    private Director director;
    private int yearOfRelease;
    private List<R> reviews;
    private List<Actor> actors;

    public Film() {
    }

    public Film(String name, Director director, int yearOfRelease) {
        this.name = name;
        this.director = director;
        this.yearOfRelease = yearOfRelease;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public List<R> getReviews() {
        return reviews;
    }

    public void setReviews(List<R> reviews) {
        this.reviews = reviews;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Actor> getActors() {
        return actors;
    }
}