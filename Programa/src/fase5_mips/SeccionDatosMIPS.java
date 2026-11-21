import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Descripcion: Guarda todo lo que va en la seccion .data del archivo MIPS.
 * Entrada: Variables, temporales, textos y numeros float que usa el programa.
 * Salida: El texto de la seccion .data.
 * Restricciones: Cada valor ocupa una palabra (4 bytes).
 * Objetivo: Separar el manejo de la memoria del resto de la traduccion.
 */
public class SeccionDatosMIPS {

    /* Prefijos de los nombres en memoria, para no chocar con instrucciones de MIPS */
    private static final String PREFIJO_VARIABLE = "var_";
    private static final String PREFIJO_TEMPORAL = "tmp_";
    private static final String PREFIJO_TEXTO = "txt_";
    private static final String PREFIJO_NUMERO = "num_";

    /* Tipos que pueden tener los temporales en su nombre (ej: float6) */
    private static final String[] TIPOS_DE_TEMPORAL = {"float", "string", "char", "bool", "int"};

    /* Nombre de cada variable o temporal y su tipo */
    private final Map<String, String> tipoPorNombre = new LinkedHashMap<>();

    /* Cada arreglo global y cuantos elementos tiene */
    private final Map<String, Integer> tamanoPorArreglo = new LinkedHashMap<>();

    /* Cada texto fijo ("hola") y su etiqueta (txt_1) */
    private final Map<String, String> etiquetaPorTexto = new LinkedHashMap<>();

    /* Cada numero float fijo (3.5) y su etiqueta (num_1) */
    private final Map<String, String> etiquetaPorNumero = new LinkedHashMap<>();

    /*
     * Descripcion: Anota una variable declarada con su tipo.
     * Entrada: Nombre y tipo de la variable.
     * Salida: Ninguna.
     * Restricciones: Si ya existia, se actualiza el tipo.
     * Objetivo: Saber el tipo de cada variable del programa.
     */
    public void declararVariable(String nombre, String tipo) {
        tipoPorNombre.put(nombre, tipo);
    }

    /*
     * Descripcion: Anota un arreglo global con su tamano.
     * Entrada: Nombre, tipo y cantidad de elementos.
     * Salida: Ninguna.
     * Restricciones: Cada elemento ocupa una palabra.
     * Objetivo: Reservar en .data el espacio de todos sus elementos.
     */
    public void declararArreglo(String nombre, String tipo, int tamano) {
        tipoPorNombre.put(nombre, tipo);
        tamanoPorArreglo.put(nombre, tamano);
    }

    /*
     * Descripcion: Reserva espacio para una variable o temporal que se usa.
     * Entrada: Nombre.
     * Salida: Ninguna.
     * Restricciones: Si ya tenia espacio, no hace nada.
     * Objetivo: Que todo lo que se usa tenga su lugar en .data.
     */
    public void reservar(String nombre) {
        tipoPorNombre.putIfAbsent(nombre, tipoDe(nombre));
    }

    /*
     * Descripcion: Dice el tipo de un valor.
     * Entrada: Numero, texto, caracter, booleano, variable o temporal.
     * Salida: int, float, bool, char o string.
     * Restricciones: Si no lo reconoce, dice int.
     * Objetivo: Elegir las instrucciones correctas para cada tipo.
     */
    public String tipoDe(String valor) {

        if (esNumeroEntero(valor)) return "int";
        if (esNumeroFloat(valor)) return "float";
        if (esBooleano(valor)) return "bool";
        if (esCaracter(valor)) return "char";
        if (esTexto(valor)) return "string";

        if (tipoPorNombre.containsKey(valor)) {
            return tipoPorNombre.get(valor);
        }

        String tipoTemporal = tipoDeTemporal(valor);

        return tipoTemporal != null ? tipoTemporal : "int";
    }

    /*
     * Descripcion: Da el tipo de un temporal segun su nombre.
     * Entrada: Nombre como float6 o int5.
     * Salida: El tipo (float, int...) o null si no es un temporal.
     * Restricciones: Los temporales siempre son un tipo seguido de un numero.
     * Objetivo: Saber el tipo de un temporal sin buscarlo en ningun lado.
     */
    public static String tipoDeTemporal(String nombre) {

        for (String tipo : TIPOS_DE_TEMPORAL) {
            if (nombre.matches(tipo + "\\d+")) {
                return tipo;
            }
        }

        return null;
    }

    /*
     * Descripcion: Dice si un valor es de tipo float.
     * Entrada: Valor.
     * Salida: true si es float.
     * Restricciones: Ninguna.
     * Objetivo: Saber cuando usar los registros $f.
     */
    public boolean esFloat(String valor) {
        return tipoDe(valor).equals("float");
    }

    /*
     * Descripcion: Da la etiqueta de un texto fijo y lo guarda si es nuevo.
     * Entrada: Texto con comillas, como "hola".
     * Salida: Etiqueta como txt_1.
     * Restricciones: Un mismo texto usa siempre la misma etiqueta.
     * Objetivo: Poner los textos en .data una sola vez.
     */
    public String etiquetaDeTexto(String texto) {
        return etiquetaPorTexto.computeIfAbsent(texto, t -> PREFIJO_TEXTO + (etiquetaPorTexto.size() + 1));
    }

    /*
     * Descripcion: Da la etiqueta de un numero float fijo y lo guarda si es nuevo.
     * Entrada: Numero como 3.5.
     * Salida: Etiqueta como num_1.
     * Restricciones: Un mismo numero usa siempre la misma etiqueta.
     * Objetivo: MIPS no carga floats directo, hay que tenerlos en .data.
     */
    public String etiquetaDeNumero(String numero) {
        return etiquetaPorNumero.computeIfAbsent(numero, n -> PREFIJO_NUMERO + (etiquetaPorNumero.size() + 1));
    }

