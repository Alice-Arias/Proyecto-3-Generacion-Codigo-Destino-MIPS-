import java.util.HashMap;
import java.util.Map;

/*
 * Descripcion: Traduce el codigo intermedio (.tac) a codigo MIPS.
 * Entrada: Texto del codigo intermedio.
 * Salida: Texto del codigo MIPS (.asm) listo para QtSpim.
 * Restricciones: Maneja int, float, string, char y bool, operaciones, if, ciclos y write.
 * Objetivo: Generar el codigo destino del compilador.
 */
public class GeneradorMIPS {

    /* Codigos de syscall: le dicen a MIPS que servicio usar */
    private static final int SYSCALL_IMPRIMIR_ENTERO = 1;
    private static final int SYSCALL_IMPRIMIR_FLOAT = 2;
    private static final int SYSCALL_IMPRIMIR_TEXTO = 4;
    private static final int SYSCALL_TERMINAR = 10;
    private static final int SYSCALL_IMPRIMIR_CARACTER = 11;

    /* Codigo ASCII del cambio de linea */
    private static final int CAMBIO_DE_LINEA = 10;

    /* Columna donde empieza el comentario de la derecha */
    private static final int COLUMNA_COMENTARIO = 36;

    /* Operador del intermedio y su instruccion MIPS con enteros */
    private static final Map<String, String> INSTRUCCION_ENTERA = new HashMap<>();

    static {
        INSTRUCCION_ENTERA.put("+", "add");
        INSTRUCCION_ENTERA.put("-", "sub");
        INSTRUCCION_ENTERA.put("*", "mul");
        INSTRUCCION_ENTERA.put("<", "slt");
        INSTRUCCION_ENTERA.put(">", "sgt");
        INSTRUCCION_ENTERA.put("<=", "sle");
        INSTRUCCION_ENTERA.put(">=", "sge");
        INSTRUCCION_ENTERA.put("==", "seq");
        INSTRUCCION_ENTERA.put("!=", "sne");
        INSTRUCCION_ENTERA.put("and", "and");
        INSTRUCCION_ENTERA.put("or", "or");
    }

    /* Operador del intermedio y su instruccion MIPS con float */
    private static final Map<String, String> INSTRUCCION_FLOAT = new HashMap<>();

    static {
        INSTRUCCION_FLOAT.put("+", "add.s");
        INSTRUCCION_FLOAT.put("-", "sub.s");
        INSTRUCCION_FLOAT.put("*", "mul.s");
        INSTRUCCION_FLOAT.put("/", "div.s");
    }

    /* Lo que va en la seccion .data */
    private final SeccionDatosMIPS datos = new SeccionDatosMIPS();

    /* Lo que va en la seccion .text */
    private final StringBuilder codigo = new StringBuilder();

    /* Numero para que las etiquetas nuevas no se repitan */
    private int contadorEtiquetas = 0;

    /* Linea del intermedio que se pone a la derecha de la siguiente instruccion */
    private String lineaPendiente = null;

    /*
     * ============================================================
     * TRADUCCION GENERAL
     * ============================================================
     */

    /*
     * Descripcion: Traduce todo el codigo intermedio.
     * Entrada: Texto del .tac.
     * Salida: Texto del .asm.
     * Restricciones: Se debe usar un generador nuevo por archivo.
     * Objetivo: Ser el punto de entrada de la Fase 5.
     */
    public String traducir(String codigoIntermedio) {

        codigo.append("main:\n");

        for (String linea : codigoIntermedio.split("\n")) {
            traducirLinea(linea.trim());
        }

        terminarPrograma();

        return datos.generar() + "\n.text\n.globl main\n" + codigo;
    }

    /*
     * Descripcion: Revisa que tipo de linea es y la manda a traducir.
     * Entrada: Una linea del .tac.
     * Salida: Ninguna.
     * Restricciones: Las lineas vacias se ignoran.
     * Objetivo: Repartir cada linea a quien sabe traducirla.
     */
    private void traducirLinea(String linea) {

        if (linea.isEmpty()) {
            return;
        }

        lineaPendiente = linea;

        if (linea.startsWith("declaracion# ") || linea.startsWith("declaracionPrevia# ")) {
            traducirDeclaracion(linea);
        } else if (linea.startsWith("write ")) {
            escribir(linea.substring("write ".length()).trim());
        } else if (linea.endsWith(":")) {
            ponerEtiqueta(convertirEtiqueta(linea.substring(0, linea.length() - 1)));
        } else if (linea.startsWith("goto ")) {
            instruccion("j " + convertirEtiqueta(linea.substring("goto ".length()).trim()));
        } else if (linea.startsWith("if ") && linea.contains(" goto ")) {
            traducirSaltoCondicional(linea);
        } else if (linea.contains(" = ")) {
            traducirAsignacion(linea);
        }
    }

