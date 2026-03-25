package com.bikeshop.rydex.repository;

import com.bikeshop.rydex.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
    Optional<ClienteModel> findByEmail(String email); // para el login
    Optional<ClienteModel> findByDocumento(String documento);
    boolean existsByEmail(String email);
    boolean existsByDocumento(String documento);
}
