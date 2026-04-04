package com.bikeshop.rydex.config;

import com.bikeshop.rydex.enums.RolUsuario;
import com.bikeshop.rydex.enums.TipoBicicleta;
import com.bikeshop.rydex.model.BicicletaModel;
import com.bikeshop.rydex.model.ClienteModel;
import com.bikeshop.rydex.model.ProveedorModel;
import com.bikeshop.rydex.repository.BicicletaRepository;
import com.bikeshop.rydex.repository.ClienteRepository;
import com.bikeshop.rydex.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final ClienteRepository clienteRepository;
    private final ProveedorRepository proveedorRepository;
    private final BicicletaRepository bicicletaRepository;
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
            // 2. INICIALIZAR PROVEEDORES
            // ==========================================
            if (proveedorRepository.count() == 0) {
                proveedorRepository.saveAll(List.of(
                        crearProveedor("Trek Colombia", "Carlos Ruiz", "6014567890", "ventas@trekcolombia.com"),
                        crearProveedor("Giant Bikes", "Ana Mora", "6019876543", "contacto@giantbikes.co"),
                        crearProveedor("Torettos S.A.S", "Dominik Toretto", "8015276548", "torettos@bikes.com")
                ));
                System.out.println("✅ Proveedores base creados con éxito.");
            }

            // ==========================================
            // 3. INICIALIZAR BICICLETAS
            // ==========================================
            if (bicicletaRepository.count() == 0) {
                bicicletaRepository.saveAll(List.of(
                        // MOUNTAIN
                        crearBici("MTB-TREK-001", "Trek", "Marlin 7", "Mountain", 2800000.0, 2000000.0, 8, 3, "Bicicleta de montaña con suspensión delantera RockShox, cambios Shimano Deore 1x12.", "NEW ARRIVAL"),
                        crearBici("MTB-SPEC-002", "Specialized", "Rockhopper Expert", "Mountain", 3500000.0, 3000000.0, 5, 2, "Cuadro de aluminio FACT 3m, horquilla SR Suntour XCR, transmisión Shimano Deore.", null),
                        crearBici("MTB-GIANT-003", "Giant", "Talon 1", "Mountain", 2200000.0, 2000000.0, 10, 4, "Geometría agresiva para trail, frenos de disco hidráulicos Tektro, ruedas 29\".", null),
                        crearBici("MTB-SCOTT-004", "Scott", "Scale 940", "Mountain", 4100000.0, 3800000.0, 3, 2, "Full-suspension con amortiguador trasero RockShox Judy, ideal para enduro.", "LUXURY TIER"),

                        // ROAD
                        crearBici("RD-TREK-001", "Trek", "Domane AL 3", "Road", 3200000.0, 2735000.0, 6, 2, "Endurance road bike con cuadro aluminio, grupo Shimano Sora, ideal para largas distancias.", null),
                        crearBici("RD-CANNO-002", "Cannondale", "CAAD13", "Road", 5800000.0, 5283000.0, 4, 2, "Cuadro de aluminio de alto rendimiento, grupo Shimano 105, ruedas WTB ST i23.", "LUXURY TIER"),
                        crearBici("RD-GIANT-003", "Giant", "Contend 3", "Road", 2600000.0, 1950000.0, 7, 3, "Perfecta para iniciarse en ciclismo de ruta, cambios Shimano Claris, cuadro ALUXX.", "NEW ARRIVAL"),
                        crearBici("RD-BIANCHI-004", "Bianchi", "Via Nirone 7", "Road", 4400000.0, 3589000.0, 2, 2, "Icónica bicicleta italiana, cuadro Countervail, grupo Shimano Tiagra.", null),

                        // ELECTRIC
                        crearBici("EL-TREK-001", "Trek", "Verve+ 2", "Electric", 8900000.0, 7000000.0, 4, 2, "E-bike urbana con motor Bosch Active Line Plus, autonomía hasta 130km, display integrado.", "NEW ARRIVAL"),
                        crearBici("EL-SPEC-002", "Specialized", "Turbo Vado 3.0", "Electric", 12500000.0, 10000000.0, 2, 1, "Motor SL 1.1 de 240W, autonomía 130km, MasterMind TCU con conectividad Bluetooth.", "LUXURY TIER"),
                        crearBici("EL-GIANT-003", "Giant", "Explore E+ 2", "Electric", 7800000.0, 5000000.0, 5, 2, "Motor SyncDrive Sport, batería EnergyPak 500Wh, suspensión delantera SR Suntour.", null),
                        crearBici("EL-SCOTT-004", "Scott", "Sub Sport eRIDE 20", "Electric", 9600000.0, 9000000.0, 3, 2, "Motor Bosch Performance CX Gen4, autonomía 120km, perfecta para commuting.", null),

                        // GEAR
                        crearBici("GR-PURE-001", "Pure Cycles", "Original Fixed Gear", "Gear", 1200000.0, 900000.0, 12, 5, "Fixed gear clásica, cuadro cromoly, llanta doble pared, ideal para ciudad.", null),
                        crearBici("GR-STATE-002", "State Bicycle", "Core Line", "Gear", 1800000.0, 1000000.0, 8, 3, "Single speed premium, cuadro aluminio 6061, flip-flop hub, colores vibrantes.", "NEW ARRIVAL"),
                        crearBici("GR-LEADER-003", "Leader", "725TR", "Gear", 2400000.0, 1800000.0, 5, 2, "Track geometry agresiva, cuadro aluminio 7005, buje Cuando, para pista y calle.", "LUXURY TIER"),
                        crearBici("GR-CINELLI-004", "Cinelli", "Tutto", "Gear", 3100000.0, 2780093.0, 3, 2, "Ícono italiano del fixed gear, acero cromoly, geometría race, componentes Cinelli.", null)
                ));
                System.out.println("✅ Catálogo de bicicletas inicializado con éxito.");
            }
        };
    }

    // --- MÉTODOS AUXILIARES PARA LIMPIEZA DE CÓDIGO ---

    private ProveedorModel crearProveedor(String empresa, String contacto, String tel, String email) {
        ProveedorModel p = new ProveedorModel();
        p.setNombreEmpresa(empresa);
        p.setPersonaContacto(contacto);
        p.setTelefonoContacto(tel);
        p.setEmailContacto(email);
        return p;
    }

    private BicicletaModel crearBici(String sku, String marca, String modelo, String tipo, Double precio, Double costo, Integer stock, Integer min, String desc, String etiqueta) {
        BicicletaModel b = new BicicletaModel();
        b.setSku(sku);
        b.setMarca(marca);
        b.setModelo(modelo);
        b.setTipo(TipoBicicleta.valueOf(tipo.toUpperCase()));
        b.setPrecio(BigDecimal.valueOf(precio));
        b.setPrecioCompra(BigDecimal.valueOf(costo));
        b.setStockActual(stock);
        b.setStockMinimo(min);
        b.setDescripcion(desc);
        b.setEtiqueta(etiqueta);
        b.setImagenUrl("/placeholder-bike.jpg");
        return b;
    }
}