package com.bikeshop.rydex.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VentaRequest {

    @NotNull
    private Long id_cliente;

    @NotNull
    private BigDecimal total;

    @NotNull
    private List<DetalleVentaRequest> detalles;

    @Data
    public static class DetalleVentaRequest {
        private Long id_bicicleta;
        private int cantidad;
        private BigDecimal precio_unitario;
        private BigDecimal subtotal;
    }
}