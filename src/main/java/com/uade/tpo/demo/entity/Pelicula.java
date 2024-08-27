package com.uade.tpo.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pelicula;

    @Column
    private String titulo;
    @Column 
    private Date fecha_estreno;
    @Column
    private double puntaje_imdb;
    
    @ManyToOne
    @JoinColumn(name = "id_director", referencedColumnName = "id_director")
    private Directores director;
    
    @ManyToOne 
    @JoinColumn(name = "id_genero", referencedColumnName = "id_genero")
    private Generos generos;

}