    /*
     * Descripcion: Anota el tipo de una variable declarada.
     * Entrada: Linea como "declaracion# int x".
     * Salida: Ninguna.
     * Restricciones: No genera instrucciones.
     * Objetivo: Saber de que tipo es cada variable.
     */
    private void traducirDeclaracion(String linea) {
        String[] partes = linea.substring(linea.indexOf('#') + 1).trim().split("\\s+");
        datos.declararVariable(partes[1], partes[0]);
    }

    /*
     * Descripcion: Traduce un salto con condicion.
     * Entrada: Linea como "if bool5 goto L1".
     * Salida: Instrucciones MIPS.
     * Restricciones: Salta si la condicion vale 1.
     * Objetivo: Hacer funcionar if, while y for.
     */
    private void traducirSaltoCondicional(String linea) {

        int posicionGoto = linea.indexOf(" goto ");
        String condicion = linea.substring("if ".length(), posicionGoto).trim();
        String destino = linea.substring(posicionGoto + " goto ".length()).trim();

        cargarEntero(condicion, "$t0");
        instruccion("bnez $t0, " + convertirEtiqueta(destino));
    }

    /*
     * Descripcion: Traduce una asignacion y decide si es copia, not u operacion.
     * Entrada: Linea como "x = y", "x = not y" o "x = a + b".
     * Salida: Instrucciones MIPS.
     * Restricciones: Los textos y caracteres pueden tener espacios.
     * Objetivo: Repartir cada asignacion segun su forma.
     */
    private void traducirAsignacion(String linea) {

        int posicionIgual = linea.indexOf(" = ");
        String destino = linea.substring(0, posicionIgual).trim();
        String valor = linea.substring(posicionIgual + " = ".length()).trim();

        if (SeccionDatosMIPS.esTexto(valor) || SeccionDatosMIPS.esCaracter(valor)) {
            copiar(destino, valor);
            return;
        }

        String[] partes = valor.split(" ");

        if (partes.length == 1) {
            copiar(destino, valor);
        } else if (partes.length == 2 && partes[0].equals("not")) {
            negar(destino, partes[1]);
        } else if (partes.length == 3) {
            operar(destino, partes[0], partes[1], partes[2]);
        }
    }

    /*
     * ============================================================
     * ASIGNACIONES Y OPERACIONES
     * ============================================================
     */

    /*
     * Descripcion: Copia un valor en una variable.
     * Entrada: Destino y valor.
     * Salida: Instrucciones de cargar y guardar.
     * Restricciones: Si el destino es float usa registros $f.
     * Objetivo: Traducir "x = y".
     */
    private void copiar(String destino, String valor) {
        if (datos.esFloat(destino)) {
            cargarFloat(valor, "$f0");
            guardarFloat("$f0", destino);
        } else {
            cargarEntero(valor, "$t0");
            guardarEntero("$t0", destino);
        }
    }

    /*
     * Descripcion: Cambia un booleano por su contrario.
     * Entrada: Destino y valor.
     * Salida: Instrucciones MIPS.
     * Restricciones: El valor debe ser 0 o 1.
     * Objetivo: Traducir "x = not y".
     */
    private void negar(String destino, String valor) {
        cargarEntero(valor, "$t0");
        instruccion("xori $t0, $t0, 1");
        guardarEntero("$t0", destino);
    }

    /*
     * Descripcion: Decide si una operacion es con enteros o con float.
     * Entrada: Destino, operando izquierdo, operador y operando derecho.
     * Salida: Instrucciones MIPS.
     * Restricciones: Si un operando es float, todo se hace en float.
     * Objetivo: Traducir "x = a op b".
     */
    private void operar(String destino, String izquierdo, String operador, String derecho) {
        if (datos.esFloat(izquierdo) || datos.esFloat(derecho)) {
            operarFloat(destino, izquierdo, operador, derecho);
        } else {
            operarEnteros(destino, izquierdo, operador, derecho);
        }
    }

