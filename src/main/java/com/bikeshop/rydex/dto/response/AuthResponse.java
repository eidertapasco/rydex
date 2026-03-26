package com.bikeshop.rydex.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private ClienteResponse cliente;

    @Data
    @AllArgsConstructor
    public static class ClienteResponse {

        @JsonProperty("id_cliente")
        private Long idCliente;

        private String nombre;
        private String email;
        private String documento;
        private String telefono;
        private String rol;
    }
}