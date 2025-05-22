package com.rentapeliculas.peliculas.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rentapeliculas.peliculas.model.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    private final PeliculaService peliculaService;

    @Autowired
    public PdfService(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    public ByteArrayInputStream generarCatalogoPeliculas() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font titleFont = com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Catálogo de Películas Disponibles", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Tabla
            PdfPTable table = new PdfPTable(5); // 5 columnas
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 3, 2, 1, 2});

            // Encabezados
            Font headerFont = com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA_BOLD, 12);
            com.itextpdf.text.BaseColor headerColor = new com.itextpdf.text.BaseColor(66, 139, 202);

            PdfPCell headerCell;
            String[] headers = {"ID", "Título", "Género", "Año", "Precio Renta"};
            
            for (String header : headers) {
                headerCell = new PdfPCell(new Phrase(header, headerFont));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(headerColor);
                headerCell.setPadding(5);
                table.addCell(headerCell);
            }

            // Datos
            Font dataFont = com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA, 10);
            List<Pelicula> peliculas = peliculaService.listarDisponibles();
            
            for (Pelicula pelicula : peliculas) {
                table.addCell(new Phrase(pelicula.getIdPelicula().toString(), dataFont));
                table.addCell(new Phrase(pelicula.getTitulo(), dataFont));
                table.addCell(new Phrase(pelicula.getGenero(), dataFont));
                table.addCell(new Phrase(pelicula.getAño().toString(), dataFont));
                table.addCell(new Phrase("$" + pelicula.getPrecioRenta().toString(), dataFont));
            }

            document.add(table);

            // Pie de página
            document.add(Chunk.NEWLINE);
            Font footerFont = com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA, 10);
            Paragraph footer = new Paragraph("Generado el: " + java.time.LocalDate.now().toString(), footerFont);
            footer.setAlignment(Element.ALIGN_RIGHT);
            document.add(footer);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}