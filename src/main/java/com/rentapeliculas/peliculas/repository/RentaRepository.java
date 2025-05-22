package com.rentapeliculas.peliculas.repository;



import com.rentapeliculas.peliculas.model.Cliente;
import com.rentapeliculas.peliculas.model.Pelicula;
import com.rentapeliculas.peliculas.model.Renta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentaRepository extends JpaRepository<Renta, Long> {
    List<Renta> findByCliente(Cliente cliente);
    
    List<Renta> findByPelicula(Pelicula pelicula);
    
    List<Renta> findByEstado(String estado);
    
    @Query("SELECT r FROM Renta r WHERE r.fechaRenta BETWEEN ?1 AND ?2")
    List<Renta> findByFechaRentaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    @Query("SELECT r FROM Renta r WHERE r.cliente.idCliente = ?1")
    List<Renta> findByClienteId(Long clienteId);
    
    @Query("SELECT r FROM Renta r WHERE r.pelicula.idPelicula = ?1")
    List<Renta> findByPeliculaId(Long peliculaId);
}