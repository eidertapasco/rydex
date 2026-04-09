package com.bikeshop.rydex.repository;

import com.bikeshop.rydex.model.CompraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<CompraModel, Long> {

    // Suma toda el dinero pagado a proveedores (Histórico)
    @Query("SELECT COALESCE(SUM(c.total), 0) FROM CompraModel c")
    java.math.BigDecimal sumarEgresosTotales();

    // NUEVO: Busca las compras en un rango de fechas específico
    List<CompraModel> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}