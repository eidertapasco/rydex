package com.bikeshop.rydex.model;

import com.bikeshop.rydex.enums.TipoBicicleta;
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

    private String etiqueta; // "NEW ARRIVAL" | "LUXURY TIER" | null

    @Enumerated(EnumType.STRING)
    private TipoBicicleta tipo;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column(name = "imagen_url")
    @JsonProperty("imagen_url")
    private String imagenUrl;

    // stock_actual: unidades disponibles actualmente
    @Column(nullable = false)
    private int stockActual;

    // stock_minimo: umbral mínimo antes de necesitar reabastecer
    @Column(nullable = false)
    private int stockMinimo;
}