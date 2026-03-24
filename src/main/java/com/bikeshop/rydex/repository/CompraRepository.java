package com.bikeshop.rydex.repository;

import com.bikeshop.rydex.model.CompraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<CompraModel, Long> {
    // Útil para buscar compras de un proveedor específico en el futuro
}