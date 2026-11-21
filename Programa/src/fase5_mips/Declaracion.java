/*
 * Descripcion: Una declaracion del codigo intermedio separada en tipo, nombre y tamano.
 * Entrada: Linea como "declaracion# int x" o "declaracionPrevia# int a[3]".
 * Salida: El tipo, el nombre y el tamano (0 si no es arreglo).
 * Restricciones: El tamano de un arreglo debe ser un numero.
 * Objetivo: Leer las declaraciones en un solo lugar.
 */
public class Declaracion {

    final String tipo;
    final String nombre;
    final int tamano;

    /*
     * Descripcion: Crea una declaracion ya separada.
     * Entrada: Tipo, nombre y tamano.
     * Salida: La declaracion.
     * Restricciones: Ninguna.
     * Objetivo: Guardar las partes de la declaracion.
     */
    private Declaracion(String tipo, String nombre, int tamano) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.tamano = tamano;
    }

    /*
     * Descripcion: Separa una linea de declaracion en sus partes.
     * Entrada: Linea como "declaracionPrevia# int a[3]".
     * Salida: tipo = int, nombre = a, tamano = 3.
     * Restricciones: La linea debe empezar con declaracion# o declaracionPrevia#.
     * Objetivo: No repetir esta lectura en cada clase.
     */
    public static Declaracion leer(String linea) {

        String[] partes = linea.substring(linea.indexOf('#') + 1).trim().split("\\s+", 2);
        String tipo = partes[0];
        String nombre = partes[1].replace(" ", "");
        int tamano = 0;

        int corchete = nombre.indexOf('[');
        if (corchete > 0) {
            tamano = Integer.parseInt(nombre.substring(corchete + 1, nombre.indexOf(']')));
            nombre = nombre.substring(0, corchete);
        }

        return new Declaracion(tipo, nombre, tamano);
    }

    /*
     * Descripcion: Dice si la declaracion es de un arreglo.
     * Entrada: Ninguna.
     * Salida: true si tiene tamano.
     * Restricciones: Ninguna.
     * Objetivo: Reservar todos los elementos del arreglo.
     */
    public boolean esArreglo() {
        return tamano > 0;
    }
}
