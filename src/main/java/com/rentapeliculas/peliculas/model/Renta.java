package com.rentapeliculas.peliculas.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rentas")
public class Renta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_renta")
    private Long idRenta;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;

    @Column(name = "fecha_renta", nullable = false)
    private LocalDate fechaRenta;

    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    @Column(name = "estado", nullable = false)
    private String estado; // "ACTIVA", "DEVUELTA", "VENCIDA"

    // Constructores
    public Renta() {
    }

    public Renta(Cliente cliente, Pelicula pelicula, LocalDate fechaRenta) {
        this.cliente = cliente;
        this.pelicula = pelicula;
        this.fechaRenta = fechaRenta;
        this.estado = "ACTIVA";
    }

    // Getters y Setters
    public Long getIdRenta() {
        return idRenta;
    }

    public void setIdRenta(Long idRenta) {
        this.idRenta = idRenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public LocalDate getFechaRenta() {
        return fechaRenta;
    }

    public void setFechaRenta(LocalDate fechaRenta) {
        this.fechaRenta = fechaRenta;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}