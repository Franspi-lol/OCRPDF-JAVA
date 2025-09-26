package org.Renalida;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LectorResultadosLaboratorio {
    public static Map<String, Double> extraerDatos(String rutaPdf) {

        File archivoPdf = new File(rutaPdf);
        StringBuilder textoCompleto = new StringBuilder();
        Map<String, Double> resultados = new HashMap<>();

        try (PDDocument documento = PDDocument.load(archivoPdf)) {
            PDFRenderer pdfRenderer = new PDFRenderer(documento);
            Tesseract tesseract = new Tesseract();

            // === CORRECCIÓN APLICADA AQUÍ ===
            // Reemplaza esta ruta por la ubicación de tu carpeta "tessdata"
            tesseract.setDatapath("/usr/share/tesseract-ocr/5/tessdata/"); // Cambia esta línea

            tesseract.setLanguage("spa");

            System.out.println("Procesando PDF y aplicando OCR...");

            for (int i = 0; i < documento.getNumberOfPages(); ++i) {
                BufferedImage imagen = pdfRenderer.renderImageWithDPI(i, 300);
                String textoPagina = tesseract.doOCR(imagen);
                textoCompleto.append(textoPagina);
            }

        } catch (IOException | TesseractException e) {
            System.err.println("Error procesando el archivo: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        System.out.println("\n--- Texto extraído (en crudo) ---\n" + textoCompleto);

        // El resto del código para analizar el texto sigue igual...
        String patronRegex = "([A-Z\\s]+)\\s+([\\d.]+)";
        Pattern patron = Pattern.compile(patronRegex);
        Matcher matcher = patron.matcher(textoCompleto.toString());

        while (matcher.find()) {
            String nombreAnalisis = matcher.group(1).trim();
            String valorStr = matcher.group(2).trim();

            if (nombreAnalisis.length() > 3 && !nombreAnalisis.trim().isEmpty()) {
                try {
                    double valor = Double.parseDouble(valorStr);
                    resultados.put(nombreAnalisis, valor);
                } catch (NumberFormatException e) {
                    // Ignorar si el valor no es válido
                }
            }
        }
        return resultados;
    }
}
