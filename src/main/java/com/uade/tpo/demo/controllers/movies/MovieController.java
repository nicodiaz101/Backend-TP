package com.uade.tpo.demo.controllers.movies;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Movie;
import com.uade.tpo.demo.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<Object> createMovie(@RequestBody Movie movie) {
        try{
        Movie result = movieService.createMovie(movie);
        return ResponseEntity.created(URI.create("/movies/" + result.getMovieId())).body(result);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Movie>> getMovies(@RequestParam(required = false) Integer page,
    @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(movieService.getMovies(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(movieService.getMovies(PageRequest.of(page, size)));
    }

    @GetMapping("/available")
    public ResponseEntity<Page<Movie>> getAvailableMovies(@RequestParam(required = false) Integer page,
    @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(movieService.getMovies(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(movieService.getMovies(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Optional<Movie> movie = movieService.getMovieById(id);
        return movie.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/title")
    public ResponseEntity<Movie> getMovieByTitle(@RequestParam String title) {
        Optional<Movie> movie = movieService.getMovieByTitle(title);
        return movie.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> modifyMovie(@PathVariable Long id, @RequestBody Movie movieDetails) {
        try{
            Movie modifiedMovie = movieService.modifyMovie(id, movieDetails);
            if (modifiedMovie != null) {
                return ResponseEntity.ok().location(URI.create("/movies/" + modifiedMovie.getMovieId())).body(modifiedMovie);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }   
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }   
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMovieById(@PathVariable Long id) {
        try {
            boolean isDeleted = movieService.deleteMovieById(id);
            if (isDeleted) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllMovies() {
        try {
            movieService.deleteAllMovies();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
