import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/*
 * Descripcion: Muestra el codigo intermedio con colores. Amarillo: funciones; cian: etiquetas; verde: declaraciones; rojo: operadores.
 * Entrada: El codigo intermedio.
 * Salida: El codigo numerado en consola.
 * Restricciones: Los numeros de linea son los mismos del .tac.
 * Objetivo: Revisar el codigo intermedio desde la consola.
 */
public class VistaCodigoIntermedio {

    /* Las etiquetas de if, while y for tienen "$if$N$", "$while$N$" o "$for$N$" en el nombre */
    private static final Pattern PATRON_ETIQUETA_ESTRUCTURA = Pattern.compile("\\$(if|while|for)\\$\\d+\\$");

    private static final String SANGRIA_ETIQUETA = "  ";

    private static final String SANGRIA_INSTRUCCION = "    ";

    private static final Set<String> OPERADORES = new LinkedHashSet<>(java.util.Arrays.asList(
            "+", "-", "*", "//", "/", "mod", "pot",
            "<", ">", "<=", ">=", "==", "!=",
            "and", "or", "not"));

    /*
     * Descripcion: Dice si una linea es una etiqueta.
     * Entrada: Linea de codigo.
     * Salida: true si termina en ":" y no tiene espacios.
     * Restricciones: Ninguna.
     * Objetivo: Reconocer etiquetas en el codigo intermedio.
     */
    private static boolean esEtiqueta(String linea) {
        return linea.endsWith(":") && !linea.contains(" ");
    }

    /*
     * Descripcion: Dice si es una etiqueta de if, while o for.
     * Entrada: Linea de codigo.
     * Salida: true si tiene $if$, $while$ o $for$.
     * Restricciones: Ninguna.
     * Objetivo: Pintar distinto las etiquetas de if y ciclos.
     */
    private static boolean esEtiquetaDeEstructura(String linea) {
        return esEtiqueta(linea) && PATRON_ETIQUETA_ESTRUCTURA.matcher(linea).find();
    }

    /*
     * Descripcion: Dice si es la etiqueta de inicio de una funcion.
     * Entrada: Linea de codigo.
     * Salida: true si es etiqueta y no es de estructura.
     * Restricciones: Ninguna.
     * Objetivo: Separar visualmente cada funcion.
     */
    private static boolean esInicioDeFuncion(String linea) {
        return esEtiqueta(linea) && !PATRON_ETIQUETA_ESTRUCTURA.matcher(linea).find();
    }

    /*
     * Descripcion: Dice si es el fin de una funcion.
     * Entrada: Linea de codigo.
     * Salida: true si termina en $final y no lleva ":".
     * Restricciones: Ninguna.
     * Objetivo: Reconocer donde termina cada funcion.
     */
    private static boolean esFinDeFuncion(String linea) {
        return linea.endsWith("$final") && !linea.contains(" ") && !linea.endsWith(":");
    }

    /*
     * Descripcion: Dice si una linea es una declaracion.
     * Entrada: Linea de codigo.
     * Salida: true si empieza con declaracion#, declaracionPrevia# o param_.
     * Restricciones: Ninguna.
     * Objetivo: Pintar las declaraciones de otro color.
     */
    private static boolean esDeclaracion(String linea) {
        return linea.startsWith("declaracion#") || linea.startsWith("declaracionPrevia#") || linea.startsWith("param_");
    }

    /*
     * Descripcion: Cuenta las instrucciones del codigo intermedio.
     * Entrada: Codigo intermedio.
     * Salida: Cantidad de instrucciones.
     * Restricciones: No cuenta lineas vacias, etiquetas ni fines de funcion.
     * Objetivo: Mostrar cuantas instrucciones se generaron.
     */
    static int contarInstrucciones(String codigo) {

        int cantidad = 0;

        for (String linea : codigo.split("\n")) {
            if (!linea.isEmpty() && !esEtiqueta(linea) && !esFinDeFuncion(linea)) {
                cantidad++;
            }
        }

        return cantidad;
    }

    /*
     * Descripcion: Muestra el codigo intermedio con colores.
     * Entrada: Codigo intermedio.
     * Salida: Codigo numerado en consola.
     * Restricciones: Los numeros son los mismos del .tac.
     * Objetivo: Revisar el codigo intermedio desde la consola.
     */
    static void mostrar(String codigo) {

        Consola.mostrarSeccion("CÓDIGO INTERMEDIO");

        if (codigo.isEmpty()) {
            System.out.println(Consola.pintar(Consola.GRIS, "  (el generador todavía no produjo instrucciones)"));
            System.out.println();
            return;
        }

        System.out.println("  " + Consola.pintar(Consola.AMARILLO, "funciones") + Consola.pintar(Consola.GRIS, "  ·  ")
                + Consola.pintar(Consola.CIAN, "etiquetas y saltos") + Consola.pintar(Consola.GRIS, "  ·  ")
                + Consola.pintar(Consola.VERDE_SUAVE, "declaraciones") + Consola.pintar(Consola.GRIS, "  ·  ")
                + Consola.pintar(Consola.ROJO_SUAVE, "operadores"));
        System.out.println();

        for (String linea : formatearCodigoIntermedio(codigo)) {
            System.out.println(linea);
        }

        System.out.println();
    }

