/*
 * Descripcion: Un elemento de arreglo del codigo intermedio, como a[i] o a[2].
 * Entrada: El texto del elemento.
 * Salida: El nombre del arreglo y su indice por separado.
 * Restricciones: El indice es un numero, una variable o un temporal (nunca una operacion).
 * Objetivo: Reconocer y separar los elementos de arreglo en un solo lugar.
 */
public class ElementoArreglo {

    final String arreglo;
    final String indice;

    /*
     * Descripcion: Crea un elemento ya separado.
     * Entrada: Nombre del arreglo e indice.
     * Salida: El elemento.
     * Restricciones: Ninguna.
     * Objetivo: Guardar las dos partes del elemento.
     */
    private ElementoArreglo(String arreglo, String indice) {
        this.arreglo = arreglo;
        this.indice = indice;
    }

    /*
     * Descripcion: Dice si un texto es un elemento de arreglo.
     * Entrada: Texto como a[i] o x.
     * Salida: true si tiene la forma nombre[indice].
     * Restricciones: Ninguna.
     * Objetivo: Saber cuando hay que calcular una direccion.
     */
    public static boolean es(String texto) {
        return texto.matches("[A-Za-z_][A-Za-z0-9_]*\\[.+\\]");
    }

    /*
     * Descripcion: Separa un elemento en arreglo e indice.
     * Entrada: Texto como edades[int5].
     * Salida: arreglo = edades, indice = int5.
     * Restricciones: El texto debe tener la forma nombre[indice].
     * Objetivo: Usar cada parte por separado.
     */
    public static ElementoArreglo leer(String texto) {
        int corchete = texto.indexOf('[');
        return new ElementoArreglo(texto.substring(0, corchete), texto.substring(corchete + 1, texto.length() - 1).trim());
    }
}
