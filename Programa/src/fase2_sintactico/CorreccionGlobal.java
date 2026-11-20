import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java_cup.runtime.Symbol;

/*
 * Descripcion: Busca los cambios minimos de tokens que quitan errores sintacticos.
 * Entrada: El parser que ya analizo el archivo.
 * Salida: Una sugerencia de correccion en consola.
 * Restricciones: No cambia el archivo. Maximo 6 ediciones.
 * Objetivo: Aplicar la tecnica de correccion global.
 */
public class CorreccionGlobal {

    /* Si el parser se rinde, su resultado cuenta como si tuviera muchisimos errores */
    private static final int PENALIZACION_INTERRUMPIDO = 1_000_000;

    /* Cuantas ediciones prueba como maximo la correccion global */
    private static final int MAXIMO_EDICIONES = 6;

    /* Los tres tipos de edicion de la correccion global. */
    private enum TipoEdicion { INSERTAR, REEMPLAZAR, ELIMINAR }

    /* Un cambio de un token y como queda el archivo despues. */
    private static class Edicion {
        TipoEdicion tipo;
        String tokenOriginal;      /* el token que se elimina, se reemplaza o antes del cual se inserta */
        String tokenNuevo;         /* el token que se inserta o que reemplaza (null al eliminar) */
        int linea;
        int columna;
        List<Symbol> tokensResultantes;
        int puntaje;               /* errores que quedan despues de la edicion */
    }

    /*
     * Descripcion: Le da al parser tokens de una lista en vez de un archivo.
     * Entrada: Lista de tokens.
     * Salida: Un token cada vez que el parser lo pide.
     * Restricciones: Se usa en la correccion global.
     * Objetivo: Guardar la lista que se va a entregar al parser.
     */
    private static class ListaDeTokens implements java_cup.runtime.Scanner {

        private final List<Symbol> tokens;
        private int posicion = 0;

        /*
         * Descripcion: Crea la lista de tokens para el parser.
         * Entrada: Lista de tokens.
         * Salida: La lista lista para usar.
         * Restricciones: La lista debe terminar en fin de archivo.
         * Objetivo: Guardar la lista que se va a entregar al parser.
         */
        ListaDeTokens(List<Symbol> tokens) {
            this.tokens = tokens;
        }

        /*
         * Descripcion: Entrega el siguiente token de la lista.
         * Entrada: Ninguna.
         * Salida: El siguiente token.
         * Restricciones: Al final repite el ultimo (fin de archivo).
         * Objetivo: Repetir el analisis con tokens cambiados.
         */
        @Override
        public Symbol next_token() {
            Symbol token = tokens.get(Math.min(posicion, tokens.size() - 1));
            posicion++;
            return new Symbol(token.sym, token.left, token.right, token.value);
        }
    }

    /*
     * Descripcion: Busca las menores ediciones que quitan errores sintacticos.
     * Entrada: El parser que ya analizo el archivo.
     * Salida: Sugerencia en consola.
     * Restricciones: Solo si hubo modo panico. Maximo 6 ediciones. No cambia el archivo.
     * Objetivo: Sugerir como arreglar los errores sintacticos.
     */
    static void sugerir(ParserConErrores analisisOriginal) {

        boolean hayErrorDeModoPanico = false;

        for (ErrorSintactico error : analisisOriginal.errores) {
            if (error.esDeModoPanico()) {
                hayErrorDeModoPanico = true;
            }
        }

        if (!hayErrorDeModoPanico) {
            return;
        }

        List<Symbol> tokens = tokensConFinDeArchivo(analisisOriginal.flujo);
        ParserConErrores analisisActual = analisisOriginal;
        int puntajeInicial = puntaje(analisisOriginal);
        List<Edicion> aplicadas = new ArrayList<>();

        for (int ronda = 0; ronda < MAXIMO_EDICIONES && puntaje(analisisActual) > 0; ronda++) {

            ErrorSintactico objetivo = primerErrorDeModoPanico(analisisActual);

            if (objetivo == null) {
                break;
            }

            Edicion mejor = null;

            for (Edicion candidata : edicionesPosibles(tokens, objetivo)) {

                candidata.puntaje = puntaje(analizarTokens(candidata.tokensResultantes));

                boolean mejora = candidata.puntaje < puntaje(analisisActual);

                if (mejora && (mejor == null || candidata.puntaje < mejor.puntaje)) {
                    mejor = candidata;
                }
            }

            if (mejor == null) {
                break;
            }

            aplicadas.add(mejor);
            tokens = mejor.tokensResultantes;
            analisisActual = analizarTokens(tokens);
        }

        if (!aplicadas.isEmpty()) {
            mostrarCuadroCorreccion(aplicadas, puntajeInicial, puntaje(analisisActual));
        }
    }

    /*
     * Descripcion: Busca el primer error de modo panico.
     * Entrada: Un analisis.
     * Salida: El error, o null si no hay.
     * Restricciones: Ninguna.
     * Objetivo: Saber que error corregir primero.
     */
    private static ErrorSintactico primerErrorDeModoPanico(ParserConErrores analisis) {

        for (ErrorSintactico error : analisis.errores) {
            if (error.esDeModoPanico() && error.posicionEnFlujo >= 0) {
                return error;
            }
        }

        return null;
    }

