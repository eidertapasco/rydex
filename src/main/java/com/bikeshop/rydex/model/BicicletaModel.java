package com.bikeshop.rydex.model;

import com.bikeshop.rydex.enums.TipoBicicleta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bicicletas")
public class BicicletaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBicicleta;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private String marca;

    private String modelo;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoBicicleta tipo;

    @Column(nullable = false)
    private BigDecimal precio;

    // stock_actual: unidades disponibles actualmente
    @Column(nullable = false)
    private int stockActual;

    // stock_minimo: umbral mínimo antes de necesitar reabastecer
    @Column(nullable = false)
    private int stockMinimo;
}