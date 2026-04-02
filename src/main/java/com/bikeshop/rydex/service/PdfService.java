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

            // 2. DATOS DE LA VENTA Y CLIENTE
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String fechaStr = venta.getFecha() != null ? venta.getFecha().format(formatter) : "N/A";

            Font fontDatos = FontFactory.getFont(FontFactory.HELVETICA, 11, Color.BLACK);
            document.add(new Paragraph("Factura #: V-" + venta.getIdVenta(), fontDatos));
            document.add(new Paragraph("Fecha: " + fechaStr, fontDatos));
            document.add(new Paragraph("Cliente: " + venta.getCliente().getNombre(), fontDatos));
            document.add(new Paragraph("Email: " + venta.getCliente().getEmail(), fontDatos));
            document.add(new Paragraph(" ", fontDatos)); // Espacio en blanco

            // 3. TABLA DE PRODUCTOS
            PdfPTable table = new PdfPTable(4); // 4 columnas
            table.setWidthPercentage(100);
            table.setWidths(new float[]{4f, 1f, 2f, 2f}); // Proporción de las columnas

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
            total.setSpacingBefore(15f); // <-- CORREGIDO AQUÍ
            document.add(total);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}
