package com.bikeshop.rydex.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id_proveedor")
    private Long idProveedor;

    @Column(nullable = false)
    @JsonProperty("nombre_empresa")
    private String nombreEmpresa;

    @JsonProperty("persona_contacto")
    private String personaContacto;

    @Column(nullable = false)
    @JsonProperty("telefono_contacto")
    private String telefonoContacto;

    @JsonProperty("email_contacto")
    private String emailContacto;
}