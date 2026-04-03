package com.bikeshop.rydex.repository;

import com.bikeshop.rydex.model.VentaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<VentaModel, Long> {

    List<VentaModel> findByClienteIdCliente(Long idCliente);

    List<VentaModel> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT SUM(v.total) FROM VentaModel v WHERE v.fecha >= :inicio")
    java.math.BigDecimal sumTotalSince(@Param("inicio") LocalDateTime inicio);

    // pa saber cuántas ventas se hicieron desde una fecha específica
    long countByFechaGreaterThanEqual(LocalDateTime fecha);

    // Busca todas las ventas de un cliente basándose en su email
    List<VentaModel> findByCliente_Email(String email);

    // Suma todos el dinero que ha entrado por ventas
    @Query("SELECT COALESCE(SUM(v.total), 0) FROM VentaModel v")
    java.math.BigDecimal sumarIngresosTotales();

    // Cuenta cuántas ventas se han hecho en total
    @Query("SELECT COUNT(v) FROM VentaModel v")
    long contarVentasTotales();

    // Esta consulta calcula la utilidad real: (Precio Venta - Precio Compra) * Cantidad
    @Query("SELECT COALESCE(SUM((dv.precioUnitario - b.precioCompra) * dv.cantidad), 0) " +
            "FROM DetalleVentaModel dv JOIN dv.bicicleta b")
    java.math.BigDecimal calcularUtilidadTotal();
}
