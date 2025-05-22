package com.rentapeliculas.peliculas.repository;

import com.rentapeliculas.peliculas.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    List<Pelicula> findByDisponible(Boolean disponible);
    
    List<Pelicula> findByGenero(String genero);
    
    @Query("SELECT p FROM Pelicula p WHERE p.titulo LIKE %?1%")
    List<Pelicula> buscarPorTitulo(String titulo);
}