    /*
     * Descripcion: Arma el codigo con numeros, sangria y separadores.
     * Entrada: Codigo intermedio.
     * Salida: Lista de lineas listas para mostrar.
     * Restricciones: Ninguna.
     * Objetivo: Que el codigo intermedio se lea ordenado.
     */
    private static List<String> formatearCodigoIntermedio(String codigo) {

        List<String> resultado = new ArrayList<>();

        if (codigo.isEmpty()) {
            return resultado;
        }

        String[] lineas = codigo.split("\n");
        int anchoNumero = String.valueOf(lineas.length).length();
        String margen = "  " + " ".repeat(anchoNumero);

        boolean dentroDeFuncion = false;

        for (int i = 0; i < lineas.length; i++) {

            String linea = lineas[i];
            String texto;

            if (esInicioDeFuncion(linea)) {
                resultado.add(Consola.pintar(Consola.GRIS, margen + " ├" + "─".repeat(50)));
                texto = Consola.pintar(Consola.AMARILLO, linea);
                dentroDeFuncion = true;

            } else if (esEtiquetaDeEstructura(linea)) {
                texto = SANGRIA_ETIQUETA + Consola.pintar(Consola.CIAN, linea);

            } else if (esFinDeFuncion(linea)) {
                texto = Consola.pintar(Consola.AMARILLO_SUAVE, linea);
                dentroDeFuncion = false;

            } else if (linea.isEmpty()) {
                texto = "";

            } else {
                texto = (dentroDeFuncion ? SANGRIA_INSTRUCCION : "") + colorearInstruccion(linea);
            }

            String numero = Consola.pintar(Consola.GRIS, String.format("  %" + anchoNumero + "d │", i + 1));

            resultado.add(texto.isEmpty() ? numero : numero + " " + texto);
        }

        return resultado;
    }

    /*
     * Descripcion: Le pone color a una instruccion.
     * Entrada: Linea de codigo.
     * Salida: La linea con colores.
     * Restricciones: El color depende del tipo de instruccion.
     * Objetivo: Que cada tipo de instruccion se distinga.
     */
    private static String colorearInstruccion(String linea) {

        /* declaracion# int x,  declaracionPrevia# int x  y  param_int x */
        if (esDeclaracion(linea)) {
            return Consola.pintar(Consola.VERDE_SUAVE, linea);
        }

        /* goto destino */
        if (linea.startsWith("goto ")) {
            return Consola.pintar(Consola.NEGRITA, "goto") + " " + Consola.pintar(Consola.CIAN_SUAVE, linea.substring("goto ".length()));
        }

        /* if condicion goto destino */
        if (linea.startsWith("if ") && linea.contains(" goto ")) {

            int posicionGoto = linea.indexOf(" goto ");
            String condicion = linea.substring("if ".length(), posicionGoto);
            String destino = linea.substring(posicionGoto + " goto ".length());

            return Consola.pintar(Consola.NEGRITA, "if") + " " + condicion + " "
                    + Consola.pintar(Consola.NEGRITA, "goto") + " " + Consola.pintar(Consola.CIAN_SUAVE, destino);
        }

        /* temporal = call funcion, n   y   call funcion, n */
        if (linea.startsWith("call ") || linea.contains(" = call ")) {

            int posicionCall = linea.indexOf("call ");
            String antes = linea.substring(0, posicionCall);
            String resto = linea.substring(posicionCall + "call ".length());

            int coma = resto.indexOf(',');
            String funcion = coma >= 0 ? resto.substring(0, coma) : resto;
            String despues = coma >= 0 ? resto.substring(coma) : "";

            return antes + Consola.pintar(Consola.NEGRITA, "call") + " " + Consola.pintar(Consola.AMARILLO_SUAVE, funcion) + despues;
        }

        /* return, param, read y write */
        for (String palabra : new String[] {"return", "param", "read", "write"}) {
            if (linea.equals(palabra) || linea.startsWith(palabra + " ")) {
                return Consola.pintar(Consola.NEGRITA, palabra) + linea.substring(palabra.length());
            }
        }

        /* Asignaciones y operaciones: solo el operador lleva color */
        return colorearOperador(linea);
    }

    /*
     * Descripcion: Pinta de rojo el operador de una operacion.
     * Entrada: Linea de codigo.
     * Salida: La linea con el operador en rojo.
     * Restricciones: No toca textos, caracteres ni asignaciones simples.
     * Objetivo: Resaltar la operacion de cada linea.
     */
    private static String colorearOperador(String linea) {

        int posicionIgual = linea.indexOf(" = ");

        if (posicionIgual < 0) {
            return linea;
        }

        String destino = linea.substring(0, posicionIgual);
        String valor = linea.substring(posicionIgual + " = ".length());

        /* Un texto o un caracter puede tener espacios adentro: no se toca */
        if (valor.startsWith("\"") || valor.startsWith("'")) {
            return linea;
        }

        String[] partes = valor.split(" ");

        if (partes.length == 3 && OPERADORES.contains(partes[1])) {
            return destino + " = " + partes[0] + " " + Consola.pintar(Consola.ROJO_SUAVE, partes[1]) + " " + partes[2];
        }

        if (partes.length == 2 && OPERADORES.contains(partes[0])) {
            return destino + " = " + Consola.pintar(Consola.ROJO_SUAVE, partes[0]) + " " + partes[1];
        }

        return linea;
    }
}
