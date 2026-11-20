import java.util.LinkedHashSet;
import java.util.Set;

/*
 * Descripcion: Traduce el codigo intermedio (.tac) a codigo MIPS.
 * Entrada: Texto del codigo intermedio.
 * Salida: Texto del codigo MIPS (.asm).
 * Restricciones: Paso 1: solo numeros enteros, copias y write.
 * Objetivo: Generar el codigo destino del compilador.
 */
public class GeneradorMIPS {

    /* Variables que van en la seccion .data */
    private final Set<String> variables = new LinkedHashSet<>();

    /* Instrucciones de la seccion .text */
    private final StringBuilder texto = new StringBuilder();

    /*
     * Descripcion: Traduce todo el codigo intermedio.
     * Entrada: Texto del .tac.
     * Salida: Texto del .asm.
     * Restricciones: Las lineas que no conoce quedan como comentario.
     */
    public String traducir(String codigoIntermedio) {

        texto.append("main:\n");

        for (String linea : codigoIntermedio.split("\n")) {
            traducirLinea(linea.trim());
        }

        /* Terminar el programa */
        instr("li $v0, 10");
        instr("syscall");

        return armarArchivo();
    }

    /*
     * Descripcion: Traduce una linea.
     * Entrada: Una linea del .tac.
     * Salida: Instrucciones MIPS.
     * Restricciones: Solo entiende "x = 5", "x = y" y "write x".
     */
    private void traducirLinea(String linea) {

        if (linea.isEmpty()) {
            return;
        }

        texto.append("    # ").append(linea).append("\n");

        /* write x */
        if (linea.startsWith("write ")) {
            String valor = linea.substring(6).trim();
            cargar(valor, "$a0");
            instr("li $v0, 1");      // 1 = imprimir entero
            instr("syscall");
            instr("li $a0, 10");     // 10 = cambio de linea
            instr("li $v0, 11");     // 11 = imprimir caracter
            instr("syscall");
            return;
        }

        /* destino = valor */
        if (linea.contains(" = ")) {
            String[] partes = linea.split(" = ");
            String destino = partes[0].trim();
            String valor = partes[1].trim();

            if (!valor.contains(" ")) {
                cargar(valor, "$t0");
                guardar("$t0", destino);
            }
        }
    }

    /* Pone un numero o una variable en un registro. */
    private void cargar(String valor, String registro) {
        if (valor.matches("-?\\d+")) {
            instr("li " + registro + ", " + valor);
        } else {
            variables.add(valor);
            instr("lw " + registro + ", g_" + valor);
        }
    }

    /* Guarda un registro en una variable. */
    private void guardar(String registro, String destino) {
        variables.add(destino);
        instr("sw " + registro + ", g_" + destino);
    }

    /* Agrega una instruccion con sangria. */
    private void instr(String instruccion) {
        texto.append("    ").append(instruccion).append("\n");
    }

    /* Junta .data y .text. */
    private String armarArchivo() {
        StringBuilder data = new StringBuilder(".data\n");
        for (String v : variables) {
            data.append("g_").append(v).append(": .word 0\n");
        }
        return data + "\n.text\n.globl main\n" + texto;
    }
}