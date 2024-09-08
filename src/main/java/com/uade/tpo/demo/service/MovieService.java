package com.uade.tpo.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.demo.entity.Movie;
import com.uade.tpo.demo.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Transactional(rollbackFor = Throwable.class)
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Page<Movie> getMovies(PageRequest pageable) {
        return movieRepository.findAll(pageable);
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Optional<Movie> getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public Movie modifyMovie(Long id, Movie movieDetails) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            movie.setTitle(movieDetails.getTitle());
            movie.setReleaseDate(movieDetails.getReleaseDate());
            movie.setImdbScore(movieDetails.getImdbScore());
            movie.setPrice(movieDetails.getPrice());
            movie.setDiscountPercentage(movieDetails.getDiscountPercentage());
            movie.setStock(movieDetails.getStock());
            movie.setGenre(movieDetails.getGenre());
            movie.setDirector(movieDetails.getDirector());
            return movieRepository.save(movie);
        } else { // Orden no encontrada
            return null;
        }
    }

    public boolean deleteMovieById(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteAllMovies() {
        movieRepository.deleteAll();
        return true;
    }

    public double calculateFinalPrice(Movie movie) {
        return movie.getPrice() - (movie.getPrice() * movie.getDiscountPercentage() / 100);
    }
}