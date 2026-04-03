/*package com.bikeshop.rydex;

import com.bikeshop.rydex.model.CompraModel;
import com.bikeshop.rydex.model.ProveedorModel;
import com.bikeshop.rydex.repository.CompraRepository;
import com.bikeshop.rydex.repository.ProveedorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CompraRepositoryTest {

    @Autowired
    private CompraRepository compraRepository; // Variable en minúscula

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Test
    void debeGuardarCompraConProveedor() {
        // 1. Crear y guardar el proveedor
        ProveedorModel prov = new ProveedorModel();
        prov.setNombreEmpresa("Shimano Oficial");
        prov.setPersonaContacto("Soporte");
        prov.setTelefonoContacto("123");
        prov.setEmailContacto("test@mail.com");
        prov = proveedorRepository.save(prov);

        // 2. Crear la compra con los nombres EXACTOS de tus variables
        CompraModel compra = new CompraModel();
        compra.setFecha(LocalDateTime.now()); // Antes era setFechaCompra (ERROR)
        compra.setTotal(1500000.0);           // Antes era setTotalCompra (ERROR)
        compra.setProveedor(prov);

        // 3. Guardar usando la instancia inyectada (compraRepository en minúscula)
        CompraModel guardada = compraRepository.save(compra);

        // 4. Verificaciones
        assertThat(guardada.getIdCompra()).isNotNull();
        assertThat(guardada.getProveedor().getNombreEmpresa()).isEqualTo("Shimano Oficial");

        System.out.println("✅ Relación Exitosa: Compra registrada con ID: " + guardada.getIdCompra());
    }
} */