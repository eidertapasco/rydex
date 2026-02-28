package com.bikeshop.rydex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor // <--- Constructor vacío para JPA
@AllArgsConstructor // <--- Constructor lleno para desarrollo/testing
@Table(name = "detalle_ventas")
public class DetalleVentaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "id_bicicleta")
    private BicicletaModel bicicleta;

    @ManyToOne
    @JoinColumn(name = "id_venta")
    private VentaModel venta;
}
