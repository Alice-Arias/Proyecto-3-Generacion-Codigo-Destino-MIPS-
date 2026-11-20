import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Descripcion: Muestra los errores semanticos, uno por linea.
 * Entrada: La lista de errores semanticos.
 * Salida: Una tabla en consola y un resumen por tipo.
 * Restricciones: Ninguna.
 * Objetivo: Que los errores semanticos se lean ordenados.
 */
public class VistaErroresSemanticos {

    /* Los errores semánticos empiezan con su validación: "[Aritmética] detalle" (grupo 1 = validación, grupo 2 = detalle) */
    private static final Pattern PATRON_VALIDACION = Pattern.compile("^\\[([^\\]]+)\\]\\s*(.*)$");

    /* Ancho maximo de la lista de errores semanticos (se ajusta a la descripcion mas larga) */
    private static final int ANCHO_MAXIMO_SEMANTICOS = 170;

    /* Largo del inicio de cada linea: Nº, LÍNEA, COL y VALIDACIÓN */
    private static final int ANCHO_PREFIJO_SEMANTICO = 37;

    /* Un error semantico listo para mostrar. */
    private static class FilaSemantica {
        String linea;
        String columna;
        String validacion;
        String detalle;
    }

    /*
     * Descripcion: Muestra los errores semanticos, uno por linea.
     * Entrada: Lista de errores.
     * Salida: Tabla en consola y resumen por tipo.
     * Restricciones: Ninguna.
     * Objetivo: Ver los errores semanticos de forma ordenada.
     */
    static void mostrar(List<String> errores) {

        List<FilaSemantica> filas = new ArrayList<>();
        int detalleMasLargo = 0;

        for (String error : errores) {

            PartesError partes = PartesError.separar(error);

            FilaSemantica fila = new FilaSemantica();
            fila.linea = partes.linea;
            fila.columna = partes.columna;
            fila.validacion = "Semántico";
            fila.detalle = partes.detalle;

            /* El error empieza con su tipo de validacion: [Aritmética] detalle */
            Matcher validacion = PATRON_VALIDACION.matcher(partes.detalle);

            if (validacion.matches()) {
                fila.validacion = validacion.group(1);
                fila.detalle = validacion.group(2);
            }

            filas.add(fila);
            detalleMasLargo = Math.max(detalleMasLargo, fila.detalle.length());
        }

        int ancho = Math.min(ANCHO_MAXIMO_SEMANTICOS, Math.max(100, ANCHO_PREFIJO_SEMANTICO + detalleMasLargo));

        System.out.println();
        System.out.println(Consola.pintar(Consola.CIAN, String.format("  %-4s %6s %5s  %-15s %s",
                "Nº", "LÍNEA", "COL", "VALIDACIÓN", "DESCRIPCIÓN")));
        System.out.println(Consola.pintar(Consola.GRIS, "  " + "─".repeat(ancho - 2)));

        /* Tipo de validacion -> cantidad de errores (en el orden en que aparecen) */
        Map<String, Integer> cantidadPorValidacion = new LinkedHashMap<>();

        int numero = 1;

        for (FilaSemantica fila : filas) {

            cantidadPorValidacion.merge(fila.validacion, 1, Integer::sum);

            String prefijoConColor = "  "
                    + Consola.pintar(Consola.GRIS, String.format("%-4s", String.format("%02d", numero++))) + " "
                    + Consola.pintar(Consola.AMARILLO_SUAVE, String.format("%6s", fila.linea)) + " "
                    + Consola.pintar(Consola.AMARILLO_SUAVE, String.format("%5s", fila.columna)) + "  "
                    + Consola.pintar(Consola.CIAN_SUAVE, String.format("%-15s", fila.validacion)) + " ";

            List<String> lineas = Consola.partirEnLineas(fila.detalle, ancho - ANCHO_PREFIJO_SEMANTICO);

            for (int i = 0; i < lineas.size(); i++) {
                String prefijo = i == 0 ? prefijoConColor : " ".repeat(ANCHO_PREFIJO_SEMANTICO);
                System.out.println(prefijo + lineas.get(i));
            }
        }

        System.out.println(Consola.pintar(Consola.GRIS, "  " + "─".repeat(ancho - 2)));

        mostrarResumenPorValidacion(cantidadPorValidacion, ancho);
    }

    /*
     * Descripcion: Muestra cuantos errores hay de cada tipo.
     * Entrada: Cantidad por tipo y ancho.
     * Salida: Linea "Por validacion" en consola.
     * Restricciones: Si no cabe, sigue en otra linea.
     * Objetivo: Ver que tipos de errores semanticos hubo.
     */
    private static void mostrarResumenPorValidacion(Map<String, Integer> cantidadPorValidacion, int ancho) {

        String inicio = "  Por validación: ";

        int largoLinea = inicio.length();
        StringBuilder linea = new StringBuilder("  " + Consola.pintar(Consola.NEGRITA, "Por validación: "));

        for (Map.Entry<String, Integer> entrada : cantidadPorValidacion.entrySet()) {

            String elemento = entrada.getKey() + " " + entrada.getValue();

            if (largoLinea + elemento.length() + 5 > ancho) {
                System.out.println(linea);
                linea = new StringBuilder("    ");
                largoLinea = 4;
            }

            linea.append(Consola.pintar(Consola.CIAN_SUAVE, entrada.getKey())).append(" ")
                 .append(Consola.pintar(Consola.AMARILLO, String.valueOf(entrada.getValue()))).append("   ");

            largoLinea += elemento.length() + 3;
        }

        System.out.println(linea);
    }
}
