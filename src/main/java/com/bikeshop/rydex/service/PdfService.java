package com.bikeshop.rydex.service;

import com.bikeshop.rydex.model.CompraModel;
import com.bikeshop.rydex.model.DetalleCompraModel;
import com.bikeshop.rydex.model.DetalleVentaModel;
import com.bikeshop.rydex.model.VentaModel;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class PdfService {

    // Logger profesional para reemplazar e.printStackTrace()
    private static final Logger logger = LoggerFactory.getLogger(PdfService.class);

    private final Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.BLACK);
    private final Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 11, Color.DARK_GRAY);
    private final Font fontEncabezado = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
    private final Font fontCelda = FontFactory.getFont(FontFactory.HELVETICA, 9, Color.BLACK);
    private final Font fontCeldaBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.BLACK);
    private final DateTimeFormatter formatterFechaHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String formatoMoneda(BigDecimal valor) {
        if (valor == null) valor = BigDecimal.ZERO;
        return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(valor);
    }

    private void agregarEncabezadoReporte(Document document, String tituloStr, LocalDateTime inicio, LocalDateTime fin) throws DocumentException {
        Paragraph titulo = new Paragraph(tituloStr, fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        String periodoStr = (inicio != null && fin != null)
                ? "Periodo: " + inicio.format(formatterFecha) + " al " + fin.format(formatterFecha)
                : "Histórico Completo";

        Paragraph subtitulo = new Paragraph(periodoStr + "\nGenerado el: " + LocalDateTime.now().format(formatterFechaHora) + "\nGenerado por: Administrador del Sistema", fontSubtitulo);
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        subtitulo.setSpacingAfter(20f);
        document.add(subtitulo);
    }

    // =========================================================================
    // 1. FACTURA DE VENTA INDIVIDUAL
    // =========================================================================
    public byte[] generarFacturaVenta(VentaModel venta) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Configuramos el documento (Tamaño carta con márgenes)
        Document document = new Document(PageSize.LETTER, 50, 50, 50, 50);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // 1. TÍTULO DE LA EMPRESA (RYDEX)
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, Color.BLACK);
            Paragraph titulo = new Paragraph("RYDEX", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            Paragraph subtitulo = new Paragraph("Tienda de Bicicletas Premium\nFactura Electrónica de Venta",
                    FontFactory.getFont(FontFactory.HELVETICA, 12, Color.DARK_GRAY));
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(20);
            document.add(subtitulo);

            // 2. DATOS DE LA VENTA Y CLIENTE (ACTUALIZADO)
            String fechaStr = venta.getFecha() != null ? venta.getFecha().format(formatterFechaHora) : "N/A";

            Font fontDatosBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.BLACK);
            Font fontDatos = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);

            // Usamos una mini-tabla para alinear la info del cliente a la izquierda y la de la factura a la derecha
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{1f, 1f});

            // Columna Izquierda (Cliente)
            PdfPCell cellCliente = new PdfPCell();
            cellCliente.setBorder(Rectangle.NO_BORDER);
            cellCliente.addElement(new Paragraph("DATOS DEL CLIENTE:", fontDatosBold));
            cellCliente.addElement(new Paragraph("Nombre: " + venta.getCliente().getNombre(), fontDatos));
            cellCliente.addElement(new Paragraph("Documento: " + (venta.getCliente().getDocumento() != null ? venta.getCliente().getDocumento() : "N/A"), fontDatos));
            cellCliente.addElement(new Paragraph("Teléfono: " + (venta.getCliente().getTelefono() != null ? venta.getCliente().getTelefono() : "N/A"), fontDatos));
            cellCliente.addElement(new Paragraph("Email: " + (venta.getCliente().getEmail() != null ? venta.getCliente().getEmail() : "N/A"), fontDatos));

            // Validamos la dirección de envío
            String direccion = (venta.getDireccionEnvio() != null && !venta.getDireccionEnvio().isBlank())
                    ? venta.getDireccionEnvio()
                    : "Entrega en tienda / Mostrador";
            cellCliente.addElement(new Paragraph("Dirección: " + direccion, fontDatos));
            headerTable.addCell(cellCliente);

            // Columna Derecha (Factura)
            PdfPCell cellFactura = new PdfPCell();
            cellFactura.setBorder(Rectangle.NO_BORDER);
            cellFactura.setHorizontalAlignment(Element.ALIGN_RIGHT);

            // Creamos los párrafos alineados a la derecha
            Paragraph pFactura = new Paragraph("FACTURA #: V-" + venta.getIdVenta(), fontDatosBold);
            pFactura.setAlignment(Element.ALIGN_RIGHT);
            cellFactura.addElement(pFactura);

            Paragraph pFecha = new Paragraph("Fecha: " + fechaStr, fontDatos);
            pFecha.setAlignment(Element.ALIGN_RIGHT);
            cellFactura.addElement(pFecha);

            headerTable.addCell(cellFactura);

            // Añadimos el encabezado al documento
            document.add(headerTable);

            // Espacio antes de la tabla de productos
            document.add(new Paragraph(" ", fontDatos));

            // 3. TABLA DE PRODUCTOS
            PdfPTable table = new PdfPTable(4); // 4 columnas
            table.setWidthPercentage(100);
            table.setWidths(new float[]{4f, 1f, 2f, 2f}); // Proporción de las columnas
            table.setSpacingBefore(10f);

            // Encabezados de la tabla
            String[] encabezados = {"Descripción", "Cant.", "V. Unitario", "Subtotal"};
            for (String header : encabezados) {
                PdfPCell cell = new PdfPCell(new Phrase(header, fontEncabezado));
                cell.setBackgroundColor(new Color(255, 77, 0)); // El color naranja de Rydex
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8);
                table.addCell(cell);
            }

            // Filas de los productos
            for (DetalleVentaModel detalle : venta.getDetalles()) {
                table.addCell(new PdfPCell(new Phrase(detalle.getBicicleta().getMarca() + " " + detalle.getBicicleta().getModelo(), fontCelda)));

                PdfPCell cellCant = new PdfPCell(new Phrase(String.valueOf(detalle.getCantidad()), fontCelda));
                cellCant.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellCant);

                table.addCell(new PdfPCell(new Phrase("$" + detalle.getPrecioUnitario().toString(), fontCelda)));
                table.addCell(new PdfPCell(new Phrase("$" + detalle.getSubtotal().toString(), fontCelda)));
            }
            document.add(table);

            // 4. TOTAL
            Paragraph total = new Paragraph("TOTAL PAGADO: $" + venta.getTotal().toString(),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLACK));
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(15f);
            document.add(total);

            document.close();
        } catch (DocumentException e) {
            logger.error("Error al generar factura de venta: {}", e.getMessage(), e);
        }

        return out.toByteArray();
    }

    // =========================================================================
    // 2. REPORTE DETALLADO DE VENTAS (INGRESOS)
    // =========================================================================
    public byte[] generarReporteVentasDetallado(List<VentaModel> ventas, LocalDateTime inicio, LocalDateTime fin) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.LETTER.rotate(), 30, 30, 40, 40); // Horizontal para que quepa el detalle

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            agregarEncabezadoReporte(document, "REPORTE DETALLADO DE INGRESOS (VENTAS)", inicio, fin);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1f, 1.5f, 2f, 4f, 1.5f}); // ID, Fecha, Cliente, Detalle, Total

            String[] encabezados = {"ID Venta", "Fecha", "Cliente", "Detalle de Artículos", "Total"};
            for (String header : encabezados) {
                PdfPCell cell = new PdfPCell(new Phrase(header, fontEncabezado));
                cell.setBackgroundColor(new Color(16, 185, 129)); // Verde
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6f);
                table.addCell(cell);
            }

            BigDecimal totalPeriodo = BigDecimal.ZERO;

            for (VentaModel v : ventas) {
                table.addCell(new PdfPCell(new Phrase("V-" + v.getIdVenta(), fontCelda)));
                table.addCell(new PdfPCell(new Phrase(v.getFecha().format(formatterFechaHora), fontCelda)));
                table.addCell(new PdfPCell(new Phrase(v.getCliente() != null ? v.getCliente().getNombre() : "N/A", fontCelda)));

                // Construir el detalle de las bicicletas
                StringBuilder detalleArticulos = new StringBuilder();
                for (DetalleVentaModel d : v.getDetalles()) {
                    detalleArticulos.append(d.getCantidad()).append("x ")
                            .append(d.getBicicleta().getMarca()).append(" ")
                            .append(d.getBicicleta().getModelo())
                            .append(" (").append(formatoMoneda(d.getPrecioUnitario())).append(" c/u)\n");
                }
                PdfPCell cellDetalle = new PdfPCell(new Phrase(detalleArticulos.toString(), fontCelda));
                table.addCell(cellDetalle);

                PdfPCell cellTotal = new PdfPCell(new Phrase(formatoMoneda(v.getTotal()), fontCeldaBold));
                cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cellTotal);

                totalPeriodo = totalPeriodo.add(v.getTotal() != null ? v.getTotal() : BigDecimal.ZERO);
            }
            document.add(table);

            Paragraph total = new Paragraph("TOTAL INGRESOS DEL PERIODO: " + formatoMoneda(totalPeriodo),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLACK));
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(15f);
            document.add(total);

            document.close();
        } catch (DocumentException e) {
            logger.error("Error al generar reporte de ventas detallado: {}", e.getMessage(), e);
        }
        return out.toByteArray();
    }

    // =========================================================================
    // 3. REPORTE DETALLADO DE COMPRAS (EGRESOS)
    // =========================================================================
    public byte[] generarReporteComprasDetallado(List<CompraModel> compras, LocalDateTime inicio, LocalDateTime fin) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.LETTER.rotate(), 30, 30, 40, 40);

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            agregarEncabezadoReporte(document, "REPORTE DETALLADO DE EGRESOS (COMPRAS)", inicio, fin);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1f, 1.5f, 2f, 4f, 1.5f});

            String[] encabezados = {"ID Compra", "Fecha", "Proveedor", "Detalle de Artículos", "Total"};
            for (String header : encabezados) {
                PdfPCell cell = new PdfPCell(new Phrase(header, fontEncabezado));
                cell.setBackgroundColor(new Color(239, 68, 68)); // Rojo
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6f);
                table.addCell(cell);
            }

            BigDecimal totalPeriodo = BigDecimal.ZERO;

            for (CompraModel c : compras) {
                table.addCell(new PdfPCell(new Phrase("C-" + c.getIdCompra(), fontCelda)));
                table.addCell(new PdfPCell(new Phrase(c.getFecha().format(formatterFechaHora), fontCelda)));
                table.addCell(new PdfPCell(new Phrase(c.getProveedor() != null ? c.getProveedor().getNombreEmpresa() : "N/A", fontCelda)));

                StringBuilder detalleArticulos = new StringBuilder();
                for (DetalleCompraModel d : c.getDetalles()) {
                    detalleArticulos.append(d.getCantidad()).append("x ")
                            .append(d.getBicicleta().getMarca()).append(" ")
                            .append(d.getBicicleta().getModelo())
                            .append(" (Costo: ").append(formatoMoneda(d.getPrecioUnitario())).append(" c/u)\n");
                }
                PdfPCell cellDetalle = new PdfPCell(new Phrase(detalleArticulos.toString(), fontCelda));
                table.addCell(cellDetalle);

                PdfPCell cellTotal = new PdfPCell(new Phrase(formatoMoneda(c.getTotal()), fontCeldaBold));
                cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cellTotal);

                totalPeriodo = totalPeriodo.add(c.getTotal() != null ? c.getTotal() : BigDecimal.ZERO);
            }
            document.add(table);

            Paragraph total = new Paragraph("TOTAL EGRESOS DEL PERIODO: " + formatoMoneda(totalPeriodo),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLACK));
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(15f);
            document.add(total);

            document.close();
        } catch (DocumentException e) {
            logger.error("Error al generar reporte de compras detallado: {}", e.getMessage(), e);
        }
        return out.toByteArray();
    }

    // =========================================================================
    // 4. NUEVO: REPORTE DE GANANCIAS NETAS (UTILIDAD POR ARTÍCULO)
    // =========================================================================
    public byte[] generarReporteGananciasDetallado(List<VentaModel> ventas, LocalDateTime inicio, LocalDateTime fin) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.LETTER.rotate(), 30, 30, 40, 40);

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            agregarEncabezadoReporte(document, "REPORTE DE UTILIDAD Y GANANCIA NETA", inicio, fin);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1f, 1.5f, 3f, 1f, 1.5f, 1.5f, 1.5f});

            String[] encabezados = {"ID Venta", "Fecha", "Bicicleta Vendida", "Cant.", "Costo (U)", "Venta (U)", "Ganancia Total"};
            for (String header : encabezados) {
                PdfPCell cell = new PdfPCell(new Phrase(header, fontEncabezado));
                cell.setBackgroundColor(new Color(16, 185, 129)); // Verde ganancia
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6f);
                table.addCell(cell);
            }

            BigDecimal gananciaNetaPeriodo = BigDecimal.ZERO;

            for (VentaModel v : ventas) {
                for (DetalleVentaModel d : v.getDetalles()) {
                    table.addCell(new PdfPCell(new Phrase("V-" + v.getIdVenta(), fontCelda)));
                    table.addCell(new PdfPCell(new Phrase(v.getFecha().format(formatterFechaHora), fontCelda)));
                    table.addCell(new PdfPCell(new Phrase(d.getBicicleta().getMarca() + " " + d.getBicicleta().getModelo(), fontCelda)));

                    PdfPCell cellCant = new PdfPCell(new Phrase(String.valueOf(d.getCantidad()), fontCelda));
                    cellCant.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellCant);

                    // Cálculos de ganancia
                    BigDecimal costoUnidad = d.getBicicleta().getPrecioCompra() != null ? d.getBicicleta().getPrecioCompra() : BigDecimal.ZERO;
                    BigDecimal ventaUnidad = d.getPrecioUnitario() != null ? d.getPrecioUnitario() : BigDecimal.ZERO;
                    BigDecimal gananciaUnidad = ventaUnidad.subtract(costoUnidad);
                    BigDecimal gananciaTotalLinea = gananciaUnidad.multiply(new BigDecimal(d.getCantidad()));

                    table.addCell(new PdfPCell(new Phrase(formatoMoneda(costoUnidad), fontCelda)));
                    table.addCell(new PdfPCell(new Phrase(formatoMoneda(ventaUnidad), fontCelda)));

                    PdfPCell cellGanancia = new PdfPCell(new Phrase(formatoMoneda(gananciaTotalLinea), fontCeldaBold));
                    cellGanancia.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.addCell(cellGanancia);

                    gananciaNetaPeriodo = gananciaNetaPeriodo.add(gananciaTotalLinea);
                }
            }
            document.add(table);

            Paragraph total = new Paragraph("GANANCIA NETA DEL PERIODO: " + formatoMoneda(gananciaNetaPeriodo),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new Color(16, 185, 129)));
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(15f);
            document.add(total);

            document.close();
        } catch (DocumentException e) {
            logger.error("Error al generar reporte de ganancias detallado: {}", e.getMessage(), e);
        }
        return out.toByteArray();
    }

    // =========================================================================
    // 5. REPORTE FINANCIERO GENERAL (Dashboard Snapshot) MEJORADO
    // =========================================================================
    public byte[] generarReporteFinanciero(Map<String, Object> metrics) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.LETTER, 40, 40, 50, 50);

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            agregarEncabezadoReporte(document, "RESUMEN EJECUTIVO RYDEX", null, null);

            // SECCIÓN 1: FLUJO DE CAJA
            Paragraph sec1 = new Paragraph("1. FLUJO DE CAJA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.DARK_GRAY));
            sec1.setSpacingAfter(10f);
            document.add(sec1);

            PdfPTable tableCaja = new PdfPTable(2);
            tableCaja.setWidthPercentage(100);
            tableCaja.setWidths(new float[]{2f, 1f});

            agregarFila(tableCaja, "Ingresos Brutos (Ventas Totales)", formatoMoneda((BigDecimal) metrics.get("ingresosTotales")), new Color(245, 245, 245));
            agregarFila(tableCaja, "Egresos Totales (Pagos a Proveedores)", formatoMoneda((BigDecimal) metrics.get("egresosTotales")), Color.WHITE);
            agregarFilaResaltada(tableCaja, "UTILIDAD / GANANCIA NETA REAL", formatoMoneda((BigDecimal) metrics.get("gananciaNeta")), new Color(16, 185, 129));
            document.add(tableCaja);

            document.add(new Paragraph(" "));

            // SECCIÓN 2: OPERACIONES E INVENTARIO
            Paragraph sec2 = new Paragraph("2. ESTADO OPERATIVO E INVENTARIO", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.DARK_GRAY));
            sec2.setSpacingAfter(10f);
            document.add(sec2);

            PdfPTable tableOps = new PdfPTable(2);
            tableOps.setWidthPercentage(100);
            tableOps.setWidths(new float[]{2f, 1f});

            agregarFila(tableOps, "Volumen Total de Ventas Registradas", metrics.get("ventasHoy").toString() + " transacciones", new Color(245, 245, 245));
            agregarFila(tableOps, "Catálogo Activo de Bicicletas", metrics.get("totalBicicletas").toString() + " modelos", Color.WHITE);

            // Resaltar en naranja si hay alertas
            String stockAlerta = metrics.get("stockBajo").toString() + " modelos requieren atención";
            Color colorStock = metrics.get("stockBajo").toString().equals("0") ? new Color(16, 185, 129) : new Color(239, 68, 68);
            agregarFilaResaltada(tableOps, "Alertas de Stock (Bajo Mínimo)", stockAlerta, colorStock);
            document.add(tableOps);

            // FOOTER CONFIDENCIAL
            Paragraph footer = new Paragraph("\n\nEste documento financiero es de carácter confidencial y de uso exclusivo de la gerencia de Rydex.",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, Color.GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
        } catch (DocumentException e) {
            logger.error("Error al generar reporte financiero: {}", e.getMessage(), e);
        }
        return out.toByteArray();
    }

    private void agregarFila(PdfPTable table, String desc, String valor, Color bgColor) {
        PdfPCell c1 = new PdfPCell(new Phrase(desc, fontCelda));
        c1.setBackgroundColor(bgColor); c1.setPadding(8f);
        PdfPCell c2 = new PdfPCell(new Phrase(valor, fontCelda));
        c2.setBackgroundColor(bgColor); c2.setPadding(8f); c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1); table.addCell(c2);
    }

    private void agregarFilaResaltada(PdfPTable table, String desc, String valor, Color colorTexto) {
        Font fontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, colorTexto);
        PdfPCell c1 = new PdfPCell(new Phrase(desc, fontBold));
        c1.setPadding(10f);
        PdfPCell c2 = new PdfPCell(new Phrase(valor, fontBold));
        c2.setPadding(10f); c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1); table.addCell(c2);
    }

}