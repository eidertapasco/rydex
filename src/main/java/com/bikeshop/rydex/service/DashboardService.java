package com.bikeshop.rydex.service;

import com.bikeshop.rydex.repository.BicicletaRepository;
import com.bikeshop.rydex.repository.CompraRepository;
import com.bikeshop.rydex.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final VentaRepository ventaRepository;
    private final CompraRepository compraRepository;
    private final BicicletaRepository bicicletaRepository;

    public Map<String, Object> getMetrics() {
        // 1. Obtener ingresos y asegurar que no sean null
        BigDecimal ingresos = ventaRepository.sumarIngresosTotales();
        if (ingresos == null) ingresos = BigDecimal.ZERO;

        // 2. Obtener egresos y asegurar que no sean null
        BigDecimal egresos = compraRepository.sumarEgresosTotales();
        if (egresos == null) egresos = BigDecimal.ZERO;

        // 3. Calcular la Ganancia Neta
        BigDecimal gananciaNeta = ventaRepository.calcularUtilidadTotal();

        // 4. Obtener datos de inventario y ventas
        long totalBicicletas = bicicletaRepository.contarTotalBicicletas();
        long stockBajo = bicicletaRepository.contarBicicletasStockBajo();
        long ventasTotales = ventaRepository.contarVentasTotales();

        // 5. Empaquetar todo con los nombres EXACTOS que espera Angular
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("ingresosTotales", ingresos);
        metrics.put("egresosTotales", egresos);
        metrics.put("gananciaNeta", gananciaNeta);
        metrics.put("totalBicicletas", totalBicicletas);
        metrics.put("stockBajo", stockBajo);
        metrics.put("ventasHoy", ventasTotales); // Usaremos el total histórico como ejemplo

        return metrics;
    }
}