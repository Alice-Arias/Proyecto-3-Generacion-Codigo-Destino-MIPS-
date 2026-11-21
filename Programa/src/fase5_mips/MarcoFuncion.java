import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Descripcion: Guarda donde vive cada parametro, variable local y temporal de una funcion.
 * Entrada: Los nombres y tipos que aparecen dentro de la funcion en el codigo intermedio.
 * Salida: La posicion de cada nombre en la pila, por ejemplo -8($fp).
 * Restricciones: Cada valor ocupa una palabra (4 bytes).
 * Objetivo: Que cada llamada tenga sus propias variables y asi funcione la recursion.
 *
 * Asi queda la pila de una funcion con n parametros:
 *
 *      parametro 0        $fp + 8 + 4*(n-1)
 *      ...
 *      parametro n-1      $fp + 8
 *      $ra guardado       $fp + 4
 *      $fp anterior       $fp + 0   <- $fp
 *      local 1            $fp - 4
 *      local 2            $fp - 8
 *      ...                          <- $sp
 */
public class MarcoFuncion {

    /* Tamano de una palabra en bytes */
    private static final int PALABRA = 4;

    /* Espacio que ocupan $ra y el $fp anterior, arriba de $fp */
    private static final int ESPACIO_GUARDADO = 8;

    /* Etiqueta de la funcion en el codigo intermedio, por ejemplo int$suma */
    private final String etiqueta;

    /* Parametros en el orden en que se declaran */
    private final List<String> parametros = new ArrayList<>();

    /* Cada nombre de la funcion y su tipo */
    private final Map<String, String> tipoPorNombre = new LinkedHashMap<>();

    /* Cada nombre de la funcion y su posicion desde $fp */
    private final Map<String, Integer> posicionPorNombre = new HashMap<>();

    /* Cada arreglo local y cuantos elementos tiene */
    private final Map<String, Integer> tamanoPorArreglo = new HashMap<>();

    /* Palabras que ocupan las variables locales y temporales */
    private int palabrasLocales = 0;

