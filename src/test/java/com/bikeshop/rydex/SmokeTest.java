/* package com.bikeshop.rydex;

import com.bikeshop.rydex.model.ProveedorModel;
import com.bikeshop.rydex.repository.ProveedorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SmokeTest {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Test
    void debeConectarYGuardarProveedor() {
        // 1. Creamos un objeto de prueba (los datos pueden ser cualquiera)
        ProveedorModel proveedor = new ProveedorModel();
        proveedor.setNombreEmpresa("SENA Proveedor Test");
        proveedor.setPersonaContacto("Instructor de Prueba");
        proveedor.setTelefonoContacto("123456789");

        // 2. Ejecutamos el guardado en la base de datos (H2 en este caso)
        ProveedorModel guardado = proveedorRepository.save(proveedor);

        // 3. Verificamos que no sea nulo y que tenga un ID asignado
        assertThat(guardado).isNotNull();
        assertThat(guardado.getIdProveedor()).isNotNull();

        System.out.println("✅ Smoke Test Pasado: La base de datos aceptó el nuevo Proveedor con ID: " + guardado.getIdProveedor());
    }
} */