import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Descripcion: Traduce el codigo intermedio (.tac) a codigo MIPS.
 * Entrada: Texto del codigo intermedio.
 * Salida: Texto del codigo MIPS (.asm) listo para QtSpim.
 * Restricciones: Maneja tipos, operaciones, if, ciclos, write y funciones con la pila.
 * Objetivo: Generar el codigo destino del compilador.
 */
public class GeneradorMIPS {

    /* Codigos de syscall: le dicen a MIPS que servicio usar */
    private static final int SYSCALL_IMPRIMIR_ENTERO = 1;
    private static final int SYSCALL_IMPRIMIR_FLOAT = 2;
    private static final int SYSCALL_IMPRIMIR_TEXTO = 4;
    private static final int SYSCALL_LEER_ENTERO = 5;
    private static final int SYSCALL_LEER_FLOAT = 6;
    private static final int SYSCALL_TERMINAR = 10;
    private static final int SYSCALL_IMPRIMIR_CARACTER = 11;
    private static final int SYSCALL_LEER_CHAR = 12;

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

    /* Cada funcion del programa (int$suma) con su marco en la pila */
    private Map<String, MarcoFuncion> funciones = new LinkedHashMap<>();

    /* Funcion que se esta traduciendo (null = codigo global) */
    private MarcoFuncion funcionActual = null;


    /* ============================================================
       TRADUCCION GENERAL
       ============================================================ */

    /*
     * Descripcion: Traduce todo el codigo intermedio.
     * Entrada: Texto del .tac.
     * Salida: Texto del .asm.
     * Restricciones: Se debe usar un generador nuevo por archivo.
     * Objetivo: Ser el punto de entrada de la Fase 5.
     */
    public String traducir(String codigoIntermedio) {

        String[] lineas = codigoIntermedio.split("\n");

        funciones = LectorDeFunciones.leer(lineas);

        codigo.append("main:\n");
        traducirCodigoGlobal(lineas);
        traducirFunciones(lineas);

        return datos.generar() + "\n.text\n.globl main\n" + codigo;
    }

    /*
     * Descripcion: Traduce lo que esta fuera de las funciones y salta a principal.
     * Entrada: Lineas del .tac.
     * Salida: Instrucciones en main.
     * Restricciones: Los goto del codigo global se cambian por un solo salto a principal.
     * Objetivo: Darle valor a las variables globales antes de empezar.
     */
    private void traducirCodigoGlobal(String[] lineas) {

        MarcoFuncion funcion = null;

        for (String lineaOriginal : lineas) {

            String linea = lineaOriginal.trim();

            if (funcion == null && LectorDeFunciones.esInicioDeFuncion(linea)) {
                funcion = funciones.get(LectorDeFunciones.etiquetaDeInicio(linea));
            } else if (funcion != null) {
                if (LectorDeFunciones.esFinDe(funcion, linea)) {
                    funcion = null;
                }
            } else if (!linea.startsWith("goto ")) {
                traducirLinea(linea);
            }
        }

        if (funciones.containsKey("void$principal")) {
            lineaPendiente = "inicio del programa: ir a principal";
            instruccion("j " + convertirEtiqueta("void$principal"));
        } else {
            terminarPrograma();
        }
    }

