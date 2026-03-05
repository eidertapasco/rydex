package com.bikeshop.rydex.repository;

import com.bikeshop.rydex.model.VentaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<VentaModel, Long> {
}
