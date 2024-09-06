package com.uade.tpo.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    //Optional<Order> findByUserId(Long userId);    VEREMOS SI HACE FALTA
    //Optional<Order> findByMovieId(Long movieId);  VEREMOS SI HACE FALTA
    Optional<Order> findByDate(String date);
}
