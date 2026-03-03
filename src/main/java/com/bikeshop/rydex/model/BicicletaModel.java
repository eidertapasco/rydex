package com.bikeshop.rydex.model;

import com.bikeshop.rydex.enums.TipoBicicleta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor // <--- Constructor vacío para JPA
@AllArgsConstructor // <--- Constructor lleno para desarrollo/testing
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

    @Enumerated(EnumType.STRING)
    private TipoBicicleta tipo;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column(nullable = false)
    private int stock;
}
