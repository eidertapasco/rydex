package com.bikeshop.rydex.controller;

import com.bikeshop.rydex.dto.request.ProveedorRequest;
import com.bikeshop.rydex.model.ProveedorModel;
import com.bikeshop.rydex.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<ProveedorModel>> getAll() {
        return ResponseEntity.ok(proveedorService.findAll());
    }

    @PostMapping
    public ResponseEntity<ProveedorModel> create(@Valid @RequestBody ProveedorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorModel> update(
            @PathVariable Long id,
            @Valid @RequestBody ProveedorRequest request) {
        return ResponseEntity.ok(proveedorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        proveedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}