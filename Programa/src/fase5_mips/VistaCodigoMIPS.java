import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/*
 * Descripcion: Muestra el codigo MIPS con colores. Cian: secciones; amarillo: etiquetas; negrita: instrucciones; verde: syscall; gris: comentarios.
 * Entrada: El codigo MIPS.
 * Salida: El codigo numerado en consola.
 * Restricciones: Usa pocos colores para que se lea facil.
 * Objetivo: Revisar el codigo MIPS desde la consola.
 */
public class VistaCodigoMIPS {

    /* Instrucciones que saltan a una etiqueta */
    private static final Set<String> MIPS_SALTOS = new LinkedHashSet<>(java.util.Arrays.asList(
            "j", "jal", "b", "beq", "bne", "bnez", "beqz", "blez", "bgtz", "bltz", "bgez",
            "bc1t", "bc1f"));

    /*
     * Descripcion: Muestra el codigo MIPS con numeros de linea y colores.
     * Entrada: Codigo MIPS.
     * Salida: El codigo en consola.
     * Restricciones: Usa pocos colores para que se lea facil.
     * Objetivo: Revisar el codigo MIPS desde la consola.
     */
    static void mostrar(String codigo) {

        String[] lineas = codigo.split("\n");
        int ancho = String.valueOf(lineas.length).length();

        for (int i = 0; i < lineas.length; i++) {
            String numero = Consola.pintar(Consola.GRIS, String.format("  %" + ancho + "d │", i + 1));
            System.out.println(numero + " " + colorearLineaMIPS(lineas[i]));
        }

        System.out.println();
    }

    /*
     * Descripcion: Le pone color a una linea de MIPS.
     * Entrada: Linea del .asm.
     * Salida: La linea con colores.
     * Restricciones: El comentario de la derecha (#) siempre va en gris.
     * Objetivo: Que el codigo MIPS se lea facil.
     */
    private static String colorearLineaMIPS(String linea) {

        String comentario = "";
        int gato = linea.indexOf('#');

        /* El # dentro de un texto ("...#...") no es comentario */
        if (gato >= 0 && !linea.substring(0, gato).contains("\"")) {
            comentario = Consola.pintar(Consola.GRIS, linea.substring(gato));
            linea = linea.substring(0, gato);
        }

        String limpia = linea.trim();

        if (limpia.isEmpty()) {
            return linea + comentario;
        }

        /* .data  .text  .globl main */
        if (limpia.startsWith(".")) {
            return Consola.pintar(Consola.CIAN, linea) + comentario;
        }

        /* etiqueta: main:  principal.if.1.bloque1: */
        if (limpia.endsWith(":") && !limpia.contains(" ")) {
            return Consola.pintar(Consola.AMARILLO, linea) + comentario;
        }

        /* dato en .data: var_x: .float 0.0  (solo la directiva lleva color) */
        int dosPuntos = limpia.indexOf(':');
        if (dosPuntos > 0 && limpia.substring(dosPuntos + 1).trim().startsWith(".")) {
            String nombre = limpia.substring(0, dosPuntos);
            String resto = limpia.substring(dosPuntos + 1).trim();
            int espacio = resto.indexOf(' ');
            String directiva = espacio > 0 ? resto.substring(0, espacio) : resto;
            String valor = espacio > 0 ? resto.substring(espacio) : "";

            return nombre + ": " + Consola.pintar(Consola.CIAN, directiva) + valor + comentario;
        }

        /* instruccion: el nombre en negrita; en un salto, la etiqueta en amarillo */
        int inicio = linea.length() - linea.stripLeading().length();
        String sangria = linea.substring(0, inicio);
        String[] partes = limpia.split("\\s+", 2);
        String instruccion = partes[0];
        String operandos = partes.length > 1 ? " " + partes[1] : "";
        String relleno = " ".repeat(Math.max(0, linea.length() - inicio - limpia.length()));

        if (MIPS_SALTOS.contains(instruccion) && !operandos.isEmpty()) {
            int coma = operandos.lastIndexOf(',');
            String antes = coma >= 0 ? operandos.substring(0, coma + 2) : " ";
            String destino = operandos.substring(antes.length()).trim();
            operandos = antes + Consola.pintar(Consola.AMARILLO, destino);
        }

        /* syscall en verde, para ver rapido donde se imprime, se lee o se termina */
        String color = instruccion.equals("syscall") ? Consola.VERDE : Consola.NEGRITA;

        return sangria + Consola.pintar(color, instruccion) + operandos + relleno + comentario;
    }
}
