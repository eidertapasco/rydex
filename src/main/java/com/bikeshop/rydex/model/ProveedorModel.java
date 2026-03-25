package com.bikeshop.rydex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "proveedores")
public class ProveedorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProveedor;

    @Column(nullable = false)
    private String nombreEmpresa;

    @Column(nullable = false)
    private String personaContacto;

    @Column(nullable = false)
    private String telefonoContacto;

    @Column(nullable = false, unique = true)
    private String emailContacto;
}