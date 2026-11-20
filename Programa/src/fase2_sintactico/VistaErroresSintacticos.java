import java.util.ArrayList;
import java.util.List;

/*
 * Descripcion: Muestra cada error sintactico en un cuadro rojo.
 * Entrada: Un error sintactico y las lineas del archivo.
 * Salida: El cuadro del error en consola.
 * Restricciones: Ninguna.
 * Objetivo: Que los errores sintacticos se entiendan facil.
 */
public class VistaErroresSintacticos {

    /*
     * Descripcion: Muestra un error sintactico en un cuadro rojo.
     * Entrada: Numero del error, el error y las lineas del archivo.
     * Salida: Cuadro en consola.
     * Restricciones: Ninguna.
     * Objetivo: Que el error sintactico se entienda facil.
     */
    static void mostrar(int numero, ErrorSintactico error, List<String> lineasFuente) {

        int anchoInterno = Consola.ANCHO - 4;

        String ubicacion = error.linea > 0
                ? "línea " + error.linea + ", columna " + error.columna
                : "final del archivo";

        String titulo = " ERROR " + String.format("%02d", numero) + " · " + ubicacion + " ";

        System.out.println(Consola.pintar(Consola.ROJO_SUAVE, "┌─") + Consola.pintar(Consola.ROJO, titulo)
                + Consola.pintar(Consola.ROJO_SUAVE, "─".repeat(Math.max(1, Consola.ANCHO - 3 - titulo.length())) + "┐"));

        Consola.filaCuadro("", "", Consola.ROJO_SUAVE);

        if (error.linea >= 1 && error.linea <= lineasFuente.size()) {
            mostrarLineaConError(error, lineasFuente.get(error.linea - 1), anchoInterno);
            Consola.filaCuadro("", "", Consola.ROJO_SUAVE);
        }

        if (!error.esDeModoPanico()) {

            /* El parser lo corrigio por su cuenta: se muestra con que tecnica */
            mostrarDetalleError("Técnica", error.tecnica, Consola.CIAN_SUAVE);

            if (!error.esperados.isEmpty()) {
                mostrarDetalleError("Se esperaba", String.join(", ", error.esperados), Consola.VERDE_SUAVE);
            }

            mostrarDetalleError("Detalle", error.mensaje, Consola.AMARILLO);

        } else {

            mostrarLoEncontrado(error);

            if (!error.esperados.isEmpty()) {
                mostrarDetalleError("Se esperaba", resumirEsperados(error.esperados), Consola.VERDE_SUAVE);
            }

            if (error.alFinalDelArchivo) {
                mostrarDetalleError("Sugerencia", "Puede faltar un cierre de bloque o un terminador.", Consola.CIAN_SUAVE);
            }
        }

        Consola.filaCuadro("", "", Consola.ROJO_SUAVE);
        System.out.println(Consola.pintar(Consola.ROJO_SUAVE, "└" + "─".repeat(Consola.ANCHO - 2) + "┘"));
        System.out.println();
    }

