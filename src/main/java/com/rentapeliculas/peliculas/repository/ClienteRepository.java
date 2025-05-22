/* es el que se encarga de interactuar directamente con la base de datos para las entidades, en este caso cliente */

package com.rentapeliculas.peliculas.repository;


import com.rentapeliculas.peliculas.model.Cliente;
import com.rentapeliculas.peliculas.model.Pelicula;
import com.rentapeliculas.peliculas.model.Renta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCorreo(String correo);
    
    @Query("SELECT c FROM Cliente c WHERE c.nombre LIKE %?1%")
    List<Cliente> buscarPorNombre(String nombre);
}