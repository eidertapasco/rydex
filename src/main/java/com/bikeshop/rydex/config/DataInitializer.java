package com.bikeshop.rydex.config;

import com.bikeshop.rydex.enums.RolUsuario;
import com.bikeshop.rydex.model.ClienteModel;
import com.bikeshop.rydex.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner initUsers() {
        return args -> {
            // Solo crea los usuarios si no existen (evita duplicados al reiniciar)
            if (!clienteRepository.existsByEmail("admin@rydex.com")) {
                ClienteModel admin = new ClienteModel();
                admin.setNombre("Admin Rydex");
                admin.setDocumento("000000000");
                admin.setTelefono("3000000000");
                admin.setEmail("admin@rydex.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRol(RolUsuario.ADMIN);
                clienteRepository.save(admin);
                System.out.println("✅ Admin creado: admin@rydex.com / admin123");
            }

            if (!clienteRepository.existsByEmail("juan@email.com")) {
                ClienteModel cliente = new ClienteModel();
                cliente.setNombre("Juan Pérez");
                cliente.setDocumento("123456789");
                cliente.setTelefono("3001234567");
                cliente.setEmail("juan@email.com");
                cliente.setPassword(passwordEncoder.encode("cliente123"));
                cliente.setRol(RolUsuario.CLIENTE);
                clienteRepository.save(cliente);
                System.out.println("✅ Cliente creado: juan@email.com / cliente123");
            }
        };
    }
}