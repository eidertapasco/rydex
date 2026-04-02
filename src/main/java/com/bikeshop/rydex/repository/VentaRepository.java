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
}
