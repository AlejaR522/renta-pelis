/* este archivo gestiona las peticiones relacionadas con clientes*/

package com.rentapeliculas.peliculas.controller;

import com.rentapeliculas.peliculas.model.Cliente;
import com.rentapeliculas.peliculas.service.ClienteService;
import com.rentapeliculas.peliculas.service.RentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final RentaService rentaService;

    @Autowired
    public ClienteController(ClienteService clienteService, RentaService rentaService) {
        this.clienteService = clienteService;
        this.rentaService = rentaService;
    }

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "cliente/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/form";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        clienteService.guardarCliente(cliente);
        redirectAttributes.addFlashAttribute("mensaje", "Cliente guardado con éxito");
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        if (cliente.isPresent()) {
            model.addAttribute("cliente", cliente.get());
            return "cliente/form";
        }
        return "redirect:/clientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        clienteService.eliminarCliente(id);
        redirectAttributes.addFlashAttribute("mensaje", "Cliente eliminado con éxito");
        return "redirect:/clientes";
    }

    @GetMapping("/buscar")
    public String buscarClientes(@RequestParam String termino, Model model) {
        model.addAttribute("clientes", clienteService.buscarPorNombre(termino));
        return "cliente/lista";
    }

    @GetMapping("/{id}/rentas")
    public String verHistorialRentas(@PathVariable Long id, Model model) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        if (cliente.isPresent()) {
            model.addAttribute("cliente", cliente.get());
            model.addAttribute("rentas", rentaService.listarPorClienteId(id));
            return "renta/historial";
        }
        return "redirect:/clientes";
    }
}