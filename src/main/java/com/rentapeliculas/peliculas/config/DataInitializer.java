/* en este archivo se genera el usuario admin al inicializar el proyecto */

package com.rentapeliculas.peliculas.config;

import com.rentapeliculas.peliculas.model.Usuario;
import com.rentapeliculas.peliculas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Verificar si ya existe algún usuario
        if (usuarioRepository.count() == 0) {
            // Crear usuario administrador
            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");
            admin.setContraseña(passwordEncoder.encode("admin123"));
            admin.setRol("ROLE_ADMIN");
            
            usuarioRepository.save(admin);
            
            System.out.println("Usuario administrador creado con éxito.");
            System.out.println("Usuario: admin");
            System.out.println("Contraseña: admin123");
        }
    }
}