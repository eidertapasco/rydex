package com.bikeshop.rydex.controller;

import com.bikeshop.rydex.dto.request.BicicletaRequest;
import com.bikeshop.rydex.dto.response.BicicletaResponse;
import com.bikeshop.rydex.dto.response.PagedResponse;
import com.bikeshop.rydex.service.BicicletaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/bicicletas")
@RequiredArgsConstructor
public class BicicletaController {

    private final BicicletaService bicicletaService;

    // GET /api/bicicletas/marcas  <-- DEBE ir ANTES de /:id o Spring lo confunde
    @GetMapping("/marcas")
    public ResponseEntity<List<String>> getMarcas() {
        return ResponseEntity.ok(bicicletaService.findMarcas());
    }

    // GET /api/bicicletas?tipo=Mountain&marca=Trek&q=texto&precioMin=0&precioMax=5000&page=0&pageSize=12
    @GetMapping
    public ResponseEntity<PagedResponse<BicicletaResponse>> getAll(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "500") int pageSize) {

        return ResponseEntity.ok(
                bicicletaService.findAll(tipo, marca, precioMin, precioMax, q, page, pageSize)
        );
    }

    // GET /api/bicicletas/:id
    @GetMapping("/{id}")
    public ResponseEntity<BicicletaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bicicletaService.findById(id));
    }

    // POST /api/bicicletas  (solo ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BicicletaResponse> create(@Valid @RequestBody BicicletaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bicicletaService.create(request));
    }

    // PUT /api/bicicletas/:id  (solo ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BicicletaResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody BicicletaRequest request) {
        return ResponseEntity.ok(bicicletaService.update(id, request));
    }

    // DELETE /api/bicicletas/:id  (solo ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bicicletaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}