    /*
     * Descripcion: Muestra la linea del error y marca el token con ^^^.
     * Entrada: Error, linea del codigo y ancho disponible.
     * Salida: Dos filas en el cuadro.
     * Restricciones: Si la linea es larga, muestra solo la zona del error.
     * Objetivo: Senalar en el codigo donde esta el error.
     */
    private static void mostrarLineaConError(ErrorSintactico error, String lineaFuente, int anchoInterno) {

        int anchoNumero = Math.max(3, String.valueOf(error.linea).length());

        String texto = lineaFuente.replace("\t", " ").replaceAll("\\s+$", "");

        int espacioDisponible = anchoInterno - anchoNumero - 3;
        int posicionError = Math.max(0, error.columna - 1);
        int inicioVisible = 0;

        /* Si la linea es muy larga, se muestra solo la zona del error */
        if (texto.length() > espacioDisponible) {
            inicioVisible = Math.max(0, Math.min(posicionError - espacioDisponible / 3,
                    texto.length() - espacioDisponible));
            texto = texto.substring(inicioVisible, Math.min(texto.length(), inicioVisible + espacioDisponible));
        }

        String numeroLinea = String.format("%" + anchoNumero + "d", error.linea);

        Consola.filaCuadro(numeroLinea + " │ " + texto, Consola.pintar(Consola.GRIS, numeroLinea + " │ ") + texto, Consola.ROJO_SUAVE);

        if (error.alFinalDelArchivo || error.columna <= 0) {
            return;
        }

        String lexema = error.lexema == null ? "" : error.lexema;

        int columnaVisible = Math.max(0, posicionError - inicioVisible);
        int largoMarca = Math.max(1, Math.min(Math.max(1, lexema.length()), espacioDisponible - columnaVisible));

        String guia = " ".repeat(anchoNumero) + " │ ";
        String marca = "^".repeat(largoMarca);

        Consola.filaCuadro(guia + " ".repeat(columnaVisible) + marca,
                Consola.pintar(Consola.GRIS, guia) + " ".repeat(columnaVisible) + Consola.pintar(Consola.ROJO, marca),
                Consola.ROJO_SUAVE);
    }

    /*
     * Descripcion: Muestra el token que se encontro.
     * Entrada: Error.
     * Salida: Fila "Se encontro" en el cuadro.
     * Restricciones: Corta los textos de mas de 40 caracteres.
     * Objetivo: Decir que token causo el error.
     */
    private static void mostrarLoEncontrado(ErrorSintactico error) {

        if (error.alFinalDelArchivo) {
            mostrarDetalleError("Se encontró", "fin de archivo", Consola.AMARILLO);
            return;
        }

        String lexema = error.lexema == null ? "" : error.lexema;

        if (lexema.length() > 40) {
            lexema = lexema.substring(0, 39) + "…";
        }

        String encontrado = "'" + lexema + "'";
        String token = "(" + error.token + ")";
        String prefijo = String.format("%-11s : ", "Se encontró");

        Consola.filaCuadro(prefijo + encontrado + "  " + token,
                Consola.pintar(Consola.NEGRITA, prefijo) + Consola.pintar(Consola.AMARILLO, encontrado) + "  " + Consola.pintar(Consola.GRIS, token),
                Consola.ROJO_SUAVE);
    }

    /*
     * Descripcion: Resume la lista de tokens esperados.
     * Entrada: Lista de tokens esperados.
     * Salida: Texto con maximo 6 opciones.
     * Restricciones: Si hay mas, termina en ", …".
     * Objetivo: Que la lista de esperados no sea muy larga.
     */
    private static String resumirEsperados(List<String> esperados) {

        List<String> lista = new ArrayList<>(esperados);
        boolean hayMas = lista.remove("…");

        if (lista.size() > 6) {
            lista = new ArrayList<>(lista.subList(0, 6));
            hayMas = true;
        }

        return String.join(", ", lista) + (hayMas ? ", …" : "");
    }

    /*
     * Descripcion: Muestra un dato dentro del cuadro de error.
     * Entrada: Etiqueta, valor y color.
     * Salida: Una o varias filas en el cuadro.
     * Restricciones: Si el valor es largo, sigue en la linea de abajo.
     * Objetivo: Mostrar cada dato del error en su fila.
     */
    private static void mostrarDetalleError(String etiqueta, String valor, String color) {

        String prefijo = String.format("%-11s : ", etiqueta);
        List<String> lineas = Consola.partirEnLineas(valor, Consola.ANCHO - 4 - prefijo.length());

        for (int i = 0; i < lineas.size(); i++) {

            String inicio = i == 0 ? prefijo : " ".repeat(prefijo.length());
            String inicioConColor = i == 0 ? Consola.pintar(Consola.NEGRITA, prefijo) : inicio;

            Consola.filaCuadro(inicio + lineas.get(i), inicioConColor + Consola.pintar(color, lineas.get(i)), Consola.ROJO_SUAVE);
        }
    }
}
