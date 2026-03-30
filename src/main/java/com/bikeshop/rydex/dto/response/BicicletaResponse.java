package com.bikeshop.rydex.dto.response;

import com.bikeshop.rydex.model.BicicletaModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BicicletaResponse {

    // Nombres en snake_case para que coincidan con la interface Angular
    private Long id_bicicleta;
    private String sku;
    private String marca;
    private String modelo;
    private String tipo;
    private BigDecimal precio;
    private String imagen_url;
    private int stock_actual;
    private int stock_minimo;
    private String descripcion;
    private String etiqueta;

    // Convierte el model a DTO
    public static BicicletaResponse from(BicicletaModel b) {
        BicicletaResponse dto = new BicicletaResponse();
        dto.id_bicicleta = b.getIdBicicleta();
        dto.sku          = b.getSku();
        dto.marca        = b.getMarca();
        dto.modelo       = b.getModelo();
        dto.tipo         = b.getTipo() != null ? b.getTipo().name() : null;
        dto.precio       = b.getPrecio();
        dto.imagen_url   = b.getImagenUrl();
        dto.stock_actual = b.getStockActual();
        dto.stock_minimo = b.getStockMinimo();
        dto.descripcion  = b.getDescripcion();
        dto.etiqueta     = b.getEtiqueta();
        return dto;
    }
}