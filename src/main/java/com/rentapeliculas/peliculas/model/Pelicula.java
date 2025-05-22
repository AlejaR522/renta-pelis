package com.rentapeliculas.peliculas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private Long idPelicula;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "genero")
    private String genero;

    @Column(name = "año")
    private Integer año;

    @Column(name = "disponible")
    private Boolean disponible = true;

    @Column(name = "precio_renta", precision = 10, scale = 2)
    private BigDecimal precioRenta;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL)
    private List<Renta> rentas = new ArrayList<>();

    // Constructores
    public Pelicula() {
    }

    public Pelicula(String titulo, String genero, Integer año, BigDecimal precioRenta) {
        this.titulo = titulo;
        this.genero = genero;
        this.año = año;
        this.precioRenta = precioRenta;
    }

    // Getters y Setters
    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public BigDecimal getPrecioRenta() {
        return precioRenta;
    }

    public void setPrecioRenta(BigDecimal precioRenta) {
        this.precioRenta = precioRenta;
    }

    public List<Renta> getRentas() {
        return rentas;
    }

    public void setRentas(List<Renta> rentas) {
        this.rentas = rentas;
    }
}