package com.uade.tpo.demo.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Date releaseDate;

    @Column
    private double imdbScore;
    
    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double discountPercentage;

    @Column(nullable = false)
    private int stock;

    @ManyToOne 
    @JoinColumn(name = "genreId", referencedColumnName = "genreId", nullable = false)
    @JsonManagedReference
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "directorId", referencedColumnName = "directorId", nullable = false)
    @JsonManagedReference
    private Director director;
    
    @ManyToMany(mappedBy = "movies")
    private List<Order> orders;
}
