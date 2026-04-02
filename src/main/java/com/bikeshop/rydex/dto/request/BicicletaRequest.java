package com.bikeshop.rydex.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BicicletaRequest {

    @NotBlank
    private String sku;

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotBlank
    private String tipo;

    @NotNull
    @Positive
    private BigDecimal precio; // Precio de Venta

    // NUEVO: Precio de Compra
    @JsonProperty("precio_compra")
    private BigDecimal precioCompra;

    @JsonProperty("stock_actual")
    private int stockActual;

    @JsonProperty("stock_minimo")
    private int stockMinimo;

    @JsonProperty("imagen_url")
    private String imagenUrl;

    private String descripcion;

    private String etiqueta;
}