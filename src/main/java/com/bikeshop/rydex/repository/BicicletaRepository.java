package com.bikeshop.rydex.repository;

import com.bikeshop.rydex.model.BicicletaModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface BicicletaRepository extends JpaRepository<BicicletaModel, Long>{

    Optional<BicicletaModel> findBySku(String sku);
}
