package com.rentapeliculas.peliculas.controller;


import com.rentapeliculas.peliculas.model.Pelicula;
import com.rentapeliculas.peliculas.service.PdfService;
import com.rentapeliculas.peliculas.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@Controller
@RequestMapping("/peliculas")
public class PeliculaController {

    private final PeliculaService peliculaService;
    private final PdfService pdfService;

    @Autowired
    public PeliculaController(PeliculaService peliculaService, PdfService pdfService) {
        this.peliculaService = peliculaService;
        this.pdfService = pdfService;
    }

    @GetMapping
    public String listarPeliculas(Model model) {
        model.addAttribute("peliculas", peliculaService.listarTodas());
        return "pelicula/lista";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("pelicula", new Pelicula());
        return "pelicula/form";
    }

    @PostMapping("/guardar")
    public String guardarPelicula(@ModelAttribute Pelicula pelicula, RedirectAttributes redirectAttributes) {
        peliculaService.guardarPelicula(pelicula);
        redirectAttributes.addFlashAttribute("mensaje", "Película guardada con éxito");
        return "redirect:/peliculas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);
        if (pelicula.isPresent()) {
            model.addAttribute("pelicula", pelicula.get());
            return "pelicula/form";
        }
        return "redirect:/peliculas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPelicula(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        peliculaService.eliminarPelicula(id);
        redirectAttributes.addFlashAttribute("mensaje", "Película eliminada con éxito");
        return "redirect:/peliculas";
    }

    @GetMapping("/buscar")
    public String buscarPeliculas(@RequestParam String termino, Model model) {
        model.addAttribute("peliculas", peliculaService.buscarPorTitulo(termino));
        return "pelicula/lista";
    }

    @GetMapping("/genero/{genero}")
    public String listarPorGenero(@PathVariable String genero, Model model) {
        model.addAttribute("peliculas", peliculaService.buscarPorGenero(genero));
        model.addAttribute("generoActual", genero);
        return "pelicula/lista";
    }

    @GetMapping("/disponibles")
    public String listarDisponibles(Model model) {
        model.addAttribute("peliculas", peliculaService.listarDisponibles());
        model.addAttribute("soloDisponibles", true);
        return "pelicula/lista";
    }

    @GetMapping("/catalogo-pdf")
    public ResponseEntity<InputStreamResource> generarCatalogoPdf() {
        ByteArrayInputStream bis = pdfService.generarCatalogoPeliculas();
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=catalogo-peliculas.pdf");
        
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}