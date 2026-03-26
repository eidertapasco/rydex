package com.bikeshop.rydex.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VentaRequest {

    @NotNull
    @JsonProperty("id_cliente")
    private Long idCliente;

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