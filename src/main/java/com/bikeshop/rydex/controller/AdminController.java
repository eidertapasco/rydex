package com.bikeshop.rydex.controller;

import com.bikeshop.rydex.repository.BicicletaRepository;
import com.bikeshop.rydex.repository.ClienteRepository;
import com.bikeshop.rydex.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final VentaRepository ventaRepository;
    private final BicicletaRepository bicicletaRepository;
    private final ClienteRepository clienteRepository; // Lo dejamos por si lo uso luego

    // GET /api/admin/dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard() {
        // Fechas de corte
        LocalDateTime inicioHoy = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime inicioSemana = LocalDateTime.now().minusDays(7).toLocalDate().atStartOfDay();

        // Cálculos de Hoy
        BigDecimal ingresosHoy = ventaRepository.sumTotalSince(inicioHoy);
        long ventasHoy = ventaRepository.countByFechaGreaterThanEqual(inicioHoy);

        // Cálculos de la Semana
        BigDecimal ingresosSemana = ventaRepository.sumTotalSince(inicioSemana);
        long ventasSemana = ventaRepository.countByFechaGreaterThanEqual(inicioSemana);

        // Cálculos de Inventario
        long totalBicicletas = bicicletaRepository.count();
        long stockBajo = bicicletaRepository.findAll().stream()
                .filter(b -> b.getStockActual() <= b.getStockMinimo())
                .count();

        // Mapeo exacto a lo que Angular espera
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("ventasHoy",       ventasHoy);
        metrics.put("ingresosHoy",     ingresosHoy != null ? ingresosHoy : BigDecimal.ZERO);
        metrics.put("totalBicicletas", totalBicicletas);
        metrics.put("stockBajo",       stockBajo);
        metrics.put("ventasSemana",    ventasSemana);
        metrics.put("ingresosSemana",  ingresosSemana != null ? ingresosSemana : BigDecimal.ZERO);

        return ResponseEntity.ok(metrics);
    }
}
