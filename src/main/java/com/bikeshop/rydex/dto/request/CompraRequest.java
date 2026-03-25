package com.bikeshop.rydex.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CompraRequest {

    @NotNull
    private Long id_proveedor;

    @NotNull
    private BigDecimal total;

    @NotNull
    private List<DetalleCompraRequest> detalles;

    @Data
    public static class DetalleCompraRequest {
        private Long id_bicicleta;
        private int cantidad;
        private BigDecimal precio_unitario;
        private BigDecimal subtotal;
    }
}