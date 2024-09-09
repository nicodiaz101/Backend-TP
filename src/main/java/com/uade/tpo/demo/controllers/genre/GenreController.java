package com.uade.tpo.demo.controllers.genre;

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

import com.uade.tpo.demo.entity.Genre;
import com.uade.tpo.demo.service.GenreService;

@RestController
@RequestMapping("/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @PostMapping
    public ResponseEntity<Object> createGenre(@RequestBody Genre genre) {
        try{
            Genre result = genreService.createGenre(genre);
            return ResponseEntity.created(URI.create("/genre/" + result.getGenreId())).body(result);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        Optional<Genre> genre = genreService.getGenreById(id);
        return genre.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Genre> getGenreByName(@PathVariable String name) {
        Optional<Genre> genre = genreService.getGenreByName(name);
        return genre.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Genre>> getGenres(@RequestParam(required = false) Integer page,
    @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(genreService.getGenres(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(genreService.getGenres(PageRequest.of(page, size)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> modifyGenre(@PathVariable Long id, @RequestBody Genre genreDetails) {
        try {
            Genre modifiedGenre = genreService.modifyGenre(id, genreDetails);
            if (modifiedGenre != null) {
                return ResponseEntity.ok().location(URI.create("/genre/" + modifiedGenre.getGenreId())).body(genreDetails);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>("Bad Request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGenreById(@PathVariable Long id) {
        try{
            boolean isDeleted = genreService.deleteGenreById(id);
            if (isDeleted) {
                return ResponseEntity.noContent().build();
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAllGenres() {
        try {
            genreService.deleteAllGenres();
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
