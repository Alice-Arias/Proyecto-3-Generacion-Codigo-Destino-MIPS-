import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Descripcion: Traduce el codigo intermedio (.tac) a codigo MIPS.
 * Entrada: Texto del codigo intermedio.
 * Salida: Texto del codigo MIPS (.asm).
 * Restricciones: Paso 4: tipos int, float, string, char y bool,
 *                aritmetica, comparaciones, logicas, etiquetas y saltos.
 * Objetivo: Generar el codigo destino del compilador.
 */
public class GeneradorMIPS {

    /* Variables que van en .data, con su tipo (nombre -> tipo) */
    private final Map<String, String> variables = new LinkedHashMap<>();

    /* Textos fijos ("hola") y numeros float fijos (3.5) que van en .data */
    private final Map<String, String> textos = new LinkedHashMap<>();
    private final Map<String, String> flotantes = new LinkedHashMap<>();

    /* Instrucciones de la seccion .text */
    private final StringBuilder texto = new StringBuilder();

    /* Para crear etiquetas que no se repitan */
    private int contadorEtiquetas = 0;

    /* NUEVO: linea del codigo intermedio que se pone a la derecha de la primera instruccion */
    private String comentarioPendiente = null;

    /* NUEVO: columna donde empiezan los comentarios de la derecha */
    private static final int COLUMNA_COMENTARIO = 36;

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
     * Restricciones: Entiende declaraciones, copias, operaciones, not, write,
     *                etiquetas y saltos.
     */
    private void traducirLinea(String linea) {

        if (linea.isEmpty()) {
            return;
        }

        /* NUEVO: la linea del intermedio se pone a la derecha de su primera instruccion */
        comentarioPendiente = linea;

        /* declaraciones: anotar el tipo de la variable */
        if (linea.startsWith("declaracion# ") || linea.startsWith("declaracionPrevia# ")) {
            String[] partes = linea.substring(linea.indexOf('#') + 1).trim().split("\\s+");
            variables.put(partes[1], partes[0]);
            return;
        }

        /* write x */
        if (linea.startsWith("write ")) {
            escribir(linea.substring(6).trim());
            return;
        }

        /* etiqueta: principal$if$1$bloque1: */
        if (linea.endsWith(":")) {
            ponerEtiqueta(etiqueta(linea.substring(0, linea.length() - 1)));
            return;
        }

        /* salto sin condicion: goto L */
        if (linea.startsWith("goto ")) {
            instr("j " + etiqueta(linea.substring(5).trim()));
            return;
        }

        /* salto con condicion: if x goto L (salta si x es 1) */
        if (linea.startsWith("if ") && linea.contains(" goto ")) {
            String condicion = linea.substring(3, linea.indexOf(" goto ")).trim();
            String destino = linea.substring(linea.indexOf(" goto ") + 6).trim();
            cargar(condicion, "$t0");
            instr("bnez $t0, " + etiqueta(destino));
            return;
        }

        /* destino = valor */
        if (linea.contains(" = ")) {
            String destino = linea.substring(0, linea.indexOf(" = ")).trim();
            String valor = linea.substring(linea.indexOf(" = ") + 3).trim();

            /* NUEVO: un texto o un caracter puede tener espacios: es una copia */
            if (valor.startsWith("\"") || valor.startsWith("'")) {
                copiar(destino, valor);
                return;
            }

            String[] operacion = valor.split(" ");

            if (operacion.length == 1) {
                /* copia: x = y  o  x = 5 */
                copiar(destino, valor);

            } else if (operacion.length == 2 && operacion[0].equals("not")) {
                /* x = not y  (cambia 1 por 0 y 0 por 1) */
                cargar(operacion[1], "$t0");
                instr("xori $t0, $t0, 1");
                guardar("$t0", destino);

            } else if (operacion.length == 3) {
                /* operacion: x = a + b */
                boolean hayFloat = tipoDe(operacion[0]).equals("float")
                        || tipoDe(operacion[2]).equals("float");

                if (hayFloat) {
                    operacionFloat(destino, operacion[0], operacion[1], operacion[2]);
                } else {
                    aritmetica(destino, operacion[0], operacion[1], operacion[2]);
                }
            }
        }
    }

    /*
     * NUEVO
     * Descripcion: Copia un valor: d = x.
     * Entrada: Destino y valor.
     * Salida: Instrucciones MIPS.
     * Restricciones: Si el destino es float usa los registros $f.
     */
    private void copiar(String destino, String valor) {
        if (tipoDe(destino).equals("float")) {
            cargarFloat(valor, "$f0");
            guardarFloat("$f0", destino);
        } else {
            cargar(valor, "$t0");
            guardar("$t0", destino);
        }
    }