    /*
     * Descripcion: Hace una operacion con enteros.
     * Entrada: Destino, operandos y operador.
     * Salida: Instrucciones MIPS.
     * Restricciones: La division deja el cociente en mflo y el residuo en mfhi.
     * Objetivo: Traducir + - * / // mod pot, comparaciones, and y or.
     */
    private void operarEnteros(String destino, String izquierdo, String operador, String derecho) {

        if (operador.equals("pot")) {
            potenciaEntera(destino, izquierdo, derecho);
            return;
        }

        cargarEntero(izquierdo, "$t0");
        cargarEntero(derecho, "$t1");

        if (operador.equals("/") || operador.equals("//")) {
            instruccion("div $t0, $t1");
            instruccion("mflo $t2");
        } else if (operador.equals("mod")) {
            instruccion("div $t0, $t1");
            instruccion("mfhi $t2");
        } else if (INSTRUCCION_ENTERA.containsKey(operador)) {
            instruccion(INSTRUCCION_ENTERA.get(operador) + " $t2, $t0, $t1");
        } else {
            instruccion("# OPERADOR NO TRADUCIDO TODAVIA");
            return;
        }

        guardarEntero("$t2", destino);
    }

    /*
     * Descripcion: Hace una operacion con float.
     * Entrada: Destino, operandos y operador.
     * Salida: Instrucciones MIPS con registros $f.
     * Restricciones: Las comparaciones dan 1 o 0.
     * Objetivo: Traducir operaciones y comparaciones con decimales.
     */
    private void operarFloat(String destino, String izquierdo, String operador, String derecho) {

        if (operador.equals("pot")) {
            potenciaFloat(destino, izquierdo, derecho);
            return;
        }

        cargarFloat(izquierdo, "$f0");
        cargarFloat(derecho, "$f2");

        if (INSTRUCCION_FLOAT.containsKey(operador)) {
            instruccion(INSTRUCCION_FLOAT.get(operador) + " $f4, $f0, $f2");
            guardarFloat("$f4", destino);
        } else {
            compararFloat(destino, operador);
        }
    }

    /*
     * Descripcion: Compara dos float que ya estan en $f0 y $f2.
     * Entrada: Destino y operador de comparacion.
     * Salida: Guarda 1 si se cumple y 0 si no.
     * Restricciones: MIPS solo tiene c.eq.s, c.lt.s y c.le.s; > y >= se hacen al
     * reves.
     * Objetivo: Traducir comparaciones con decimales.
     */
    private void compararFloat(String destino, String operador) {

        String saltarSi = "bc1t";

        switch (operador) {
            case "<":
                instruccion("c.lt.s $f0, $f2");
                break;
            case "<=":
                instruccion("c.le.s $f0, $f2");
                break;
            case ">":
                instruccion("c.lt.s $f2, $f0");
                break;
            case ">=":
                instruccion("c.le.s $f2, $f0");
                break;
            case "==":
                instruccion("c.eq.s $f0, $f2");
                break;
            case "!=":
                instruccion("c.eq.s $f0, $f2");
                saltarSi = "bc1f";
                break;
            default:
                instruccion("# OPERADOR FLOAT NO TRADUCIDO TODAVIA");
                return;
        }

        String listo = "cmp_listo_" + numeroNuevo();
        instruccion("li $t2, 1");
        instruccion(saltarSi + " " + listo);
        instruccion("li $t2, 0");
        ponerEtiqueta(listo);
        guardarEntero("$t2", destino);
    }

    /*
     * Descripcion: Calcula una potencia con enteros usando un ciclo.
     * Entrada: Destino, base y exponente.
     * Salida: Instrucciones MIPS con un ciclo.
     * Restricciones: Si el exponente es 0 o negativo, da 1.
     * Objetivo: Traducir "x = a pot b".
     */
    private void potenciaEntera(String destino, String base, String exponente) {

        int numero = numeroNuevo();
        String ciclo = "pot_ciclo_" + numero;
        String fin = "pot_fin_" + numero;

        cargarEntero(base, "$t0");
        cargarEntero(exponente, "$t1");
        instruccion("li $t2, 1");

        ponerEtiqueta(ciclo);
        instruccion("blez $t1, " + fin);
        instruccion("mul $t2, $t2, $t0");
        instruccion("addi $t1, $t1, -1");
        instruccion("j " + ciclo);

        ponerEtiqueta(fin);
        guardarEntero("$t2", destino);
    }

