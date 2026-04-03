package com.bikeshop.rydex.repository;

import com.bikeshop.rydex.model.DetalleCompraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompraModel, Long> {
}