    /*
     * NUEVO
     * Descripcion: Imprime un valor segun su tipo y un cambio de linea.
     * Entrada: El valor.
     * Salida: Instrucciones syscall.
     * Restricciones: int = 1, float = 2, string = 4, char = 11.
     */
    private void escribir(String valor) {

        switch (tipoDe(valor)) {
            case "float":
                cargarFloat(valor, "$f12");   // syscall 2 imprime lo que esta en $f12
                instr("li $v0, 2");
                break;
            case "string":
                cargar(valor, "$a0");          // $a0 = direccion del texto
                instr("li $v0, 4");
                break;
            case "char":
                cargar(valor, "$a0");
                instr("li $v0, 11");
                break;
            default:                           // int y bool
                cargar(valor, "$a0");
                instr("li $v0, 1");
        }
        instr("syscall");

        instr("li $a0, 10");     // 10 = cambio de linea
        instr("li $v0, 11");     // 11 = imprimir caracter
        instr("syscall");
    }

    /*
     * Descripcion: Traduce una operacion con enteros: d = a op b.
     * Entrada: Destino, operando izquierdo, operador y operando derecho.
     * Salida: Instrucciones MIPS.
     * Restricciones: div deja el cociente en LO (mflo) y el residuo en HI (mfhi).
     *                Las comparaciones dan 1 si se cumplen y 0 si no.
     */
    private void aritmetica(String destino, String a, String operador, String b) {

        if (operador.equals("pot")) {
            potencia(destino, a, b);
            return;
        }

        cargar(a, "$t0");
        cargar(b, "$t1");

        switch (operador) {
            /* aritmeticas */
            case "+":   instr("add $t2, $t0, $t1"); break;
            case "-":   instr("sub $t2, $t0, $t1"); break;
            case "*":   instr("mul $t2, $t0, $t1"); break;
            case "/":
            case "//":
                instr("div $t0, $t1");
                instr("mflo $t2");       // cociente
                break;
            case "mod":
                instr("div $t0, $t1");
                instr("mfhi $t2");       // residuo
                break;

            /* comparaciones */
            case "<":   instr("slt $t2, $t0, $t1"); break;
            case ">":   instr("sgt $t2, $t0, $t1"); break;
            case "<=":  instr("sle $t2, $t0, $t1"); break;
            case ">=":  instr("sge $t2, $t0, $t1"); break;
            case "==":  instr("seq $t2, $t0, $t1"); break;
            case "!=":  instr("sne $t2, $t0, $t1"); break;

            /* logicas */
            case "and": instr("and $t2, $t0, $t1"); break;
            case "or":  instr("or $t2, $t0, $t1"); break;

            default:
                instr("# OPERADOR NO TRADUCIDO TODAVIA");
                return;
        }

        guardar("$t2", destino);
    }

    /*
     * NUEVO
     * Descripcion: Traduce una operacion con float: d = a op b.
     * Entrada: Destino, operandos y operador.
     * Salida: Instrucciones MIPS con registros $f.
     * Restricciones: MIPS solo compara con c.eq.s, c.lt.s y c.le.s;
     *                para > y >= se cambian de lado los operandos.
     */
    private void operacionFloat(String destino, String a, String operador, String b) {

        if (operador.equals("pot")) {
            potenciaFloat(destino, a, b);
            return;
        }

        cargarFloat(a, "$f0");
        cargarFloat(b, "$f2");

        switch (operador) {
            case "+": instr("add.s $f4, $f0, $f2"); guardarFloat("$f4", destino); return;
            case "-": instr("sub.s $f4, $f0, $f2"); guardarFloat("$f4", destino); return;
            case "*": instr("mul.s $f4, $f0, $f2"); guardarFloat("$f4", destino); return;
            case "/": instr("div.s $f4, $f0, $f2"); guardarFloat("$f4", destino); return;
            default:  break;
        }

        /* comparaciones: el resultado es 1 o 0 en $t2 */
        String saltarSi = "bc1t";   // saltar si la comparacion fue verdadera
        switch (operador) {
            case "<":  instr("c.lt.s $f0, $f2"); break;
            case "<=": instr("c.le.s $f0, $f2"); break;
            case ">":  instr("c.lt.s $f2, $f0"); break;
            case ">=": instr("c.le.s $f2, $f0"); break;
            case "==": instr("c.eq.s $f0, $f2"); break;
            case "!=": instr("c.eq.s $f0, $f2"); saltarSi = "bc1f"; break;
            default:
                instr("# OPERADOR FLOAT NO TRADUCIDO TODAVIA");
                return;
        }

        contadorEtiquetas++;
        String listo = "cmp_listo_" + contadorEtiquetas;
        instr("li $t2, 1");
        instr(saltarSi + " " + listo);
        instr("li $t2, 0");
        ponerEtiqueta(listo);
        guardar("$t2", destino);
    }

