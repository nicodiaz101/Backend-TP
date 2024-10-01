package com.uade.tpo.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.demo.controllers.movies.MovieRequest;
import com.uade.tpo.demo.entity.Director;
import com.uade.tpo.demo.entity.Genre;
import com.uade.tpo.demo.entity.Movie;
import com.uade.tpo.demo.exceptions.ResourceNotFoundException;
import com.uade.tpo.demo.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreService genreService;
    
    @Autowired
    private DirectorService directorService;
    

    @Transactional(rollbackFor = Throwable.class)
    public Movie createMovie(MovieRequest movieRequest) {
        Movie movie = new Movie();
        movie.setTitle(movieRequest.getTitle());
        movie.setReleaseDate(movieRequest.getReleaseDate());
        movie.setImdbScore(movieRequest.getImdbScore());
        movie.setPrice(movieRequest.getPrice());
        movie.setDiscountPercentage(movieRequest.getDiscountPercentage());
        movie.setStock(movieRequest.getStock());

        // Buscar y asignar el género
        Genre genre = genreService.getGenreById(movieRequest.getGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));
        movie.setGenre(genre);

        // Buscar y asignar el director
        Director director = directorService.getDirectorById(movieRequest.getDirectorId())
                .orElseThrow(() -> new ResourceNotFoundException("Director not found"));
        movie.setDirector(director);
        
        // Guardar la película
        Movie savedMovie = movieRepository.save(movie);

        // Agregar la película a la lista de películas del género y del director
        genre.getMovies().add(savedMovie);
        genreService.modifyGenre(genre.getGenreId(), genre);

        director.getMovies().add(savedMovie);
        directorService.modifyDirector(director.getDirectorId(), director);

        return savedMovie;
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
    
    public Page<Movie> getAvailableMovies(PageRequest pageable) {
        return movieRepository.findByStockGreaterThan(0, pageable);
    }

    public double calculateFinalPrice(Movie movie) {
        return movie.getPrice() - (movie.getPrice() * movie.getDiscountPercentage() / 100);
    }
}