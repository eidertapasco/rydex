package com.bikeshop.rydex.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProveedorRequest {

    @NotBlank
    private String nombre_empresa;

    private String persona_contacto;

    @NotBlank
    private String telefono_contacto;

    private String email_contacto;
}