    /*
     * Descripcion: Traduce todas las funciones, una despues de otra.
     * Entrada: Lineas del .tac.
     * Salida: Instrucciones de cada funcion.
     * Restricciones: Se salta el codigo global (ya se tradujo).
     * Objetivo: Generar el codigo de cada funcion con su pila.
     */
    private void traducirFunciones(String[] lineas) {

        for (String lineaOriginal : lineas) {

            String linea = lineaOriginal.trim();

            if (funcionActual == null && !LectorDeFunciones.esInicioDeFuncion(linea)) {
                continue;
            }

            traducirLinea(linea);
        }
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

        if (LectorDeFunciones.esInicioDeFuncion(linea)) {
            entrarAFuncion(LectorDeFunciones.etiquetaDeInicio(linea));
        } else if (funcionActual != null && LectorDeFunciones.esFinDe(funcionActual, linea)) {
            salirDeFuncion();
        } else if (linea.startsWith("param_")) {
            lineaPendiente = null;
        } else if (linea.startsWith("param ")) {
            empujarArgumento(linea.substring("param ".length()).trim());
        } else if (linea.startsWith("call ")) {
            llamar(linea.substring("call ".length()).trim(), null);
        } else if (linea.equals("return") || linea.startsWith("return ")) {
            retornar(linea.length() > "return".length() ? linea.substring("return ".length()).trim() : null);
        } else if (linea.startsWith("declaracion# ") || linea.startsWith("declaracionPrevia# ")) {
            traducirDeclaracion(linea);
        } else if (linea.startsWith("read ")) {
            leer(linea.substring("read ".length()).trim());
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
     * Descripcion: Anota una variable global o limpia una variable local sin valor.
     * Entrada: Linea como "declaracion# int x".
     * Salida: Ninguna, o un sw $zero si es local sin valor.
     * Restricciones: Las locales ya estan anotadas en el marco; los arreglos solo se anotan.
     * Objetivo: Que toda variable empiece en 0 y tenga su tipo conocido.
     */
    private void traducirDeclaracion(String linea) {

        Declaracion declaracion = Declaracion.leer(linea);

        if (funcionActual == null && declaracion.esArreglo()) {
            datos.declararArreglo(declaracion.nombre, declaracion.tipo, declaracion.tamano);
        } else if (funcionActual == null) {
            datos.declararVariable(declaracion.nombre, declaracion.tipo);
        } else if (linea.startsWith("declaracion# ") && !declaracion.esArreglo()) {
            instruccion("sw $zero, " + ubicacion(declaracion.nombre));
        }
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

        if (valor.startsWith("call ")) {
            llamar(valor.substring("call ".length()).trim(), destino);
            return;
        }

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


    /* ============================================================
       ASIGNACIONES Y OPERACIONES
       ============================================================ */

    /*
     * Descripcion: Copia un valor en una variable.
     * Entrada: Destino y valor.
     * Salida: Instrucciones de cargar y guardar.
     * Restricciones: Si el destino es float usa registros $f.
     * Objetivo: Traducir "x = y".
     */
    private void copiar(String destino, String valor) {
        if (esFloat(destino)) {
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
        if (esFloat(izquierdo) || esFloat(derecho)) {
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
     * Restricciones: MIPS solo tiene c.eq.s, c.lt.s y c.le.s; > y >= se hacen al reves.
     * Objetivo: Traducir comparaciones con decimales.
     */
    private void compararFloat(String destino, String operador) {

        String saltarSi = "bc1t";

        switch (operador) {
            case "<":  instruccion("c.lt.s $f0, $f2"); break;
            case "<=": instruccion("c.le.s $f0, $f2"); break;
            case ">":  instruccion("c.lt.s $f2, $f0"); break;
            case ">=": instruccion("c.le.s $f2, $f0"); break;
            case "==": instruccion("c.eq.s $f0, $f2"); break;
            case "!=": instruccion("c.eq.s $f0, $f2"); saltarSi = "bc1f"; break;
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

    /* ============================================================
       FUNCIONES: ENTRAR, SALIR, LLAMAR Y RETORNAR
       ============================================================ */

    /*
     * Descripcion: Empieza una funcion y prepara su espacio en la pila.
     * Entrada: Etiqueta como int$suma.
     * Salida: La etiqueta y el prologo (guardar $ra y $fp, reservar locales).
     * Restricciones: El marco ya debe estar calculado.
     * Objetivo: Que cada llamada tenga sus propias variables.
     */
    private void entrarAFuncion(String etiqueta) {

        funcionActual = funciones.get(etiqueta);
        lineaPendiente = null;

        codigo.append("\n# ============================================================\n");
        codigo.append("# funcion ").append(etiqueta).append("\n");
        codigo.append("# ============================================================");
        ponerEtiqueta(convertirEtiqueta(etiqueta));

        lineaPendiente = "guardar $ra y $fp, y reservar las variables locales";
        instruccion("addiu $sp, $sp, -8");
        instruccion("sw $ra, 4($sp)");
        instruccion("sw $fp, 0($sp)");
        instruccion("move $fp, $sp");

        if (funcionActual.bytesLocales() > 0) {
            instruccion("addiu $sp, $sp, -" + funcionActual.bytesLocales());
        }
    }

    /*
     * Descripcion: Termina una funcion.
     * Entrada: Ninguna.
     * Salida: Regreso a quien llamo, o fin del programa si es principal.
     * Restricciones: Se usa en la linea tipo$nombre$final.
     * Objetivo: Que una funcion sin return tambien regrese bien.
     */
    private void salirDeFuncion() {

        if (funcionActual.esPrincipal()) {
            terminarPrograma();
        } else {
            regresar();
        }

        funcionActual = null;
    }

    /*
     * Descripcion: Devuelve el control a quien llamo la funcion.
     * Entrada: Ninguna.
     * Salida: Instrucciones del epilogo.
     * Restricciones: El valor de retorno ya debe estar en $v0 o $f0.
     * Objetivo: Recuperar $fp y $ra y volver con jr $ra.
     */
    private void regresar() {
        instruccion("move $sp, $fp");
        instruccion("lw $fp, 0($sp)");
        instruccion("lw $ra, 4($sp)");
        instruccion("addiu $sp, $sp, 8");
        instruccion("jr $ra");
    }

    /*
     * Descripcion: Traduce un return.
     * Entrada: El valor a devolver, o null si no devuelve nada.
     * Salida: El valor en $v0 (o $f0 si es float) y el regreso.
     * Restricciones: En principal, return termina el programa.
     * Objetivo: Traducir "return x".
     */
    private void retornar(String valor) {

        if (funcionActual == null || funcionActual.esPrincipal()) {
            terminarPrograma();
            return;
        }

        if (valor != null && esFloat(valor)) {
            cargarFloat(valor, "$f0");
        } else if (valor != null) {
            cargarEntero(valor, "$v0");
        }

        regresar();
    }

    /*
     * Descripcion: Mete un argumento en la pila antes de una llamada.
     * Entrada: El argumento.
     * Salida: El valor queda en 0($sp).
     * Restricciones: Los argumentos se meten en el mismo orden que los parametros.
     * Objetivo: Traducir "param x".
     */
    private void empujarArgumento(String valor) {

        if (esFloat(valor)) {
            cargarFloat(valor, "$f0");
            instruccion("addiu $sp, $sp, -4");
            instruccion("s.s $f0, 0($sp)");
        } else {
            cargarEntero(valor, "$t0");
            instruccion("addiu $sp, $sp, -4");
            instruccion("sw $t0, 0($sp)");
        }
    }

    /*
     * Descripcion: Llama a una funcion.
     * Entrada: Texto como "int$suma, 2" y donde guardar el resultado (o null).
     * Salida: jal, sacar los argumentos de la pila y guardar el resultado.
     * Restricciones: El resultado llega en $v0, o en $f0 si es float.
     * Objetivo: Traducir "call f, n" y "x = call f, n".
     */
    private void llamar(String datosLlamada, String destino) {

        String[] partes = datosLlamada.split(",");
        String funcion = partes[0].trim();
        int cantidadArgumentos = partes.length > 1 ? Integer.parseInt(partes[1].trim()) : 0;

        instruccion("jal " + convertirEtiqueta(funcion));

        if (cantidadArgumentos > 0) {
            instruccion("addiu $sp, $sp, " + (4 * cantidadArgumentos));
        }

        if (destino != null && esFloat(destino)) {
            guardarFloat("$f0", destino);
        } else if (destino != null) {
            guardarEntero("$v0", destino);
        }
    }

    /*
     * Descripcion: Dice donde esta guardado un nombre.
     * Entrada: Nombre de variable, parametro, temporal o elemento de arreglo (a[i]).
     * Salida: Posicion en la pila (-8($fp)), etiqueta en .data (var_x) o 0($t9) para a[i].
     * Restricciones: Primero busca en la funcion actual y despues en las globales.
     * Objetivo: Usar la pila dentro de funciones y .data para lo global.
     */
    private String ubicacion(String nombre) {

        if (ElementoArreglo.es(nombre)) {
            calcularDireccionDeElemento(ElementoArreglo.leer(nombre));
            return "0($t9)";
        }

        if (funcionActual != null && funcionActual.tiene(nombre)) {
            return funcionActual.ubicacion(nombre);
        }

        datos.reservar(nombre);
        return datos.nombreEnMemoria(nombre);
    }

    /*
     * Descripcion: Deja en $t9 la direccion de un elemento de arreglo.
     * Entrada: El elemento, por ejemplo a[i] o a[2].
     * Salida: $t9 = donde empieza el arreglo + indice * 4.
     * Restricciones: Usa $t8 para el indice; el valor a guardar no puede estar en $t8 ni $t9.
     * Objetivo: Poder leer y escribir cualquier posicion del arreglo.
     */
    private void calcularDireccionDeElemento(ElementoArreglo elemento) {

        String arreglo = elemento.arreglo;

        if (funcionActual != null && funcionActual.esArreglo(arreglo)) {
            instruccion("addiu $t9, $fp, " + funcionActual.inicioDeArreglo(arreglo));
        } else {
            datos.reservar(arreglo);
            instruccion("la $t9, " + datos.nombreEnMemoria(arreglo));
        }

        if (SeccionDatosMIPS.esNumeroEntero(elemento.indice)) {
            int desplazamiento = 4 * Integer.parseInt(elemento.indice);
            if (desplazamiento != 0) {
                instruccion("addiu $t9, $t9, " + desplazamiento);
            }
            return;
        }

        instruccion("lw $t8, " + ubicacion(elemento.indice));
        instruccion("sll $t8, $t8, 2");
        instruccion("addu $t9, $t9, $t8");
    }

    /*
     * Descripcion: Dice el tipo de un valor.
     * Entrada: Literal, variable, parametro o temporal.
     * Salida: int, float, bool, char o string.
     * Restricciones: Primero busca en la funcion actual.
     * Objetivo: Elegir las instrucciones correctas para cada tipo.
     */
    private String tipoDe(String valor) {

        /* el tipo de a[i] es el tipo del arreglo a */
        if (ElementoArreglo.es(valor)) {
            valor = ElementoArreglo.leer(valor).arreglo;
        }

        if (funcionActual != null && funcionActual.tiene(valor)) {
            return funcionActual.tipoDe(valor);
        }

        return datos.tipoDe(valor);
    }

    /*
     * Descripcion: Dice si un valor es float.
     * Entrada: Valor.
     * Salida: true si es float.
     * Restricciones: Ninguna.
     * Objetivo: Saber cuando usar los registros $f.
     */
    private boolean esFloat(String valor) {
        return tipoDe(valor).equals("float");
    }


    /* ============================================================
       ESCRITURA, LECTURA Y FIN DEL PROGRAMA
       ============================================================ */

    /*
     * Descripcion: Imprime un valor y un cambio de linea.
     * Entrada: El valor a imprimir.
     * Salida: Instrucciones syscall.
     * Restricciones: El servicio depende del tipo del valor.
     * Objetivo: Traducir "write x".
     */
    private void escribir(String valor) {

        switch (tipoDe(valor)) {
            case "float":
                cargarFloat(valor, "$f12");
                llamarSistema(SYSCALL_IMPRIMIR_FLOAT);
                break;
            case "bool":
                escribirBooleano(valor);
                break;
            case "string":
                cargarEntero(valor, "$a0");
                llamarSistema(SYSCALL_IMPRIMIR_TEXTO);
                break;
            default:
                cargarEntero(valor, "$a0");
                llamarSistema(SYSCALL_IMPRIMIR_ENTERO);
        }

        instruccion("li $a0, " + CAMBIO_DE_LINEA);
        llamarSistema(SYSCALL_IMPRIMIR_CARACTER);
    }

    /*
     * Descripcion: Imprime un valor booleano y un cambio de linea.
     * Entrada: El valor a imprimir.
     * Salida: Instrucciones syscall.
     * Restricciones: El servicio depende del tipo bool.
     * Objetivo: Traducir "write x".
     */
    private void escribirBooleano(String valor) {
        int numero = numeroNuevo();
        String etiquetaFalso = "write_bool_false_" + numero;
        String etiquetaFin = "write_bool_end_" + numero;

        cargarEntero(valor, "$t0");
        instruccion("beq $t0, $zero, " + etiquetaFalso);
        cargarEntero("\"true\"", "$a0");
        llamarSistema(SYSCALL_IMPRIMIR_TEXTO);
        instruccion("j " + etiquetaFin);

        ponerEtiqueta(etiquetaFalso);
        cargarEntero("\"false\"", "$a0");
        llamarSistema(SYSCALL_IMPRIMIR_TEXTO);
        ponerEtiqueta(etiquetaFin);
    }

    /*
     * Descripcion: Lee un valor y un cambio de linea.
     * Entrada: El valor a leer.
     * Salida: Instrucciones syscall.
     * Restricciones: El servicio depende del tipo del valor.
     * Objetivo: Traducir "read x".
     */

    private void leer(String destino) {
        
        switch (tipoDe(destino)) {
            case "int":
                //Va al revés que la escritura porque primero necesita preparar el registro 
                llamarSistema(SYSCALL_LEER_ENTERO); 
                guardarEntero("$v0", destino); //Aquí va a guardar la variable
                break;
            
            case "float":
                llamarSistema(SYSCALL_LEER_FLOAT);
                guardarFloat("$f0", destino); //Se deja guardado directamente en $f0 sin moverlo
                break;

            case "char":
                llamarSistema(SYSCALL_LEER_CHAR);
                instruccion("move $t0, $v0"); //Añade la instrucción de move para terminar dejando el valor en $t0
                guardarChar("$t0", destino);
                break;
        
            default:
                break;
        }
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


    /* ============================================================
       CARGAR Y GUARDAR VALORES
       ============================================================ */

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
            instruccion("lw " + registro + ", " + ubicacion(valor));
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
        instruccion("sw " + registro + ", " + ubicacion(destino));
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
        } else if (!esFloat(valor)) {
            cargarEntero(valor, "$t3");
            instruccion("mtc1 $t3, " + registro);
            instruccion("cvt.s.w " + registro + ", " + registro);
        } else {
            instruccion("l.s " + registro + ", " + ubicacion(valor));
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
        instruccion("s.s " + registro + ", " + ubicacion(destino));
    }

    /*
     * Descripcion: Guarda un registro $t en una variable char y la mueve al destino.
     * Entrada: Registro y destino.
     * Salida: Instruccion sb.
     * Restricciones: Ninguna.
     * Objetivo: Dejar el valor char en la variable.
     */
    private void guardarChar(String registro, String destino) {
        instruccion("sw " + registro + ", " + ubicacion(destino));
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
                case 'n': return 10;
                case 't': return 9;
                case '0': return 0;
                default:  return contenido.charAt(1);
            }
        }

        return contenido.isEmpty() ? 0 : contenido.charAt(0);
    }


    /* ============================================================
       ESCRIBIR EN EL .asm
       ============================================================ */

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