    /*
     * Descripcion: Calcula una potencia con base float usando un ciclo.
     * Entrada: Destino, base y exponente entero.
     * Salida: Instrucciones MIPS con un ciclo.
     * Restricciones: Si el exponente es 0 o negativo, da 1.0.
     * Objetivo: Traducir "x = a pot b" con decimales.
     */
    private void potenciaFloat(String destino, String base, String exponente) {

        int numero = numeroNuevo();
        String ciclo = "pot_ciclo_" + numero;
        String fin = "pot_fin_" + numero;

        cargarFloat(base, "$f0");
        cargarEntero(exponente, "$t1");
        cargarFloat("1.0", "$f4");

        ponerEtiqueta(ciclo);
        instruccion("blez $t1, " + fin);
        instruccion("mul.s $f4, $f4, $f0");
        instruccion("addi $t1, $t1, -1");
        instruccion("j " + ciclo);

        ponerEtiqueta(fin);
        guardarFloat("$f4", destino);
    }

    /*
     * ============================================================
     * ESCRITURA Y FIN DEL PROGRAMA
     * ============================================================
     */

    /*
     * Descripcion: Imprime un valor y un cambio de linea.
     * Entrada: El valor a imprimir.
     * Salida: Instrucciones syscall.
     * Restricciones: El servicio depende del tipo del valor.
     * Objetivo: Traducir "write x".
     */
    private void escribir(String valor) {

        switch (datos.tipoDe(valor)) {
            case "float":
                cargarFloat(valor, "$f12");
                llamarSistema(SYSCALL_IMPRIMIR_FLOAT);
                break;
            case "string":
                cargarEntero(valor, "$a0");
                llamarSistema(SYSCALL_IMPRIMIR_TEXTO);
                break;
            case "char":
                cargarEntero(valor, "$a0");
                llamarSistema(SYSCALL_IMPRIMIR_CARACTER);
                break;
            default:
                cargarEntero(valor, "$a0");
                llamarSistema(SYSCALL_IMPRIMIR_ENTERO);
        }

        instruccion("li $a0, " + CAMBIO_DE_LINEA);
        llamarSistema(SYSCALL_IMPRIMIR_CARACTER);
    }

    /*
     * Descripcion: Termina el programa.
     * Entrada: Ninguna.
     * Salida: Instrucciones de salida.
     * Restricciones: Va al final del codigo.
     * Objetivo: Que QtSpim termine bien la ejecucion.
     */
    private void terminarPrograma() {
        llamarSistema(SYSCALL_TERMINAR);
    }

    /*
     * Descripcion: Pide un servicio al sistema.
     * Entrada: Codigo del servicio.
     * Salida: Instrucciones li y syscall.
     * Restricciones: Los datos del servicio ya deben estar en sus registros.
     * Objetivo: No repetir el par li/syscall en todo el codigo.
     */
    private void llamarSistema(int servicio) {
        instruccion("li $v0, " + servicio);
        instruccion("syscall");
    }

    /*
     * ============================================================
     * CARGAR Y GUARDAR VALORES
     * ============================================================
     */

    /*
     * Descripcion: Pone un valor entero en un registro.
     * Entrada: Valor y registro.
     * Salida: Instruccion li, la o lw.
     * Restricciones: true = 1, false = 0, 'A' = 65, "texto" = su direccion.
     * Objetivo: Tener el valor listo para operar.
     */
    private void cargarEntero(String valor, String registro) {

        if (SeccionDatosMIPS.esBooleano(valor)) {
            instruccion("li " + registro + ", " + (valor.equals("true") ? 1 : 0));
        } else if (SeccionDatosMIPS.esCaracter(valor)) {
            instruccion("li " + registro + ", " + codigoAscii(valor));
        } else if (SeccionDatosMIPS.esTexto(valor)) {
            instruccion("la " + registro + ", " + datos.etiquetaDeTexto(valor));
        } else if (SeccionDatosMIPS.esNumeroEntero(valor)) {
            instruccion("li " + registro + ", " + valor);
        } else {
            datos.reservar(valor);
            instruccion("lw " + registro + ", " + datos.nombreEnMemoria(valor));
        }
    }

