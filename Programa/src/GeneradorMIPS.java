import java.util.LinkedHashSet;
import java.util.Set;

/*
 * Descripcion: Traduce el codigo intermedio (.tac) a codigo MIPS.
 * Entrada: Texto del codigo intermedio.
 * Salida: Texto del codigo MIPS (.asm).
 * Restricciones: Paso 2: enteros, copias, write y operaciones aritmeticas.
 * Objetivo: Generar el codigo destino del compilador.
 */
public class GeneradorMIPS {

    /* Variables que van en la seccion .data */
    private final Set<String> variables = new LinkedHashSet<>();

    /* Instrucciones de la seccion .text */
    private final StringBuilder texto = new StringBuilder();

    /* Para crear etiquetas que no se repitan (ciclo de pot) */
    private int contadorEtiquetas = 0;

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
     * Restricciones: Entiende "x = 5", "x = y", "x = a op b" y "write x".
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

            /* NUEVO: separar el valor para saber si es copia u operacion */
            String[] operacion = valor.split(" ");

            if (operacion.length == 1) {
                /* copia: x = y  o  x = 5 */
                cargar(valor, "$t0");
                guardar("$t0", destino);

            } else if (operacion.length == 3) {
                /* NUEVO: operacion: x = a + b */
                aritmetica(destino, operacion[0], operacion[1], operacion[2]);
            }
        }
    }

    /*
     * NUEVO
     * Descripcion: Traduce una operacion con enteros: d = a op b.
     * Entrada: Destino, operando izquierdo, operador y operando derecho.
     * Salida: Instrucciones MIPS.
     * Restricciones: div deja el cociente en LO (mflo) y el residuo en HI (mfhi).
     */
    private void aritmetica(String destino, String a, String operador, String b) {

        if (operador.equals("pot")) {
            potencia(destino, a, b);
            return;
        }

        cargar(a, "$t0");
        cargar(b, "$t1");

        switch (operador) {
            case "+":
                instr("add $t2, $t0, $t1");
                break;
            case "-":
                instr("sub $t2, $t0, $t1");
                break;
            case "*":
                instr("mul $t2, $t0, $t1");
                break;
            case "/":
            case "//":
                instr("div $t0, $t1");
                instr("mflo $t2");       // cociente
                break;
            case "mod":
                instr("div $t0, $t1");
                instr("mfhi $t2");       // residuo
                break;
            default:
                texto.append("    # OPERADOR NO TRADUCIDO TODAVIA\n");
                return;
        }

        guardar("$t2", destino);
    }

    /*
     * NUEVO
     * Descripcion: Traduce la potencia: d = a pot b.
     * Entrada: Destino, base y exponente.
     * Salida: Un ciclo que multiplica la base b veces.
     * Restricciones: Si el exponente es 0 o negativo, el resultado es 1.
     */
    private void potencia(String destino, String base, String exponente) {

        contadorEtiquetas++;
        String ciclo = "pot_ciclo_" + contadorEtiquetas;
        String fin = "pot_fin_" + contadorEtiquetas;

        cargar(base, "$t0");
        cargar(exponente, "$t1");
        instr("li $t2, 1");              // resultado = 1

        texto.append(ciclo).append(":\n");
        instr("blez $t1, " + fin);       // si exponente <= 0, terminar
        instr("mul $t2, $t2, $t0");      // resultado = resultado * base
        instr("addi $t1, $t1, -1");      // exponente = exponente - 1
        instr("j " + ciclo);

        texto.append(fin).append(":\n");
        guardar("$t2", destino);
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