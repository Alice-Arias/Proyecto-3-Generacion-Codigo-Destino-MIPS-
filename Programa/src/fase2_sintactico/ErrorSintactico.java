import java.util.ArrayList;
import java.util.List;

/*
 * Descripcion: Los datos de un error sintactico.
 * Entrada: Ninguna.
 * Salida: Linea, columna, token y lo que se esperaba.
 * Restricciones: Solo guarda datos.
 * Objetivo: Mostrar y corregir los errores sintacticos.
 */
public class ErrorSintactico {
    int linea;
    int columna;
    boolean alFinalDelArchivo;
    String token;
    String lexema;
    List<String> esperados = new ArrayList<>();

    /* Si el parser lo corrigio por su cuenta: con que tecnica y que dice (null en un error normal) */
    String tecnica;
    String mensaje;

    /* Para la correccion global: posicion del token en el flujo y numeros de los tokens esperados */
    int posicionEnFlujo = -1;
    List<Integer> numerosEsperados = new ArrayList<>();

    /*
     * Descripcion: Dice si el error se recupero con modo panico.
     * Entrada: Ninguna.
     * Salida: true si el error no tiene mensaje propio.
     * Restricciones: Ninguna.
     * Objetivo: Saber como mostrar cada error sintactico.
     */
    boolean esDeModoPanico() {
        return mensaje == null;
    }
}
