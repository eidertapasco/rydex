package com.bikeshop.rydex.repository;

import com.bikeshop.rydex.model.CompraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<CompraModel, Long> {

    // Suma toda el dinero pagado a proveedores
    @Query("SELECT COALESCE(SUM(c.total), 0) FROM CompraModel c")
    java.math.BigDecimal sumarEgresosTotales();
}