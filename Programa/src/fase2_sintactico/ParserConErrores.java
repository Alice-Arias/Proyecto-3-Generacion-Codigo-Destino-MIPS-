import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java_cup.runtime.Symbol;

/*
 * Descripcion: Parser que guarda los errores en vez de imprimirlos.
 * Entrada: Tokens del Lexer o de una lista.
 * Salida: Errores sintacticos y si el analisis se interrumpio.
 * Restricciones: Extiende el parser que genera CUP.
 * Objetivo: Que todos los errores se muestren con el mismo formato.
 */
public class ParserConErrores extends parser {

    final List<ErrorSintactico> errores = new ArrayList<>();

    /* true si el parser no pudo recuperarse de un error y se detuvo */
    boolean interrumpido = false;

    /* Ultimo token que no es fin de archivo, para ubicar errores al final */
    private Symbol ultimoTokenReal = null;

    /* Todos los tokens que el parser leyo, en orden (la correccion global los vuelve a analizar) */
    final List<Symbol> flujo = new ArrayList<>();

    /*
     * Descripcion: Crea el parser.
     * Entrada: De donde salen los tokens.
     * Salida: El parser listo.
     * Restricciones: Ninguna.
     * Objetivo: Analizar el programa guardando los errores.
     */
    ParserConErrores(java_cup.runtime.Scanner lexer) {
        super(lexer);
    }

    /*
     * Descripcion: Busca la posicion de un token.
     * Entrada: Token.
     * Salida: Su posicion, o -1 si no esta.
     * Restricciones: Ninguna.
     * Objetivo: Saber donde aplicar la correccion global.
     */
    private int posicionEnFlujo(Symbol token) {
        for (int i = flujo.size() - 1; i >= 0; i--) {
            if (flujo.get(i) == token) {
                return i;
            }
        }
        return -1;
    }

    /*
     * Descripcion: Dice cuantos tokens correctos hacen falta para seguir.
     * Entrada: Ninguna.
     * Salida: 1.
     * Restricciones: Ninguna.
     * Objetivo: Que el modo panico se recupere rapido.
     */
    @Override
    protected int error_sync_size() {
        return 1;
    }

    /*
     * Descripcion: Pide el siguiente token y lo guarda.
     * Entrada: Ninguna.
     * Salida: El token leido.
     * Restricciones: Recuerda el ultimo token real.
     * Objetivo: Guardar los tokens para la correccion global.
     */
    @Override
    public Symbol scan() throws Exception {

        Symbol token = super.scan();

        if (token != null) {
            flujo.add(token);

            if (token.sym != sym.EOF) {
                ultimoTokenReal = token;
            }
        }

        return token;
    }

    /*
     * Descripcion: Guarda un error de modo panico.
     * Entrada: Token donde fallo.
     * Salida: Error agregado a la lista.
     * Restricciones: Ninguna.
     * Objetivo: Guardar los errores de modo panico.
     */
    @Override
    public void syntax_error(Symbol token) {

        ErrorSintactico error = new ErrorSintactico();

        if (token != null) {

            error.alFinalDelArchivo = token.sym == sym.EOF;
            error.token = Tokens.nombreToken(token.sym);
            error.lexema = token.value == null ? null : String.valueOf(token.value);

            /* El Lexer entrega linea y columna desde 1 */
            if (token.left > 0) {
                error.linea = token.left;
                error.columna = token.right;
            } else if (error.alFinalDelArchivo && ultimoTokenReal != null) {
                error.linea = ultimoTokenReal.left;
                error.columna = 0;
            }
        }

        error.esperados = textosEsperados();
        error.posicionEnFlujo = posicionEnFlujo(token);

        try {
            for (int numero : expected_token_ids()) {
                error.numerosEsperados.add(numero);
            }
        } catch (Exception e) {
            /* Sin tokens esperados */
        }

        errores.add(error);
    }

    /*
     * Descripcion: Evita que CUP imprima mensajes.
     * Entrada: Mensaje e informacion.
     * Salida: Ninguna.
     * Restricciones: Main muestra los errores.
     * Objetivo: Que los errores se muestren solo con el formato del programa.
     */
    @Override
    public void report_error(String message, Object info) {
    }

    /*
     * Descripcion: Marca que el parser se rindio.
     * Entrada: Mensaje e informacion.
     * Salida: Ninguna.
     * Restricciones: Ninguna.
     * Objetivo: Saber cuando el parser no pudo seguir.
     */
    @Override
    public void report_fatal_error(String message, Object info) {
        interrumpido = true;
    }

    /*
     * Descripcion: Guarda un error comun que la gramatica reconoce.
     * Entrada: Linea, columna y mensaje.
     * Salida: Error agregado a la lista.
     * Restricciones: Ninguna.
     * Objetivo: Guardar errores que la gramatica ya conoce.
     */
    @Override
    public void errorProduccion(int linea, int columna, String mensaje) {

        ErrorSintactico error = new ErrorSintactico();

        error.linea = linea;
        error.columna = columna;
        error.token = "ERROR";
        error.tecnica = "Producción de error";
        error.mensaje = Character.toUpperCase(mensaje.charAt(0)) + mensaje.substring(1) + ".";

        errores.add(error);
    }

    /*
     * Descripcion: Guarda un simbolo que faltaba y que el parser agrego.
     * Entrada: Simbolo, linea y columna.
     * Salida: Error agregado a la lista.
     * Restricciones: Ninguna.
     * Objetivo: Guardar los errores de recuperacion a nivel de frase.
     */
    @Override
    public void tokenFaltante(String token, int linea, int columna) {

        ErrorSintactico error = new ErrorSintactico();

        error.linea = linea;
        error.columna = columna;
        error.tecnica = "Recuperación a nivel de frase";
        error.esperados.add(token);

        String queFalta;

        switch (token) {
            case ":э":
                error.token = "PARENCL";
                queFalta = "Falta el paréntesis de cierre :э.";
                break;
            case ":?":
                error.token = "CLBLOCK";
                queFalta = "Falta el cierre de bloque :? de la función.";
                break;
            default:
                error.token = "TERMINATOR";
                queFalta = "Falta el terminador » al final de la sentencia.";
        }

        error.mensaje = queFalta + " Se insertó y se siguió leyendo.";

        errores.add(error);
    }

    /*
     * Descripcion: Lista los tokens que se esperaban.
     * Entrada: Ninguna.
     * Salida: Lista de textos.
     * Restricciones: Maximo 10.
     * Objetivo: Decir que se esperaba en cada error.
     */
    private List<String> textosEsperados() {

        Set<String> textos = new LinkedHashSet<>();

        try {
            for (int numero : expected_token_ids()) {
                if (numero != sym.error) {
                    textos.add(Tokens.textoLegible(Tokens.nombreToken(numero)));
                }
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }

        List<String> lista = new ArrayList<>(textos);

        if (lista.size() > 10) {
            lista = new ArrayList<>(lista.subList(0, 10));
            lista.add("…");
        }

        return lista;
    }
}
