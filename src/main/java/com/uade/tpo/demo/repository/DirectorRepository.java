package com.uade.tpo.demo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    // Método para buscar un director por su nombre
    Optional<Director> findByName(String name);
}
