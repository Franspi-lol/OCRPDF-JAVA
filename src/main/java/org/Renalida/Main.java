package org.Renalida;


import java.util.Map;

import static org.Renalida.LectorResultadosLaboratorio.extraerDatos;

public class Main {
    public static void main(String[] args) {
        // Reemplaza esta ruta con la ubicación real de tu archivo PDF
        String rutaDelArchivo = "src/labotest1.pdf";

        Map<String, Double> datosLimpios = extraerDatos(rutaDelArchivo);

        if (datosLimpios != null && !datosLimpios.isEmpty()) {
            System.out.println("\n--- Resultados Limpios y Estructurados ---");
            for (Map.Entry<String, Double> entry : datosLimpios.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } else {
            System.out.println("No se encontraron datos válidos o hubo un error en el procesamiento.");
        }
    }
}