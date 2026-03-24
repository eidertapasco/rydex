package com.bikeshop.rydex.repository;

import com.bikeshop.rydex.model.ProveedorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorModel, Long> {
     Optional<ProveedorModel> findBynombreEmpresa(String nombre);
}