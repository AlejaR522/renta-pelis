package com.rentapeliculas.peliculas.service;

import com.rentapeliculas.peliculas.model.Cliente;
import com.rentapeliculas.peliculas.model.Pelicula;
import com.rentapeliculas.peliculas.model.Renta;
import com.rentapeliculas.peliculas.repository.RentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentaService {

    private final RentaRepository rentaRepository;
    private final PeliculaService peliculaService;

    @Autowired
    public RentaService(RentaRepository rentaRepository, PeliculaService peliculaService) {
        this.rentaRepository = rentaRepository;
        this.peliculaService = peliculaService;
    }

    public List<Renta> listarTodas() {
        return rentaRepository.findAll();
    }

    public Optional<Renta> buscarPorId(Long id) {
        return rentaRepository.findById(id);
    }

    @Transactional
    public Renta registrarRenta(Renta renta) {
        // Actualizar disponibilidad de la película
        peliculaService.actualizarDisponibilidad(renta.getPelicula().getIdPelicula(), false);
        return rentaRepository.save(renta);
    }

    @Transactional
    public Renta devolverPelicula(Long rentaId) {
        Optional<Renta> rentaOpt = rentaRepository.findById(rentaId);
        if (rentaOpt.isPresent()) {
            Renta renta = rentaOpt.get();
            renta.setFechaDevolucion(LocalDate.now());
            renta.setEstado("DEVUELTA");
            
            // Actualizar disponibilidad de la película
            peliculaService.actualizarDisponibilidad(renta.getPelicula().getIdPelicula(), true);
            
            return rentaRepository.save(renta);
        }
        return null;
    }

    public List<Renta> listarPorCliente(Cliente cliente) {
        return rentaRepository.findByCliente(cliente);
    }

    public List<Renta> listarPorClienteId(Long clienteId) {
        return rentaRepository.findByClienteId(clienteId);
    }

    public List<Renta> listarPorPelicula(Pelicula pelicula) {
        return rentaRepository.findByPelicula(pelicula);
    }

    public List<Renta> listarPorPeliculaId(Long peliculaId) {
        return rentaRepository.findByPeliculaId(peliculaId);
    }

    public List<Renta> listarPorEstado(String estado) {
        return rentaRepository.findByEstado(estado);
    }

    public List<Renta> listarPorRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return rentaRepository.findByFechaRentaBetween(fechaInicio, fechaFin);
    }

    public void eliminarRenta(Long id) {
        rentaRepository.deleteById(id);
    }
}
