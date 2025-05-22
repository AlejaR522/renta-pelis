package com.rentapeliculas.peliculas.controller;

import com.rentapeliculas.peliculas.model.Cliente;
import com.rentapeliculas.peliculas.model.Pelicula;
import com.rentapeliculas.peliculas.model.Renta;
import com.rentapeliculas.peliculas.service.ClienteService;
import com.rentapeliculas.peliculas.service.PeliculaService;
import com.rentapeliculas.peliculas.service.RentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/rentas")
public class RentaController {

    private final RentaService rentaService;
    private final ClienteService clienteService;
    private final PeliculaService peliculaService;

    @Autowired
    public RentaController(RentaService rentaService, ClienteService clienteService, PeliculaService peliculaService) {
        this.rentaService = rentaService;
        this.clienteService = clienteService;
        this.peliculaService = peliculaService;
    }

    @GetMapping
    public String listarRentas(Model model) {
        model.addAttribute("rentas", rentaService.listarTodas());
        return "renta/lista";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("renta", new Renta());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("peliculas", peliculaService.listarDisponibles());
        model.addAttribute("fechaActual", LocalDate.now());
        return "renta/form";
    }

    @PostMapping("/guardar")
    public String guardarRenta(@RequestParam("clienteId") Long clienteId,
                              @RequestParam("peliculaId") Long peliculaId,
                              @RequestParam("fechaRenta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaRenta,
                              RedirectAttributes redirectAttributes) {
        
        Optional<Cliente> cliente = clienteService.buscarPorId(clienteId);
        Optional<Pelicula> pelicula = peliculaService.buscarPorId(peliculaId);
        
        if (cliente.isPresent() && pelicula.isPresent()) {
            Renta renta = new Renta(cliente.get(), pelicula.get(), fechaRenta);
            rentaService.registrarRenta(renta);
            redirectAttributes.addFlashAttribute("mensaje", "Renta registrada con éxito");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la renta");
        }
        
        return "redirect:/rentas";
    }

    @GetMapping("/devolver/{id}")
    public String devolverPelicula(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Renta renta = rentaService.devolverPelicula(id);
        if (renta != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Película devuelta con éxito");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al devolver la película");
        }
        return "redirect:/rentas";
    }

    @GetMapping("/cliente/{id}")
    public String listarRentasPorCliente(@PathVariable Long id, Model model) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        if (cliente.isPresent()) {
            model.addAttribute("cliente", cliente.get());
            model.addAttribute("rentas", rentaService.listarPorClienteId(id));
            return "renta/historial";
        }
        return "redirect:/rentas";
    }

    @GetMapping("/pelicula/{id}")
    public String listarRentasPorPelicula(@PathVariable Long id, Model model) {
        Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);
        if (pelicula.isPresent()) {
            model.addAttribute("pelicula", pelicula.get());
            model.addAttribute("rentas", rentaService.listarPorPeliculaId(id));
            return "renta/historial";
        }
        return "redirect:/rentas";
    }

    @GetMapping("/historial")
    public String mostrarFormularioHistorial() {
        return "renta/buscar-historial";
    }

    @GetMapping("/historial/buscar")
    public String buscarHistorial(@RequestParam(required = false) Long clienteId,
                                 @RequestParam(required = false) Long peliculaId,
                                 Model model) {
        if (clienteId != null) {
            return "redirect:/rentas/cliente/" + clienteId;
        } else if (peliculaId != null) {
            return "redirect:/rentas/pelicula/" + peliculaId;
        }
        return "redirect:/rentas/historial";
    }
}