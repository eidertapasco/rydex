package com.bikeshop.rydex.controller;

import com.bikeshop.rydex.service.DashboardService;
import com.bikeshop.rydex.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final PdfService pdfService;

    // GET /api/admin/dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        return ResponseEntity.ok(dashboardService.getMetrics());
    }

    // GET /api/admin/reporte-financiero
    @GetMapping("/reporte-financiero")
    public ResponseEntity<byte[]> descargarReporteFinanciero() {
        // 1. Obtenemos los mismos números reales del dashboard
        Map<String, Object> metrics = dashboardService.getMetrics();

        // 2. Le pasamos esos números al PdfService para que dibuje el reporte
        byte[] pdfBytes = pdfService.generarReporteFinanciero(metrics);

        // 3. Empaquetamos la respuesta para descargar
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Le ponemos fecha al nombre del archivo
        String fileName = "Rydex_Reporte_" + java.time.LocalDate.now() + ".pdf";
        headers.setContentDispositionFormData("attachment", fileName);

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    // GET /api/admin/reporte-ventas-detalle?inicio=2026-04-01T00:00:00&fin=2026-04-08T23:59:59
    @GetMapping("/reporte-ventas-detalle")
    public ResponseEntity<byte[]> descargarReporteVentasDetalle(
            @org.springframework.web.bind.annotation.RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime inicio,
            @org.springframework.web.bind.annotation.RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime fin) {

        byte[] pdfBytes = dashboardService.generarPdfVentasPorFechas(inicio, fin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Reporte_Ingresos_" + java.time.LocalDate.now() + ".pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    // GET /api/admin/reporte-compras-detalle?inicio=2026-04-01T00:00:00&fin=2026-04-08T23:59:59
    @GetMapping("/reporte-compras-detalle")
    public ResponseEntity<byte[]> descargarReporteComprasDetalle(
            @org.springframework.web.bind.annotation.RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime inicio,
            @org.springframework.web.bind.annotation.RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime fin) {

        byte[] pdfBytes = dashboardService.generarPdfComprasPorFechas(inicio, fin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Reporte_Egresos_" + java.time.LocalDate.now() + ".pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    // GET /api/admin/reporte-ganancias-detalle
    @GetMapping("/reporte-ganancias-detalle")
    public ResponseEntity<byte[]> descargarReporteGananciasDetalle(
            @org.springframework.web.bind.annotation.RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime inicio,
            @org.springframework.web.bind.annotation.RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime fin) {

        byte[] pdfBytes = dashboardService.generarPdfGananciasPorFechas(inicio, fin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Reporte_Ganancia_Neta_" + java.time.LocalDate.now() + ".pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

}