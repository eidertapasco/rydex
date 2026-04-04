package com.bikeshop.rydex.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VentaRequest {

    // Ya no es @NotNull, porque puede venir vacío si vamos a crear un cliente nuevo
    @JsonProperty("id_cliente")
    private Long idCliente;

    // NUEVO: La dirección de envío
    @JsonProperty("direccion_envio")
    private String direccionEnvio;

    // NUEVOS: Campos opcionales para crear cliente en ventas físicas
    @JsonProperty("nuevo_cliente_nombre")
    private String nuevoClienteNombre;

    @JsonProperty("nuevo_cliente_documento")
    private String nuevoClienteDocumento;

    @JsonProperty("nuevo_cliente_telefono")
    private String nuevoClienteTelefono;

    @JsonProperty("nuevo_cliente_email")
    private String nuevoClienteEmail;

    @NotNull
    private BigDecimal total;

    @NotNull
    private List<DetalleVentaRequest> detalles;

    @Data
    public static class DetalleVentaRequest {

        @JsonProperty("id_bicicleta")
        private Long idBicicleta;

        private int cantidad;

        @JsonProperty("precio_unitario")
        private BigDecimal precioUnitario;

        private BigDecimal subtotal;
    }
}