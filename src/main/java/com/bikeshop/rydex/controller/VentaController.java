package com.bikeshop.rydex.controller;

import com.bikeshop.rydex.dto.request.VentaRequest;
import com.bikeshop.rydex.model.VentaModel;
import com.bikeshop.rydex.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    // GET /api/ventas?fechaInicio=2026-03-02&fechaFin=2026-03-05
    @GetMapping
    public ResponseEntity<List<VentaModel>> getAll(
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        List<VentaModel> ventas = (fechaInicio != null && !fechaInicio.isBlank() && fechaFin != null && !fechaFin.isBlank())
                ? ventaService.findByRango(fechaInicio, fechaFin)
                : ventaService.findAll();

        return ResponseEntity.ok(ventas);
    }

    // POST /api/ventas
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody VentaRequest request) {
        VentaModel venta = ventaService.createVenta(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.ventaToMap(venta));
    }
}