    /*
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

        ponerEtiqueta(ciclo);
        instr("blez $t1, " + fin);       // si exponente <= 0, terminar
        instr("mul $t2, $t2, $t0");      // resultado = resultado * base
        instr("addi $t1, $t1, -1");      // exponente = exponente - 1
        instr("j " + ciclo);

        ponerEtiqueta(fin);
        guardar("$t2", destino);
    }

    /*
     * NUEVO
     * Descripcion: Potencia con base float: d = a pot b.
     * Entrada: Destino, base float y exponente entero.
     * Salida: Un ciclo con mul.s.
     * Restricciones: Si el exponente es 0 o negativo, el resultado es 1.0.
     */
    private void potenciaFloat(String destino, String base, String exponente) {

        contadorEtiquetas++;
        String ciclo = "pot_ciclo_" + contadorEtiquetas;
        String fin = "pot_fin_" + contadorEtiquetas;

        cargarFloat(base, "$f0");
        cargar(exponente, "$t1");
        cargarFloat("1.0", "$f4");       // resultado = 1.0

        ponerEtiqueta(ciclo);
        instr("blez $t1, " + fin);
        instr("mul.s $f4, $f4, $f0");
        instr("addi $t1, $t1, -1");
        instr("j " + ciclo);

        ponerEtiqueta(fin);
        guardarFloat("$f4", destino);
    }

    /*
     * Descripcion: Pone un valor entero en un registro.
     * Entrada: El valor y el registro.
     * Salida: Instruccion li, la o lw.
     * Restricciones: true = 1, false = 0, 'A' = 65, "texto" = su direccion.
     */
    private void cargar(String valor, String registro) {

        if (valor.equals("true") || valor.equals("false")) {
            instr("li " + registro + ", " + (valor.equals("true") ? 1 : 0));
            return;
        }

        /* NUEVO: caracter: 'A' -> 65 */
        if (valor.startsWith("'")) {
            instr("li " + registro + ", " + codigoCaracter(valor));
            return;
        }

        /* NUEVO: texto: se guarda en .data y se carga su direccion */
        if (valor.startsWith("\"")) {
            instr("la " + registro + ", " + etiquetaTexto(valor));
            return;
        }

        if (valor.matches("-?\\d+")) {
            instr("li " + registro + ", " + valor);
        } else {
            anotar(valor);
            instr("lw " + registro + ", " + nombreEnMemoria(valor));
        }
    }

    /* Guarda un registro entero en una variable. */
    private void guardar(String registro, String destino) {
        anotar(destino);
        instr("sw " + registro + ", " + nombreEnMemoria(destino));
    }

    /*
     * NUEVO
     * Descripcion: Pone un valor float en un registro $f.
     * Entrada: El valor y el registro.
     * Salida: Instruccion l.s (o conversion si el valor es entero).
     * Restricciones: Los numeros float fijos se guardan en .data.
     */
    private void cargarFloat(String valor, String registro) {

        if (valor.matches("-?\\d+\\.\\d+")) {
            instr("l.s " + registro + ", " + etiquetaFlotante(valor));
            return;
        }

        if (!tipoDe(valor).equals("float")) {
            /* un entero usado como float: se convierte */
            cargar(valor, "$t3");
            instr("mtc1 $t3, " + registro);
            instr("cvt.s.w " + registro + ", " + registro);
            return;
        }

        anotar(valor);
        instr("l.s " + registro + ", " + nombreEnMemoria(valor));
    }

    /* NUEVO: Guarda un registro $f en una variable float. */
    private void guardarFloat(String registro, String destino) {
        anotar(destino);
        instr("s.s " + registro + ", " + nombreEnMemoria(destino));
    }

    /*
     * NUEVO
     * Descripcion: Averigua el tipo de un valor.
     * Entrada: Numero, texto, caracter, temporal o variable.
     * Salida: int, float, bool, char o string.
     * Restricciones: Los temporales llevan el tipo en el nombre (float6).
     */
    private String tipoDe(String valor) {

        if (valor.matches("-?\\d+")) return "int";
        if (valor.matches("-?\\d+\\.\\d+")) return "float";
        if (valor.equals("true") || valor.equals("false")) return "bool";
        if (valor.startsWith("'")) return "char";
        if (valor.startsWith("\"")) return "string";

        if (variables.containsKey(valor)) {
            return variables.get(valor);
        }

        /* temporal: int5, float6, string7, char8, bool9 */
        for (String tipo : new String[] {"float", "string", "char", "bool", "int"}) {
            if (valor.matches(tipo + "\\d+")) {
                return tipo;
            }
        }

        return "int";
    }

    /* NUEVO: Anota una variable o temporal para reservarle espacio en .data. */
    private void anotar(String nombre) {
        variables.putIfAbsent(nombre, tipoDe(nombre));
    }

