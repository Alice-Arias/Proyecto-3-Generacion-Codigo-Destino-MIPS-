import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Descripcion: Un error separado en linea, columna y detalle.
 * Entrada: El texto de un error lexico o semantico.
 * Salida: Sus partes por separado.
 * Restricciones: Si el texto no tiene el formato esperado, todo queda en el detalle.
 * Objetivo: Mostrar los errores en columnas ordenadas.
 */
public class PartesError {
    String linea = "-";
    String columna = "-";
    String detalle;

    /* Errores de la forma "Error léxico en línea 3, columna 5: detalle" (sirve para léxicos y semánticos) */
    private static final Pattern PATRON_ERROR = Pattern.compile("Error \\S+ en línea (\\d+), columna (\\d+): (.*)");

    /*
     * Descripcion: Separa un error en linea, columna y detalle.
     * Entrada: Texto del error.
     * Salida: Las partes del error.
     * Restricciones: Si no tiene el formato esperado, todo queda en el detalle.
     * Objetivo: Mostrar los errores en columnas ordenadas.
     */
    static PartesError separar(String error) {

        PartesError partes = new PartesError();

        String texto = error == null ? "" : error.trim().replace("\r", " ").replace("\n", " ");
        partes.detalle = texto;

        Matcher coincidencia = PATRON_ERROR.matcher(texto);

        if (coincidencia.matches()) {
            partes.linea = coincidencia.group(1);
            partes.columna = coincidencia.group(2);
            partes.detalle = coincidencia.group(3);
        }

        return partes;
    }
}
