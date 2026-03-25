package com.bikeshop.rydex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compras")
public class CompraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompra;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private Double total;

    // Relación Many-to-One: Muchas compras pertenecen a un proveedor
    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private ProveedorModel proveedor;
}