    /* NUEVO: Guarda un texto fijo en .data y devuelve su etiqueta. */
    private String etiquetaTexto(String literal) {
        return textos.computeIfAbsent(literal, k -> "txt_" + (textos.size() + 1));
    }

    /* NUEVO: Guarda un float fijo en .data y devuelve su etiqueta. */
    private String etiquetaFlotante(String literal) {
        return flotantes.computeIfAbsent(literal, k -> "num_" + (flotantes.size() + 1));
    }

    /*
     * NUEVO
     * Descripcion: Da el codigo ASCII de un caracter.
     * Entrada: Literal como 'A' o '\n'.
     * Salida: El numero (ej: 'A' = 65).
     * Restricciones: Acepta \n, \t y \0.
     */
    private int codigoCaracter(String literal) {
        String c = literal.substring(1, literal.length() - 1);
        if (c.startsWith("\\") && c.length() > 1) {
            switch (c.charAt(1)) {
                case 'n': return 10;
                case 't': return 9;
                case '0': return 0;
                default:  return c.charAt(1);
            }
        }
        return c.isEmpty() ? 0 : c.charAt(0);
    }

    /*
     * Descripcion: Agrega una instruccion con sangria.
     * Entrada: La instruccion.
     * Salida: Una linea en el .asm.
     * Restricciones: Si hay una linea del intermedio pendiente, se pone a la derecha.
     */
    private void instr(String instruccion) {

        String linea = "    " + instruccion;

        if (comentarioPendiente != null) {
            linea = String.format("%-" + COLUMNA_COMENTARIO + "s# %s", linea, comentarioPendiente);
            comentarioPendiente = null;
        }

        texto.append(linea).append("\n");
    }

    /*
     * NUEVO
     * Descripcion: Agrega una etiqueta separada con una linea en blanco.
     * Entrada: Nombre de la etiqueta.
     * Salida: La etiqueta sin sangria, para que se vea donde empieza cada bloque.
     * Restricciones: La etiqueta ya no necesita comentario: se lee igual que en el intermedio.
     */
    private void ponerEtiqueta(String nombre) {
        texto.append("\n").append(nombre).append(":\n");
        comentarioPendiente = null;
    }

    /*
     * Descripcion: Convierte una etiqueta del codigo intermedio a MIPS.
     * Entrada: Etiqueta como principal$if$1$bloque1.
     * Salida: principal.if.1.bloque1
     * Restricciones: MIPS no acepta $ en las etiquetas, pero si acepta puntos.
     */
    private String etiqueta(String nombre) {
        return nombre.replace('$', '.');
    }

    /*
     * NUEVO
     * Descripcion: Indica si un nombre es un temporal del codigo intermedio.
     * Entrada: Nombre.
     * Salida: true si es como int5, float6, string7, char8 o bool9.
     * Restricciones: Ninguna.
     */
    private boolean esTemporal(String nombre) {
        return nombre.matches("(int|float|string|char|bool)\\d+");
    }

    /*
     * NUEVO
     * Descripcion: Nombre con el que se guarda algo en .data.
     * Entrada: Nombre de la variable o del temporal.
     * Salida: var_x para variables y tmp_float6 para temporales.
     * Restricciones: El prefijo evita choques con instrucciones (ej: una variable llamada b).
     */
    private String nombreEnMemoria(String nombre) {
        return (esTemporal(nombre) ? "tmp_" : "var_") + nombre;
    }

    /*
     * Descripcion: Junta .data y .text.
     * Entrada: Ninguna.
     * Salida: El archivo .asm completo.
     * Restricciones: .data se ordena en grupos: variables, temporales, numeros y textos.
     */
    private String armarArchivo() {

        StringBuilder vars = new StringBuilder();
        StringBuilder tmps = new StringBuilder();

        for (Map.Entry<String, String> v : variables.entrySet()) {
            String espacio = v.getValue().equals("float") ? ".float 0.0" : ".word 0";
            String linea = nombreEnMemoria(v.getKey()) + ": " + espacio + "\n";

            if (esTemporal(v.getKey())) {
                tmps.append(linea);
            } else {
                vars.append(linea);
            }
        }

        StringBuilder data = new StringBuilder(".data\n");

        data.append("\n# ---------- variables del programa ----------\n").append(vars);
        data.append("\n# ---------- temporales del codigo intermedio ----------\n").append(tmps);

        data.append("\n# ---------- numeros float fijos ----------\n");
        for (Map.Entry<String, String> f : flotantes.entrySet()) {
            data.append(f.getValue()).append(": .float ").append(f.getKey()).append("\n");
        }

        data.append("\n# ---------- textos fijos ----------\n");
        for (Map.Entry<String, String> t : textos.entrySet()) {
            data.append(t.getValue()).append(": .asciiz ").append(t.getKey()).append("\n");
        }

        return data + "\n.text\n.globl main\n" + texto;
    }
}