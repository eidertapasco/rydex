package com.bikeshop.rydex.repository;

import com.bikeshop.rydex.enums.TipoBicicleta;
import com.bikeshop.rydex.model.BicicletaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BicicletaRepository extends JpaRepository<BicicletaModel, Long>{

    Optional<BicicletaModel> findBySku(String sku);

    List<BicicletaModel> findByTipo(TipoBicicleta tipo);

    // Distinct marcas for sidebar filter
    @Query("SELECT DISTINCT b.marca FROM BicicletaModel b ORDER BY b.marca")
    List<String> findDistinctMarcas();

    // Filtro combinado con todos los parámetros opcionales
    @Query("""
        SELECT b FROM BicicletaModel b
        WHERE (:tipo IS NULL OR b.tipo = :tipo)
          AND (:marca IS NULL OR b.marca = :marca)
          AND (:precioMin IS NULL OR b.precio >= :precioMin)
          AND (:precioMax IS NULL OR b.precio <= :precioMax)
          AND (:q IS NULL OR LOWER(b.marca) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(b.modelo) LIKE LOWER(CONCAT('%', :q, '%')))
        """)
    List<BicicletaModel> findWithFilters(
            @Param("tipo") TipoBicicleta tipo,
            @Param("marca") String marca,
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax,
            @Param("q") String q
    );

    // Cuenta cuántas bicicletas diferentes hay
    @Query("SELECT COUNT(b) FROM BicicletaModel b")
    long contarTotalBicicletas();

    // Cuenta cuántas bicicletas están en stock bajo (stock_actual <= stock_minimo)
    @Query("SELECT COUNT(b) FROM BicicletaModel b WHERE b.stockActual <= b.stockMinimo")
    long contarBicicletasStockBajo();

}
