package com.uade.tpo.demo.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.demo.repository.GenreRepository;
import com.uade.tpo.demo.entity.Genre;


@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Transactional(rollbackFor = Throwable.class)
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Page<Genre> getGenres(PageRequest pageable) {
        return genreRepository.findAll(pageable);
    }

    public Optional<Genre> getGenreByName(String name) {
        return genreRepository.findByName(name);
    }

    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    public Genre modifyGenre(Long id, Genre genreDetails) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        if (optionalGenre.isPresent()) {
            Genre genre = optionalGenre.get();
            genre.setName(genreDetails.getName());
            genre.setMovies(genreDetails.getMovies());
            return genreRepository.save(genre);
        } else { // Género no encontrado
            return null;
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public boolean deleteGenreByName(String name) {
        try {
            Optional<Genre> optionalGenre = genreRepository.findByName(name);
            if (optionalGenre.isPresent()) {
                Genre genre = optionalGenre.get();
                genreRepository.delete(genre);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting genre by name: " + e.getMessage(), e);
        }
    }

    public boolean deleteAllGenres() {
        genreRepository.deleteAll();
        return true;
    }

}