    /*
     * Descripcion: Muestra las ediciones sugeridas.
     * Entrada: Ediciones y errores antes y despues.
     * Salida: Cuadro cian en consola.
     * Restricciones: Ninguna.
     * Objetivo: Explicar la correccion sugerida.
     */
    private static void mostrarCuadroCorreccion(List<Edicion> aplicadas, int puntajeInicial, int puntajeFinal) {

        System.out.println(Consola.pintar(Consola.CIAN_SUAVE, "┌─ CORRECCIÓN GLOBAL (sugerencia) "
                + "─".repeat(Math.max(1, Consola.ANCHO - 35)) + "┐"));

        Consola.filaCuadro("", "", Consola.CIAN_SUAVE);

        List<String> parrafos = new ArrayList<>();

        parrafos.add("Cambios mínimos de un token que reducen los errores sintácticos.");
        parrafos.add("");

        int puntajeAnterior = puntajeInicial;
        int numero = 1;

        for (Edicion edicion : aplicadas) {

            parrafos.add(numero++ + ". " + describirEdicion(edicion)
                    + " (línea " + edicion.linea + ", columna " + edicion.columna + "): errores sintácticos "
                    + textoPuntaje(puntajeAnterior) + " - " + textoPuntaje(edicion.puntaje));

            puntajeAnterior = edicion.puntaje;
        }

        parrafos.add("");
        parrafos.add(puntajeFinal == 0
                ? "Con " + aplicadas.size() + " edición(es) el archivo queda sin errores sintácticos."
                : "Con " + aplicadas.size() + " edición(es) los errores sintácticos pasan de "
                        + textoPuntaje(puntajeInicial) + " a " + textoPuntaje(puntajeFinal) + ".");

        for (String parrafo : parrafos) {

            if (parrafo.isEmpty()) {
                Consola.filaCuadro("", "", Consola.CIAN_SUAVE);
                continue;
            }

            for (String linea : Consola.partirEnLineas(parrafo, Consola.ANCHO - 4)) {
                Consola.filaCuadro(linea, linea, Consola.CIAN_SUAVE);
            }
        }

        Consola.filaCuadro("", "", Consola.CIAN_SUAVE);
        System.out.println(Consola.pintar(Consola.CIAN_SUAVE, "└" + "─".repeat(Consola.ANCHO - 2) + "┘"));
        System.out.println();
    }

    /*
     * Descripcion: Explica una edicion en palabras.
     * Entrada: Edicion.
     * Salida: Texto como "Insertar '»' antes de 'x'".
     * Restricciones: Ninguna.
     * Objetivo: Explicar cada cambio de forma clara.
     */
    private static String describirEdicion(Edicion edicion) {
        switch (edicion.tipo) {
            case INSERTAR:
                return "Insertar '" + edicion.tokenNuevo + "' antes de '" + edicion.tokenOriginal + "'";
            case REEMPLAZAR:
                return "Reemplazar '" + edicion.tokenOriginal + "' por '" + edicion.tokenNuevo + "'";
            default:
                return "Eliminar '" + edicion.tokenOriginal + "'";
        }
    }

    /*
     * Descripcion: Mide que tan malo es un analisis.
     * Entrada: Un analisis.
     * Salida: Cantidad de errores.
     * Restricciones: Si el parser se rindio, suma una penalizacion muy grande.
     * Objetivo: Comparar que edicion arregla mas errores.
     */
    private static int puntaje(ParserConErrores analisis) {
        return analisis.interrumpido
                ? PENALIZACION_INTERRUMPIDO + analisis.errores.size()
                : analisis.errores.size();
    }

    /*
     * Descripcion: Convierte un puntaje en texto.
     * Entrada: Puntaje.
     * Salida: Texto con la cantidad de errores.
     * Restricciones: Agrega "(se interrumpe)" si el parser se rindio.
     * Objetivo: Mostrar el puntaje de forma clara.
     */
    private static String textoPuntaje(int puntaje) {
        return puntaje >= PENALIZACION_INTERRUMPIDO
                ? (puntaje - PENALIZACION_INTERRUMPIDO) + " (se interrumpe)"
                : String.valueOf(puntaje);
    }

    /*
     * Descripcion: Analiza una lista de tokens con un parser nuevo.
     * Entrada: Lista de tokens.
     * Salida: El analisis.
     * Restricciones: No imprime nada.
     * Objetivo: Probar si una edicion arregla errores.
     */
    private static ParserConErrores analizarTokens(List<Symbol> tokens) {

        PrintStream consola = Consola.silenciarConsola();

        ParserConErrores analisis = new ParserConErrores(new ListaDeTokens(tokens));

        try {
            analisis.parse();
        } catch (Exception e) {
            analisis.interrumpido = true;
        } finally {
            System.setOut(consola);
        }

        return analisis;
    }

