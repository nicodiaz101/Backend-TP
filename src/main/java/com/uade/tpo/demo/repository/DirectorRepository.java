package com.uade.tpo.demo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    // Método para buscar un director por su nombre
    Optional<Director> findByName(String name);
    // Método para verificar si existe un director por su nombre
    boolean existsByName(String name);
    // Método para eliminar un director por su nombre
    void deleteByName(String name);
}
