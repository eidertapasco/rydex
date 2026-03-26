package com.bikeshop.rydex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detalle_compras")
public class DetalleCompraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id_detalle_compra")
    private Long idDetalleCompra;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    @JsonProperty("precio_unitario")
    private BigDecimal precioUnitario;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compra", nullable = false)
    @JsonIgnoreProperties({"detalles", "hibernateLazyInitializer", "handler"})
    private CompraModel compra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bicicleta", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private BicicletaModel bicicleta;
}