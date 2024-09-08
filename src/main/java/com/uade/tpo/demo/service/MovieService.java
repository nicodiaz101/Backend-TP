package com.uade.tpo.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Movie;
import com.uade.tpo.demo.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Object createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Optional<Movie> getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }
  
    public void modifyMovie(Movie movie) {
        movieRepository.save(movie);
    }
  
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public double calculateFinalPrice(Movie movie) {
        return movie.getPrice() - (movie.getPrice() * movie.getDiscountPercentage() / 100);
    }
}