    /*
     * Descripcion: Copia la lista de tokens.
     * Entrada: Lista original.
     * Salida: Copia que termina con fin de archivo.
     * Restricciones: Ninguna.
     * Objetivo: Que el parser siempre reciba el fin de archivo.
     */
    private static List<Symbol> tokensConFinDeArchivo(List<Symbol> original) {

        List<Symbol> copia = new ArrayList<>(original);

        boolean terminaEnEof = !copia.isEmpty() && copia.get(copia.size() - 1).sym == sym.EOF;

        if (!terminaEnEof) {
            Symbol ultimo = copia.isEmpty() ? null : copia.get(copia.size() - 1);
            int linea = ultimo == null ? 1 : ultimo.left;
            int columna = ultimo == null ? 1 : ultimo.right;
            copia.add(new Symbol(sym.EOF, linea, columna, null));
        }

        return copia;
    }

    /*
     * Descripcion: Dice si un token se puede usar en una sugerencia.
     * Entrada: Numero del token.
     * Salida: true si es un simbolo o palabra del lenguaje.
     * Restricciones: No inventa identificadores, numeros ni textos.
     * Objetivo: Sugerir solo cambios que tengan sentido.
     */
    private static boolean esTokenDeEstructura(int numero) {

        String nombre = Tokens.nombreToken(numero);

        return numero != sym.error
                && !nombre.equals("ID")
                && !nombre.startsWith("LITERAL_")
                && !nombre.equals("EOF")
                && !nombre.equals("DESCONOCIDO");
    }

    /*
     * Descripcion: Dice como se muestra un token en una sugerencia.
     * Entrada: Token.
     * Salida: Su texto.
     * Restricciones: Ninguna.
     * Objetivo: Mostrar el token como lo escribio el usuario.
     */
    private static String textoDelToken(Symbol token) {

        boolean tieneValor = token.sym != sym.EOF && token.value != null && !String.valueOf(token.value).isEmpty();

        if (tieneValor) {
            return String.valueOf(token.value);
        }

        return token.sym == sym.EOF ? "fin de archivo" : Tokens.textoLegible(Tokens.nombreToken(token.sym));
    }

    /*
     * Descripcion: Crea una edicion.
     * Entrada: Tipo, token, token nuevo y tokens resultantes.
     * Salida: La edicion.
     * Restricciones: Ninguna.
     * Objetivo: Crear ediciones sin repetir codigo.
     */
    private static Edicion nuevaEdicion(TipoEdicion tipo, Symbol token, String tokenNuevo, List<Symbol> tokens) {

        Edicion edicion = new Edicion();
        edicion.tipo = tipo;
        edicion.tokenOriginal = textoDelToken(token);
        edicion.tokenNuevo = tokenNuevo;
        edicion.linea = token.left;
        edicion.columna = token.right;
        edicion.tokensResultantes = tokens;
        return edicion;
    }

    /*
     * Descripcion: Lista las ediciones que se prueban en un error.
     * Entrada: Tokens y el error.
     * Salida: Ediciones: eliminar, insertar y reemplazar.
     * Restricciones: Si dos arreglan lo mismo, gana la primera.
     * Objetivo: Probar los cambios minimos posibles.
     */
    private static List<Edicion> edicionesPosibles(List<Symbol> tokens, ErrorSintactico error) {

        List<Edicion> ediciones = new ArrayList<>();

        int posicion = error.posicionEnFlujo;

        if (posicion < 0 || posicion >= tokens.size()) {
            return ediciones;
        }

        Symbol tokenDelError = tokens.get(posicion);

        /* Eliminar el token del error, o el anterior (por ejemplo, un operador de mas) */
        for (int i = posicion; i >= Math.max(0, posicion - 1); i--) {

            Symbol token = tokens.get(i);

            if (token.sym == sym.EOF) {
                continue;
            }

            List<Symbol> sinElToken = new ArrayList<>(tokens);
            sinElToken.remove(i);

            ediciones.add(nuevaEdicion(TipoEdicion.ELIMINAR, token, null, sinElToken));
        }

        Set<Integer> esperados = new LinkedHashSet<>(error.numerosEsperados);

        /* Insertar un token esperado justo antes del token del error */
        for (int numero : esperados) {

            if (!esTokenDeEstructura(numero)) {
                continue;
            }

            String texto = Tokens.textoLegible(Tokens.nombreToken(numero));

            List<Symbol> conElToken = new ArrayList<>(tokens);
            conElToken.add(posicion, new Symbol(numero, tokenDelError.left, tokenDelError.right, texto));

            ediciones.add(nuevaEdicion(TipoEdicion.INSERTAR, tokenDelError, texto, conElToken));
        }

        /* Reemplazar el token del error por uno esperado */
        if (tokenDelError.sym != sym.EOF) {

            for (int numero : esperados) {

                if (!esTokenDeEstructura(numero)) {
                    continue;
                }

                String texto = Tokens.textoLegible(Tokens.nombreToken(numero));

                List<Symbol> conReemplazo = new ArrayList<>(tokens);
                conReemplazo.set(posicion, new Symbol(numero, tokenDelError.left, tokenDelError.right, texto));

                ediciones.add(nuevaEdicion(TipoEdicion.REEMPLAZAR, tokenDelError, texto, conReemplazo));
            }
        }

        return ediciones;
    }
}
