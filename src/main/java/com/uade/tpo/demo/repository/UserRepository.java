package com.uade.tpo.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    static Optional<User> findByEmail(String mail) {
        throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
    }
    Optional<User> findByUsername(String username);
    boolean existsById(Integer id); 
}

