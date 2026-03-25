package com.bikeshop.rydex.dto.response;

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
        private Long idCliente;
        private String nombre;
        private String email;
        private String documento;
        private String telefono;
        private String rol;
    }
}