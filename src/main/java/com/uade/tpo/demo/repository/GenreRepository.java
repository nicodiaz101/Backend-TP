package com.uade.tpo.demo.repository;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.tpo.demo.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    // Método para buscar un género por su nombre
    Optional<Genre> findByName(String name);

}
