package com.movies;

import java.io.Serializable;

class MovieObject implements Serializable {

    private String title, image, genre;
    private double rating;
    private int releaseYear;

    MovieObject(String title, String image, double rating, int releaseYear, String genre) {
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    String getTitle() {
        return title;
    }

    String getImage() {
        return image;
    }

    double getRating() {
        return rating;
    }

    int getReleaseYear() {
        return releaseYear;
    }

    String getGenre() {
        return genre;
    }
}
