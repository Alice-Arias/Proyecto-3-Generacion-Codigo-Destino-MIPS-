import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Descripcion: Recorre el codigo intermedio y arma el marco de cada funcion.
 * Entrada: Las lineas del codigo intermedio.
 * Salida: Cada funcion con sus parametros, variables locales y temporales.
 * Restricciones: No genera codigo MIPS, solo junta informacion.
 * Objetivo: Saber de antemano cuanto espacio necesita cada funcion en la pila.
 */
public class LectorDeFunciones {

    /* Tipos que puede devolver una funcion */
    private static final String[] TIPOS_DE_RETORNO = {"int", "float", "bool", "char", "string", "void"};

    /*
     * Descripcion: Lee todas las funciones del codigo intermedio.
     * Entrada: Lineas del codigo intermedio.
     * Salida: Cada etiqueta de funcion (int$suma) con su marco.
     * Restricciones: Una funcion va desde tipo$nombre: hasta tipo$nombre$final.
     * Objetivo: Ser la primera pasada antes de traducir.
     */
    public static Map<String, MarcoFuncion> leer(String[] lineas) {

        Map<String, MarcoFuncion> funciones = new LinkedHashMap<>();
        MarcoFuncion actual = null;

        for (String lineaOriginal : lineas) {

            String linea = lineaOriginal.trim();

            if (esInicioDeFuncion(linea)) {
                actual = new MarcoFuncion(etiquetaDeInicio(linea));
                funciones.put(actual.getEtiqueta(), actual);
            } else if (actual != null && esFinDe(actual, linea)) {
                actual.calcularPosiciones();
                actual = null;
            } else if (actual != null) {
                anotarNombre(actual, linea);
            }
        }

        return funciones;
    }

    /*
     * Descripcion: Anota el parametro, variable o temporal que aparece en una linea.
     * Entrada: Marco de la funcion y una linea de su codigo.
     * Salida: Ninguna.
     * Restricciones: Solo mira param_, declaraciones y temporales que reciben valor.
     * Objetivo: Juntar todos los nombres que necesitan lugar en la pila.
     */
    private static void anotarNombre(MarcoFuncion marco, String linea) {

        if (linea.startsWith("param_")) {
            String[] partes = linea.substring("param_".length()).split("\\s+");
            marco.agregarParametro(partes[1], partes[0]);

        } else if (linea.startsWith("declaracion# ") || linea.startsWith("declaracionPrevia# ")) {
            Declaracion declaracion = Declaracion.leer(linea);
            if (declaracion.esArreglo()) {
                marco.agregarArreglo(declaracion.nombre, declaracion.tipo, declaracion.tamano);
            } else {
                marco.agregarLocal(declaracion.nombre, declaracion.tipo);
            }

        } else if (linea.contains(" = ")) {
            String destino = linea.substring(0, linea.indexOf(" = ")).trim();
            String tipo = SeccionDatosMIPS.tipoDeTemporal(destino);
            if (tipo != null) {
                marco.agregarLocal(destino, tipo);
            }
        }
    }

    /*
     * Descripcion: Dice si una linea es el inicio de una funcion.
     * Entrada: Linea del codigo intermedio.
     * Salida: true si es como int$suma:
     * Restricciones: Las etiquetas de if y ciclos tienen mas de un $ y no cuentan.
     * Objetivo: Reconocer donde empieza cada funcion.
     */
    public static boolean esInicioDeFuncion(String linea) {

        if (!linea.endsWith(":")) {
            return false;
        }

        String[] partes = etiquetaDeInicio(linea).split("\\$");

        if (partes.length != 2) {
            return false;
        }

        for (String tipo : TIPOS_DE_RETORNO) {
            if (tipo.equals(partes[0])) {
                return true;
            }
        }

        return false;
    }

    /*
     * Descripcion: Dice si una linea es el fin de una funcion.
     * Entrada: Marco de la funcion y una linea.
     * Salida: true si es como int$suma$final.
     * Restricciones: Ninguna.
     * Objetivo: Reconocer donde termina cada funcion.
     */
    public static boolean esFinDe(MarcoFuncion marco, String linea) {
        return linea.equals(marco.getEtiqueta() + "$final");
    }

    /*
     * Descripcion: Quita los dos puntos del inicio de una funcion.
     * Entrada: Linea como int$suma:
     * Salida: int$suma
     * Restricciones: La linea debe terminar en dos puntos.
     * Objetivo: Obtener la etiqueta de la funcion.
     */
    public static String etiquetaDeInicio(String linea) {
        return linea.substring(0, linea.length() - 1);
    }
}
