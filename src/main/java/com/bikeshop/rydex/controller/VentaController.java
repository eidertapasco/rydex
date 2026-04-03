package com.bikeshop.rydex.controller;

import com.bikeshop.rydex.dto.request.VentaRequest;
import com.bikeshop.rydex.model.VentaModel;
import com.bikeshop.rydex.service.PdfService;
import com.bikeshop.rydex.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;
    private final PdfService pdfService; // <-- NUEVO: Inyectamos el creador de PDFs

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

    // NUEVO: GET /api/ventas/{id}/factura
    @GetMapping("/{id}/factura")
    public ResponseEntity<byte[]> descargarFactura(@PathVariable Long id) {
        // 1. Buscamos la venta usando tu servicio
        VentaModel venta = ventaService.findById(id);

        // 2. Le pedimos al PdfService que "dibuje" la factura
        byte[] pdfBytes = pdfService.generarFacturaVenta(venta);

        // 3. Empaquetamos la respuesta para que el navegador la descargue como PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Factura_Rydex_" + id + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    // NUEVO: GET /api/ventas/mis-compras
    @GetMapping("/mis-compras")
    public ResponseEntity<List<VentaModel>> getMisCompras(Principal principal) {
        // principal.getName() nos da el email del usuario logueado (sacado del JWT)
        String emailUsuarioLogueado = principal.getName();

        List<VentaModel> misCompras = ventaService.getMisCompras(emailUsuarioLogueado);
        return ResponseEntity.ok(misCompras);
    }
}