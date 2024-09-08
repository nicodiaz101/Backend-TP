package com.uade.tpo.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Método para buscar una película por su título
    Optional<Movie> findByTitle(String titleString);
}
