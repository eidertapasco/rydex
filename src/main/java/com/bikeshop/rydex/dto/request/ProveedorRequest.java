package com.bikeshop.rydex.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProveedorRequest {

    @NotBlank
    @JsonProperty("nombre_empresa")
    private String nombreEmpresa;

    @JsonProperty("persona_contacto")
    private String personaContacto;

    @NotBlank
    @JsonProperty("telefono_contacto")
    private String telefonoContacto;

    @JsonProperty("email_contacto")
    private String emailContacto;
}