    /*
     * Descripcion: Da el nombre con el que algo se guarda en memoria.
     * Entrada: Nombre de la variable o del temporal.
     * Salida: var_x para variables y tmp_int5 para temporales.
     * Restricciones: Ninguna.
     * Objetivo: Evitar choques con nombres de instrucciones (ej: una variable llamada b).
     */
    public String nombreEnMemoria(String nombre) {
        return (esTemporal(nombre) ? PREFIJO_TEMPORAL : PREFIJO_VARIABLE) + nombre;
    }

    /*
     * Descripcion: Arma la seccion .data completa.
     * Entrada: Ninguna.
     * Salida: Texto de .data ordenado en grupos.
     * Restricciones: Las variables float usan .float, las demas .word.
     * Objetivo: Reservar la memoria que necesita el programa.
     */
    public String generar() {

        StringBuilder variables = new StringBuilder();
        StringBuilder temporales = new StringBuilder();

        for (Map.Entry<String, String> entrada : tipoPorNombre.entrySet()) {

            String nombre = entrada.getKey();
            String espacio = entrada.getValue().equals("float") ? ".float 0.0" : ".word 0";

            /* Un arreglo reserva 4 bytes por elemento; .align 2 lo deja en una direccion multiplo de 4 */
            if (tamanoPorArreglo.containsKey(nombre)) {
                espacio = ".space " + (4 * tamanoPorArreglo.get(nombre));
            }

            String linea = (tamanoPorArreglo.containsKey(nombre) ? ".align 2\n" : "")
                    + nombreEnMemoria(nombre) + ": " + espacio + "\n";

            if (esTemporal(nombre)) {
                temporales.append(linea);
            } else {
                variables.append(linea);
            }
        }

        StringBuilder numeros = new StringBuilder();
        for (Map.Entry<String, String> entrada : etiquetaPorNumero.entrySet()) {
            numeros.append(entrada.getValue()).append(": .float ").append(entrada.getKey()).append("\n");
        }

        StringBuilder textos = new StringBuilder();
        for (Map.Entry<String, String> entrada : etiquetaPorTexto.entrySet()) {
            textos.append(entrada.getValue()).append(": .asciiz ").append(entrada.getKey()).append("\n");
        }

        StringBuilder datos = new StringBuilder(".data\n");

        agregarGrupo(datos, "variables del programa", variables);
        agregarGrupo(datos, "temporales del codigo intermedio", temporales);
        agregarGrupo(datos, "numeros float fijos", numeros);
        agregarGrupo(datos, "textos fijos", textos);

        return datos.toString();
    }

    /*
     * Descripcion: Agrega un grupo de .data con su titulo.
     * Entrada: Donde escribir, el titulo del grupo y su contenido.
     * Salida: Ninguna.
     * Restricciones: Si el grupo esta vacio, no se escribe nada (ni el titulo).
     * Objetivo: Que no salgan titulos vacios en el .asm.
     */
    private void agregarGrupo(StringBuilder datos, String titulo, StringBuilder contenido) {

        if (contenido.length() == 0) {
            return;
        }

        datos.append("\n# ---------- ").append(titulo).append(" ----------\n").append(contenido);
    }

    /*
     * Descripcion: Dice si un nombre es un temporal del codigo intermedio.
     * Entrada: Nombre.
     * Salida: true si es como int5, float6 o bool9.
     * Restricciones: Ninguna.
     * Objetivo: Separar los temporales de las variables del programa.
     */
    private boolean esTemporal(String nombre) {
        return tipoDeTemporal(nombre) != null;
    }

    /*
     * Descripcion: Dice si un valor es un numero entero escrito directo.
     * Entrada: Valor.
     * Salida: true si es como 5 o -3.
     * Restricciones: Ninguna.
     * Objetivo: Cargarlo con li en vez de buscarlo en memoria.
     */
    public static boolean esNumeroEntero(String valor) {
        return valor.matches("-?\\d+");
    }

    /*
     * Descripcion: Dice si un valor es un numero float escrito directo.
     * Entrada: Valor.
     * Salida: true si es como 3.5.
     * Restricciones: Ninguna.
     * Objetivo: Guardarlo en .data para poder cargarlo.
     */
    public static boolean esNumeroFloat(String valor) {
        return valor.matches("-?\\d+\\.\\d+");
    }

    /*
     * Descripcion: Dice si un valor es true o false.
     * Entrada: Valor.
     * Salida: true si es un booleano escrito directo.
     * Restricciones: Ninguna.
     * Objetivo: Cargarlo como 1 o 0.
     */
    public static boolean esBooleano(String valor) {
        return valor.equals("true") || valor.equals("false");
    }

    /*
     * Descripcion: Dice si un valor es un caracter escrito directo.
     * Entrada: Valor.
     * Salida: true si es como 'A'.
     * Restricciones: Ninguna.
     * Objetivo: Cargarlo como su codigo ASCII.
     */
    public static boolean esCaracter(String valor) {
        return valor.startsWith("'");
    }

    /*
     * Descripcion: Dice si un valor es un texto escrito directo.
     * Entrada: Valor.
     * Salida: true si es como "hola".
     * Restricciones: Ninguna.
     * Objetivo: Cargar la direccion del texto con la.
     */
    public static boolean esTexto(String valor) {
        return valor.startsWith("\"");
    }
}
