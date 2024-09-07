package com.uade.tpo.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
    List<Order> findByUserUserId(@Param("userId") Long userId);

    @Query("SELECT o FROM Order o WHERE DATE(o.order_date) = :orderDate")
    List<Order> findByOrderDate(@Param("orderDate") Date orderDate);

    //Optional<Order> findByMovieId(Long movieId);  VEREMOS SI HACE FALTA
}
