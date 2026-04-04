package com.bikeshop.rydex.config;

import com.bikeshop.rydex.enums.RolUsuario;
import com.bikeshop.rydex.model.ClienteModel;
import com.bikeshop.rydex.model.ProveedorModel;
import com.bikeshop.rydex.repository.ClienteRepository;
import com.bikeshop.rydex.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final ClienteRepository clienteRepository;
    private final ProveedorRepository proveedorRepository; // NUEVO: Para los proveedores
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner initData() {
        return args -> {
            // ==========================================
            // 1. INICIALIZAR ADMIN Y CLIENTES
            // ==========================================
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

            if (!clienteRepository.existsByEmail("mostrador@rydex.com")) {
                ClienteModel mostrador = new ClienteModel();
                mostrador.setNombre("Cliente Mostrador");
                mostrador.setDocumento("999999999");
                mostrador.setTelefono("0000000000");
                mostrador.setEmail("mostrador@rydex.com");
                mostrador.setPassword(passwordEncoder.encode("12345678"));
                mostrador.setRol(RolUsuario.CLIENTE);
                clienteRepository.save(mostrador);
                System.out.println("✅ Cliente Mostrador creado: mostrador@rydex.com");
            }

            // ==========================================
            // 2. INICIALIZAR PROVEEDORES (Cero duplicados)
            // ==========================================
            if (proveedorRepository.count() == 0) {
                ProveedorModel prov1 = new ProveedorModel();
                prov1.setNombreEmpresa("Trek Colombia");
                prov1.setPersonaContacto("Carlos Ruiz");
                prov1.setTelefonoContacto("6014567890");
                prov1.setEmailContacto("ventas@trekcolombia.com");

                ProveedorModel prov2 = new ProveedorModel();
                prov2.setNombreEmpresa("Giant Bikes");
                prov2.setPersonaContacto("Maria Gomez");
                prov2.setTelefonoContacto("6019876543");
                prov2.setEmailContacto("contacto@giant.com");

                ProveedorModel prov3 = new ProveedorModel();
                prov3.setNombreEmpresa("Torettos S.A.S");
                prov3.setPersonaContacto("Dominic Toretto");
                prov3.setTelefonoContacto("3001112233");
                prov3.setEmailContacto("familia@torettos.com");

                proveedorRepository.saveAll(List.of(prov1, prov2, prov3));
                System.out.println("✅ Proveedores base creados con éxito.");
            }
        };
    }
}