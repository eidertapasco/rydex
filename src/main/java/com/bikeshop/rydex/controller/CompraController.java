package com.bikeshop.rydex.controller;

import com.bikeshop.rydex.dto.request.CompraRequest;
import com.bikeshop.rydex.model.CompraModel;
import com.bikeshop.rydex.service.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @GetMapping
    public ResponseEntity<List<CompraModel>> getAll() {
        return ResponseEntity.ok(compraService.findAll());
    }

    @PostMapping
    public ResponseEntity<CompraModel> create(@Valid @RequestBody CompraRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compraService.createCompra(request));
    }
}