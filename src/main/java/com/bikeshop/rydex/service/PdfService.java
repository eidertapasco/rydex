package com.bikeshop.rydex.service;

import com.bikeshop.rydex.model.DetalleVentaModel;
import com.bikeshop.rydex.model.VentaModel;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.awt.Color;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String fechaStr = venta.getFecha() != null ? venta.getFecha().format(formatter) : "N/A";

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
            Font fontEncabezado = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.WHITE);
            for (String header : encabezados) {
                PdfPCell cell = new PdfPCell(new Phrase(header, fontEncabezado));
                cell.setBackgroundColor(new Color(255, 77, 0)); // El color naranja de Rydex
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8);
                table.addCell(cell);
            }

            // Filas de los productos
            Font fontCelda = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
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
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    // Generar Reporte Financiero para el Admin
    public byte[] generarReporteFinanciero(java.util.Map<String, Object> metrics) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.LETTER, 50, 50, 50, 50);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // 1. TÍTULO Y FECHA
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Color.BLACK);
            Paragraph titulo = new Paragraph("RYDEX - REPORTE FINANCIERO", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            Paragraph subtitulo = new Paragraph("Generado el: " + java.time.LocalDateTime.now().format(formatter),
                    FontFactory.getFont(FontFactory.HELVETICA, 12, Color.DARK_GRAY));
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(30f);
            document.add(subtitulo);

            // 2. TABLA DE MÉTRICAS FINANCIERAS
            PdfPTable table = new PdfPTable(2); // 2 columnas
            table.setWidthPercentage(80);
            table.setWidths(new float[]{2f, 2f});

            // Metodo auxiliar para no repetir código creando celdas
            Font fontLabel = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
            Font fontValue = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);

            // Encabezados de la tabla
            PdfPCell cellDesc = new PdfPCell(new Phrase("Descripción", fontLabel));
            cellDesc.setBackgroundColor(new Color(40, 40, 40));
            cellDesc.setPadding(10f);
            table.addCell(cellDesc);

            PdfPCell cellValor = new PdfPCell(new Phrase("Valor", fontLabel));
            cellValor.setBackgroundColor(new Color(40, 40, 40));
            cellValor.setPadding(10f);
            table.addCell(cellValor);

            // Filas de datos
            String[][] datos = {
                    {"Ingresos Totales (Ventas)", "$" + metrics.get("ingresosTotales").toString()},
                    {"Egresos Totales (Compras)", "$" + metrics.get("egresosTotales").toString()},
                    {"Ganancia Neta Real", "$" + metrics.get("gananciaNeta").toString()},
                    {"Total de Ventas Registradas", metrics.get("ventasHoy").toString()},
                    {"Modelos en Alerta de Stock", metrics.get("stockBajo").toString() + " de " + metrics.get("totalBicicletas").toString()}
            };

            for (String[] fila : datos) {
                PdfPCell celda1 = new PdfPCell(new Phrase(fila[0], FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.DARK_GRAY)));
                celda1.setPadding(10f);
                table.addCell(celda1);

                PdfPCell celda2 = new PdfPCell(new Phrase(fila[1], fontValue));
                celda2.setPadding(10f);
                // Si es la Ganancia Neta, la ponemos en verde
                if (fila[0].contains("Ganancia")) {
                    celda2.setPhrase(new Phrase(fila[1], FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, new Color(16, 185, 129))));
                }
                table.addCell(celda2);
            }

            document.add(table);

            // 3. MENSAJE FINAL
            Paragraph footer = new Paragraph("Este documento es confidencial y de uso exclusivo de la administración de Rydex.",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, Color.GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(40f);
            document.add(footer);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}