package com.uade.tpo.demo.controllers.movies;

import java.util.Date;

import lombok.Data;

@Data
public class MovieRequest {
    private String title;
    private Date releaseDate;
    private double imdbScore;
    private double price;
    private double discountPercentage;
    private int stock;
    private String poster;
    private String description;
    private String genre;
    private String director;
}