    /*
     * Descripcion: Crea el marco de una funcion.
     * Entrada: Etiqueta como int$suma.
     * Salida: Un marco vacio.
     * Restricciones: La etiqueta debe tener la forma tipo$nombre.
     * Objetivo: Empezar a anotar los datos de la funcion.
     */
    public MarcoFuncion(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /*
     * Descripcion: Da la etiqueta de la funcion.
     * Entrada: Ninguna.
     * Salida: Etiqueta como int$suma.
     * Restricciones: Ninguna.
     * Objetivo: Reconocer donde empieza y termina la funcion.
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /*
     * Descripcion: Dice si es el procedimiento principal.
     * Entrada: Ninguna.
     * Salida: true si es void$principal.
     * Restricciones: Ninguna.
     * Objetivo: Terminar el programa al salir de principal en vez de regresar.
     */
    public boolean esPrincipal() {
        return etiqueta.equals("void$principal");
    }

    /*
     * Descripcion: Anota un parametro.
     * Entrada: Nombre y tipo del parametro.
     * Salida: Ninguna.
     * Restricciones: Se deben anotar en el orden en que se declaran.
     * Objetivo: Saber donde quedan los argumentos que manda quien llama.
     */
    public void agregarParametro(String nombre, String tipo) {
        parametros.add(nombre);
        tipoPorNombre.put(nombre, tipo);
    }

    /*
     * Descripcion: Anota una variable local o un temporal.
     * Entrada: Nombre y tipo.
     * Salida: Ninguna.
     * Restricciones: Si ya estaba anotado, no se repite.
     * Objetivo: Reservarle un lugar en la pila.
     */
    public void agregarLocal(String nombre, String tipo) {
        tipoPorNombre.putIfAbsent(nombre, tipo);
    }

    /*
     * Descripcion: Anota un arreglo local con su tamano.
     * Entrada: Nombre, tipo y cantidad de elementos.
     * Salida: Ninguna.
     * Restricciones: Cada elemento ocupa una palabra.
     * Objetivo: Reservarle al arreglo todos sus elementos en la pila.
     */
    public void agregarArreglo(String nombre, String tipo, int tamano) {
        tipoPorNombre.putIfAbsent(nombre, tipo);
        tamanoPorArreglo.put(nombre, tamano);
    }

    /*
     * Descripcion: Dice si un nombre es un arreglo de esta funcion.
     * Entrada: Nombre.
     * Salida: true si es un arreglo local.
     * Restricciones: Ninguna.
     * Objetivo: Calcular la direccion de sus elementos.
     */
    public boolean esArreglo(String nombre) {
        return tamanoPorArreglo.containsKey(nombre);
    }

    /*
     * Descripcion: Da la posicion donde empieza un arreglo en la pila.
     * Entrada: Nombre del arreglo.
     * Salida: Numero como -16 (el elemento 0 esta en -16($fp)).
     * Restricciones: El arreglo debe pertenecer a la funcion.
     * Objetivo: Sumarle el indice para llegar a cada elemento.
     */
    public int inicioDeArreglo(String nombre) {
        return posicionPorNombre.get(nombre);
    }

    /*
     * Descripcion: Le da a cada nombre su posicion en la pila.
     * Entrada: Ninguna.
     * Salida: Ninguna.
     * Restricciones: Se llama una sola vez, cuando ya se anoto todo.
     * Objetivo: Dejar listas las posiciones antes de traducir la funcion.
     */
    public void calcularPosiciones() {

        int cantidadParametros = parametros.size();

        for (int i = 0; i < cantidadParametros; i++) {
            int posicion = ESPACIO_GUARDADO + PALABRA * (cantidadParametros - 1 - i);
            posicionPorNombre.put(parametros.get(i), posicion);
        }

        for (String nombre : tipoPorNombre.keySet()) {
            if (!posicionPorNombre.containsKey(nombre)) {
                /* Un arreglo ocupa una palabra por elemento; empieza en la direccion mas baja */
                palabrasLocales += tamanoPorArreglo.getOrDefault(nombre, 1);
                posicionPorNombre.put(nombre, -PALABRA * palabrasLocales);
            }
        }
    }

    /*
     * Descripcion: Dice si un nombre pertenece a esta funcion.
     * Entrada: Nombre.
     * Salida: true si es parametro, variable local o temporal de la funcion.
     * Restricciones: Ninguna.
     * Objetivo: Saber si buscarlo en la pila o en .data.
     */
    public boolean tiene(String nombre) {
        return tipoPorNombre.containsKey(nombre);
    }

    /*
     * Descripcion: Da el tipo de un nombre de la funcion.
     * Entrada: Nombre.
     * Salida: int, float, bool, char o string.
     * Restricciones: El nombre debe pertenecer a la funcion.
     * Objetivo: Elegir las instrucciones correctas para ese tipo.
     */
    public String tipoDe(String nombre) {
        return tipoPorNombre.get(nombre);
    }

    /*
     * Descripcion: Da donde esta un nombre en la pila.
     * Entrada: Nombre.
     * Salida: Texto como -8($fp) o 12($fp).
     * Restricciones: El nombre debe pertenecer a la funcion.
     * Objetivo: Usarlo directo en lw, sw, l.s y s.s.
     */
    public String ubicacion(String nombre) {
        return posicionPorNombre.get(nombre) + "($fp)";
    }

    /*
     * Descripcion: Da cuantos bytes ocupan las variables locales.
     * Entrada: Ninguna.
     * Salida: Cantidad de bytes.
     * Restricciones: Ninguna.
     * Objetivo: Reservar ese espacio al entrar a la funcion.
     */
    public int bytesLocales() {
        return PALABRA * palabrasLocales;
    }
}
