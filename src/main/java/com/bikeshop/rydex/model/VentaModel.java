package com.bikeshop.rydex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor // <--- Constructor vacío para JPA
@AllArgsConstructor // <--- Constructor lleno para desarrollo/testing
@Table(name = "ventas")
public class VentaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    //La anotación indica que una venta puede tener muchos detalles (ítems de la venta) y que esta mapeada o controlada desde la misma clase.
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL) //permite que al guardar la Venta, se guarden automáticamente todos sus detalles.
    List<DetalleVentaModel> detalles;

    @Column(nullable = false)
    private LocalDateTime fecha;

    private BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private ClienteModel cliente;

    @PrePersist //Asigna la fecha y hora actual al campo fecha en el momento exacto de la inserción.
    protected void onCreate(){
        this.fecha = LocalDateTime.now();
    }

}
