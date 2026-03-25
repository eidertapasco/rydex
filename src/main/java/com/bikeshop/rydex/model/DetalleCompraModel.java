package com.bikeshop.rydex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detalles_compra")
public class DetalleCompraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleCompra;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Double precioUnitario;

    @Column(nullable = false)
    private Double subtotal;

    // Relación con la Compra (Cabecera)
    @ManyToOne
    @JoinColumn(name = "id_compra", nullable = false)
    private CompraModel compra;

    // Relación con la Bicicleta (Producto)
    @ManyToOne
    @JoinColumn(name = "id_bicicleta", nullable = false)
    private BicicletaModel bicicleta;
}