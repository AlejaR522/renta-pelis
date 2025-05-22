package com.rentapeliculas.peliculas.service;

import com.rentapeliculas.peliculas.model.Pelicula;
import com.rentapeliculas.peliculas.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;

    @Autowired
    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    public List<Pelicula> listarTodas() {
        return peliculaRepository.findAll();
    }

    public List<Pelicula> listarDisponibles() {
        return peliculaRepository.findByDisponible(true);
    }

    public Optional<Pelicula> buscarPorId(Long id) {
        return peliculaRepository.findById(id);
    }

    public Pelicula guardarPelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    public void eliminarPelicula(Long id) {
        peliculaRepository.deleteById(id);
    }

    public List<Pelicula> buscarPorGenero(String genero) {
        return peliculaRepository.findByGenero(genero);
    }

    public List<Pelicula> buscarPorTitulo(String titulo) {
        return peliculaRepository.buscarPorTitulo(titulo);
    }

    public void actualizarDisponibilidad(Long id, boolean disponible) {
        Optional<Pelicula> peliculaOpt = peliculaRepository.findById(id);
        if (peliculaOpt.isPresent()) {
            Pelicula pelicula = peliculaOpt.get();
            pelicula.setDisponible(disponible);
            peliculaRepository.save(pelicula);
        }
    }
}