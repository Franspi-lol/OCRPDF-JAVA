package org.Renalida;


import java.util.Map;

import static org.Renalida.LectorResultadosLaboratorio.extraerDatos;

public class Main {
    public static void main(String[] args) {

        String rutaDelArchivo = "src/labotest1.pdf";
        String rutaDelArchivo1 = "src/labbcorp1.pdf";
        String rutaDelArchivo2 = "src/labo2.pdf";
        String rutaDelArchivo3 = "src/labo4.pdf";

        Map<String, Double> datosLimpios = extraerDatos(rutaDelArchivo3);

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