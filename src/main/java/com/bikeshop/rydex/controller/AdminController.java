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
    private final ClienteRepository clienteRepository;

    // GET /api/admin/dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard() {
        LocalDateTime inicioDia = LocalDateTime.now().toLocalDate().atStartOfDay();

        BigDecimal ingresosDia = ventaRepository.sumTotalSince(inicioDia);
        long totalVentas       = ventaRepository.count();
        long totalClientes     = clienteRepository.count();
        long stockBajo         = bicicletaRepository.findAll().stream()
                .filter(b -> b.getStockActual() <= b.getStockMinimo())
                .count();

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("ingresosDia",   ingresosDia != null ? ingresosDia : BigDecimal.ZERO);
        metrics.put("totalVentas",   totalVentas);
        metrics.put("totalClientes", totalClientes);
        metrics.put("stockBajo",     stockBajo);

        return ResponseEntity.ok(metrics);
    }
}