    /*
     * Descripcion: Guarda un registro entero en una variable.
     * Entrada: Registro y destino.
     * Salida: Instruccion sw.
     * Restricciones: Ninguna.
     * Objetivo: Dejar el resultado en memoria.
     */
    private void guardarEntero(String registro, String destino) {
        datos.reservar(destino);
        instruccion("sw " + registro + ", " + datos.nombreEnMemoria(destino));
    }

    /*
     * Descripcion: Pone un valor float en un registro $f.
     * Entrada: Valor y registro.
     * Salida: Instruccion l.s, o conversion si el valor es entero.
     * Restricciones: Los numeros float fijos se leen de .data.
     * Objetivo: Tener el valor listo para operar con decimales.
     */
    private void cargarFloat(String valor, String registro) {

        if (SeccionDatosMIPS.esNumeroFloat(valor)) {
            instruccion("l.s " + registro + ", " + datos.etiquetaDeNumero(valor));
        } else if (!datos.esFloat(valor)) {
            cargarEntero(valor, "$t3");
            instruccion("mtc1 $t3, " + registro);
            instruccion("cvt.s.w " + registro + ", " + registro);
        } else {
            datos.reservar(valor);
            instruccion("l.s " + registro + ", " + datos.nombreEnMemoria(valor));
        }
    }

    /*
     * Descripcion: Guarda un registro $f en una variable float.
     * Entrada: Registro y destino.
     * Salida: Instruccion s.s.
     * Restricciones: Ninguna.
     * Objetivo: Dejar el resultado con decimales en memoria.
     */
    private void guardarFloat(String registro, String destino) {
        datos.reservar(destino);
        instruccion("s.s " + registro + ", " + datos.nombreEnMemoria(destino));
    }

    /*
     * Descripcion: Da el codigo ASCII de un caracter.
     * Entrada: Caracter como 'A' o '\n'.
     * Salida: Numero, por ejemplo 65 para 'A'.
     * Restricciones: Acepta \n, \t y \0.
     * Objetivo: MIPS guarda los caracteres como numeros.
     */
    private int codigoAscii(String caracter) {

        String contenido = caracter.substring(1, caracter.length() - 1);

        if (contenido.startsWith("\\") && contenido.length() > 1) {
            switch (contenido.charAt(1)) {
                case 'n':
                    return 10;
                case 't':
                    return 9;
                case '0':
                    return 0;
                default:
                    return contenido.charAt(1);
            }
        }

        return contenido.isEmpty() ? 0 : contenido.charAt(0);
    }

    /*
     * ============================================================
     * ESCRIBIR EN EL .asm
     * ============================================================
     */

    /*
     * Descripcion: Agrega una instruccion al codigo.
     * Entrada: La instruccion.
     * Salida: Una linea en .text.
     * Restricciones: La linea del intermedio pendiente se pone a la derecha.
     * Objetivo: Que se vea de donde salio cada grupo de instrucciones.
     */
    private void instruccion(String texto) {

        String linea = "    " + texto;

        if (lineaPendiente != null) {
            linea = String.format("%-" + COLUMNA_COMENTARIO + "s# %s", linea, lineaPendiente);
            lineaPendiente = null;
        }

        codigo.append(linea).append("\n");
    }

    /*
     * Descripcion: Agrega una etiqueta con una linea en blanco antes.
     * Entrada: Nombre de la etiqueta.
     * Salida: La etiqueta en .text.
     * Restricciones: La etiqueta no lleva comentario.
     * Objetivo: Que se vea donde empieza cada bloque.
     */
    private void ponerEtiqueta(String nombre) {
        codigo.append("\n").append(nombre).append(":\n");
        lineaPendiente = null;
    }

    /*
     * Descripcion: Da un numero nuevo para las etiquetas del generador.
     * Entrada: Ninguna.
     * Salida: Un numero que no se ha usado (1, 2, 3...).
     * Restricciones: Ninguna.
     * Objetivo: Que las etiquetas de pot y de comparaciones no se repitan.
     */
    private int numeroNuevo() {
        contadorEtiquetas++;
        return contadorEtiquetas;
    }

    /*
     * Descripcion: Convierte una etiqueta del intermedio a MIPS.
     * Entrada: Etiqueta como principal$if$1$bloque1.
     * Salida: principal.if.1.bloque1
     * Restricciones: MIPS no acepta $ en las etiquetas.
     * Objetivo: Usar los mismos nombres del codigo intermedio.
     */
    private String convertirEtiqueta(String nombre) {
        return nombre.replace('$', '.');
    }
}
