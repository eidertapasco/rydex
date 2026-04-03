package com.bikeshop.rydex.repository;

import com.bikeshop.rydex.model.DetalleVentaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVentaModel, Long> {
}
