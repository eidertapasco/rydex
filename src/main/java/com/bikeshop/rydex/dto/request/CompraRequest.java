package com.bikeshop.rydex.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CompraRequest {

    @NotNull
    @JsonProperty("id_proveedor")
    private Long idProveedor;

    @NotNull
    private BigDecimal total;

    @NotNull
    private List<DetalleCompraRequest> detalles;

    @Data
    public static class DetalleCompraRequest {

        @JsonProperty("id_bicicleta")
        private Long idBicicleta;

        private int cantidad;

        @JsonProperty("precio_unitario")
        private BigDecimal precioUnitario;

        private BigDecimal subtotal;
    }
}