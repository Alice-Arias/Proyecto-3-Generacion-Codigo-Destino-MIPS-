import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/*
 * Descripcion: Nombres de los tokens del lenguaje.
 * Entrada: El numero de un token.
 * Salida: Su nombre interno (PLUS) o como se escribe en el lenguaje (+).
 * Restricciones: Los numeros salen de la clase sym que genera CUP.
 * Objetivo: Que los mensajes muestren los tokens de forma entendible.
 */
public class Tokens {

    /* Nombre interno del token -> como se escribe en el lenguaje */
    private static final Map<String, String> TEXTO_DEL_TOKEN = new HashMap<>();

    static {
        TEXTO_DEL_TOKEN.put("PLUS", "+");
        TEXTO_DEL_TOKEN.put("MINUS", "-");
        TEXTO_DEL_TOKEN.put("MULTIPLY", "*");
        TEXTO_DEL_TOKEN.put("ENTIREDIV", "//");
        TEXTO_DEL_TOKEN.put("FLOATDIV", "/");
        TEXTO_DEL_TOKEN.put("MOD", "mod");
        TEXTO_DEL_TOKEN.put("POT", "pot");
        TEXTO_DEL_TOKEN.put("INCREMENT", "++");
        TEXTO_DEL_TOKEN.put("DECREMENT", "--");
        TEXTO_DEL_TOKEN.put("LTE", "<=");
        TEXTO_DEL_TOKEN.put("GTE", ">=");
        TEXTO_DEL_TOKEN.put("EQUAL", "==");
        TEXTO_DEL_TOKEN.put("NEQ", "!=");
        TEXTO_DEL_TOKEN.put("LT", "<");
        TEXTO_DEL_TOKEN.put("GT", ">");
        TEXTO_DEL_TOKEN.put("AND", "λ");
        TEXTO_DEL_TOKEN.put("OR", "θ");
        TEXTO_DEL_TOKEN.put("NOT", "Σ");
        TEXTO_DEL_TOKEN.put("OPBLOCK", "¿:");
        TEXTO_DEL_TOKEN.put("CLBLOCK", ":?");
        TEXTO_DEL_TOKEN.put("PARENOP", "є:");
        TEXTO_DEL_TOKEN.put("PARENCL", ":э");
        TEXTO_DEL_TOKEN.put("OPBRACKET", "ʃ:");
        TEXTO_DEL_TOKEN.put("CLBRACKET", ":ʅ");
        TEXTO_DEL_TOKEN.put("TERMINATOR", "»");
        TEXTO_DEL_TOKEN.put("ASIGN", "Ͱ");
        TEXTO_DEL_TOKEN.put("COMA", ",");
        TEXTO_DEL_TOKEN.put("ID", "identificador");
        TEXTO_DEL_TOKEN.put("LITERAL_INT", "número entero");
        TEXTO_DEL_TOKEN.put("LITERAL_FLOAT", "número decimal");
        TEXTO_DEL_TOKEN.put("LITERAL_CHAR", "carácter");
        TEXTO_DEL_TOKEN.put("LITERAL_STRING", "cadena de texto");
        TEXTO_DEL_TOKEN.put("EOF", "fin de archivo");
    }

    /* Numero del token -> nombre interno (PLUS, ID...). Se arma una sola vez leyendo la clase sym */
    private static final Map<Integer, String> NOMBRE_DEL_TOKEN = cargarNombresDeTokens();

    /*
     * Descripcion: Arma la lista de nombres de los tokens.
     * Entrada: Ninguna (lee la clase sym).
     * Salida: Numero de cada token y su nombre.
     * Restricciones: Se hace una sola vez.
     * Objetivo: Poder mostrar el nombre de un token en los mensajes.
     */
    private static Map<Integer, String> cargarNombresDeTokens() {

        Map<Integer, String> nombres = new HashMap<>();

        for (Field campo : sym.class.getFields()) {

            boolean esConstante = campo.getType() == int.class && Modifier.isStatic(campo.getModifiers());

            if (esConstante) {
                try {
                    nombres.put(campo.getInt(null), campo.getName());
                } catch (IllegalAccessException e) {
                    /* Un campo que no se puede leer no es un token */
                }
            }
        }

        return nombres;
    }

    /*
     * Descripcion: Busca el nombre de un token.
     * Entrada: Numero del token.
     * Salida: Nombre del token, por ejemplo PLUS.
     * Restricciones: Si no existe, devuelve DESCONOCIDO.
     * Objetivo: Mostrar el nombre del token en errores y archivos.
     */
    static String nombreToken(int numero) {
        return NOMBRE_DEL_TOKEN.getOrDefault(numero, "DESCONOCIDO");
    }

    /*
     * Descripcion: Dice como se escribe un token en el lenguaje.
     * Entrada: Nombre del token.
     * Salida: El simbolo, por ejemplo "+" para PLUS.
     * Restricciones: Si no esta en la lista, lo devuelve en minuscula.
     * Objetivo: Que los mensajes muestren el simbolo real del lenguaje.
     */
    static String textoLegible(String nombreToken) {
        return TEXTO_DEL_TOKEN.getOrDefault(nombreToken, nombreToken.toLowerCase());
    }
}
