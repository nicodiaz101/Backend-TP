package com.uade.tpo.demo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "directors")
public class Director{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long director_id;

    @Column(nullable = false)
    private String name;
    
    @OneToMany(mappedBy = "director")
    private List<Movie> movies;
}
