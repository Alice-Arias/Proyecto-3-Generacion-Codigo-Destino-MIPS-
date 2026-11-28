import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

/*
 * Descripcion: Coordina la compilacion .cmm o la traduccion directa de archivos .tac.
 * Entrada: Archivo .cmm, archivo de tokens opcional o modo --tac.
 * Salida: Resultados en consola y archivos en la carpeta salida.
 * Restricciones: El archivo .cmm debe estar en UTF-8.
 * Objetivo: Coordinar las fases sin hacer el trabajo de cada una.
 */
public class Main {

    /*
     * Descripcion: Ejecuta la compilacion completa o la traduccion directa de TAC.
     * Entrada: Archivo .cmm y archivo de tokens opcional, o --tac <archivo.tac>.
     * Salida: Resultados en consola y archivos en salida.
     * Restricciones: Si no recibe archivo, muestra como usarlo.
     * Objetivo: Ser el punto de entrada del compilador.
     */
    public static void main(String[] args) throws Exception {

        long inicio = System.nanoTime();

        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, "UTF-8"));

        if (args.length < 1) {
            Consola.mostrarTitulo("ANALIZADOR · USO");
            System.out.println();
            System.out.println("  java Main <archivo_fuente> [archivo_tokens]");
            System.out.println("  java Main --tac <archivo.tac>");
            System.out.println();
            return;
        }

        if ("--tac".equals(args[0])) {
            if (args.length != 2) {
                throw new IllegalArgumentException("Uso: java Main --tac <archivo.tac>");
            }

            traducirArchivoTAC(new File(args[1]));
            return;
        }

        File archivoFuente = new File(args[0]);
        File archivoTokens = args.length > 1 ? new File(args[1]) : Archivos.archivoTokensPorDefecto(archivoFuente);

        Consola.mostrarTitulo("ANALIZADOR LÉXICO, SINTÁCTICO Y SEMÁNTICO");
        System.out.println();
        Consola.mostrarDato("Archivo fuente", Archivos.rutaCorta(archivoFuente));
        Consola.mostrarDato("Archivo de tokens", Archivos.rutaCorta(archivoTokens));

        ResultadoLexico lexico = faseLexica(archivoFuente, archivoTokens);

        ParserConErrores analizador = faseSintactica(archivoFuente);

        TablaSimbolos tabla = analizador.tabla;
        File reporte = faseSemantica(tabla, archivoFuente);

        int erroresTotales = lexico.errores.size() + analizador.errores.size() + tabla.getErrores().size();

        /* Si el parser se rindio, el resultado tampoco es valido aunque no haya errores contados */
        boolean archivoCorrecto = erroresTotales == 0 && !analizador.interrumpido;

        String codigoIntermedio = faseCodigoIntermedio(analizador, archivoFuente, archivoCorrecto);

        String codigoMips = faseCodigoMIPS(codigoIntermedio, archivoFuente);

        mostrarResultadoFinal(lexico, analizador, tabla, erroresTotales, archivoCorrecto, inicio);

        /* Las preguntas van despues del resultado: asi el veredicto se ve primero
        y el tiempo no cuenta lo que se tarda la persona en responder.
        Orden: codigo intermedio, codigo MIPS y tabla de simbolos */
        ofrecerCodigoIntermedio(codigoIntermedio);

        ofrecerCodigoMIPS(codigoMips);

        ofrecerTablaSimbolos(tabla, reporte);

        System.out.println();
    }

    /*
     * Descripcion: Traduce un archivo TAC existente sin ejecutar el frontend.
     * Entrada: Archivo .tac.
     * Salida: Archivo .asm dentro de salida/mips.
     * Restricciones: El archivo debe existir y tener extension .tac.
     * Objetivo: Permitir ejecutar directamente la Fase 5.
     */
    private static void traducirArchivoTAC(File archivoTAC) throws Exception {

        if (!archivoTAC.isFile() || !archivoTAC.getName().toLowerCase().endsWith(".tac")) {
            throw new IllegalArgumentException("El archivo TAC no existe o no tiene extensión .tac: "
                    + archivoTAC.getPath());
        }

        long inicio = System.nanoTime();
        String codigoIntermedio = Files.readString(archivoTAC.toPath(), StandardCharsets.UTF_8);

        Consola.mostrarTitulo("TRADUCCIÓN DIRECTA · TAC A MIPS");
        Consola.mostrarDato("Archivo TAC", Archivos.rutaCorta(archivoTAC));

        String codigoMips = new GeneradorMIPS().traducir(codigoIntermedio);
        String nombreAsm = Archivos.nombreSinExtension(archivoTAC) + ".asm";
        File archivoAsm = Archivos.guardarEnSalida("mips", nombreAsm, codigoMips);

        Consola.mostrarDato("Archivo MIPS", archivoAsm.getName());
        Consola.mostrarDato("Carpeta", Archivos.carpetaCorta(archivoAsm));
        Consola.mostrarDato("Tiempo de traducción", tiempoTranscurrido(inicio));
        Consola.mostrarEstado(true, "Código MIPS generado directamente desde TAC.");
    }

    /*
     * Descripcion: Fase 1: lee los tokens y muestra los errores lexicos.
     * Entrada: Archivo fuente y archivo de tokens.
     * Salida: Cantidad de tokens y errores lexicos.
     * Restricciones: Guarda los tokens en un archivo.
     * Objetivo: Ejecutar y mostrar la fase lexica.
     */
    private static ResultadoLexico faseLexica(File fuente, File archivoTokens) throws Exception {

        Consola.mostrarSeccion("FASE 1 · ANÁLISIS LÉXICO");

        ResultadoLexico resultado = AnalisisLexico.analizar(fuente, archivoTokens);

        Consola.mostrarConteo("Tokens reconocidos", resultado.tokens);
        Consola.mostrarConteoErrores("Errores léxicos", resultado.errores.size());

        if (resultado.errores.isEmpty()) {
            Consola.mostrarEstado(true, "Análisis léxico correcto.");
        } else {
            AnalisisLexico.mostrarErrores(resultado.errores);
            Consola.mostrarEstado(false, "El archivo contiene errores léxicos.");
        }

        return resultado;
    }

    /*
     * Descripcion: Fase 2: revisa la estructura y muestra los errores sintacticos.
     * Entrada: Archivo fuente.
     * Salida: El parser ya ejecutado.
     * Restricciones: El parser tambien llena la tabla y genera el codigo.
     * Objetivo: Ejecutar y mostrar la fase sintactica.
     */
    private static ParserConErrores faseSintactica(File fuente) throws Exception {

        Consola.mostrarSeccion("FASE 2 · ANÁLISIS SINTÁCTICO");

        ParserConErrores analizador = AnalisisSintactico.analizar(fuente);

        List<String> lineasFuente = Archivos.leerLineas(fuente);

        int numero = 1;
        for (ErrorSintactico error : analizador.errores) {
            VistaErroresSintacticos.mostrar(numero++, error, lineasFuente);
        }

        if (!analizador.errores.isEmpty()) {
            CorreccionGlobal.sugerir(analizador);
            Consola.mostrarEstado(false, "El archivo contiene errores sintácticos.");
        } else if (analizador.interrumpido) {
            Consola.mostrarEstado(false, "El análisis se interrumpió de forma inesperada antes de terminar.");
        } else {
            Consola.mostrarEstado(true, "Análisis sintáctico correcto.");
        }

        return analizador;
    }

    /*
     * Descripcion: Fase 3: guarda la tabla de simbolos y muestra los errores semanticos.
     * Entrada: Tabla de simbolos y archivo fuente.
     * Salida: Archivo donde quedo la tabla.
     * Restricciones: La tabla ya la lleno el parser.
     * Objetivo: Ejecutar y mostrar la fase semantica.
     */
    private static File faseSemantica(TablaSimbolos tabla, File fuente) throws Exception {

        Consola.mostrarSeccion("FASE 3 · ANÁLISIS SEMÁNTICO");

        File reporte = tabla.guardarReporte(fuente, VistaTablaSimbolos.tablaSimbolosParaArchivo(tabla));

        List<String> errores = tabla.getErrores();

        Consola.mostrarConteo("Ámbitos", tabla.getAmbitos().size());
        Consola.mostrarConteo("Símbolos declarados", tabla.cantidadSimbolos());
        Consola.mostrarConteoErrores("Errores semánticos", errores.size());

        if (errores.isEmpty()) {
            Consola.mostrarEstado(true, "Análisis semántico correcto.");
        } else {
            VistaErroresSemanticos.mostrar(errores);
            Consola.mostrarEstado(false, "El archivo contiene errores semánticos.");
        }

        return reporte;
    }

    /*
     * Descripcion: Fase 4: guarda el codigo intermedio.
     * Entrada: Parser, archivo fuente y si no hay errores.
     * Salida: El codigo generado, o null si no se genero.
     * Restricciones: Si hay errores, no se genera.
     * Objetivo: Ejecutar y mostrar la generacion de codigo intermedio.
     */
    private static String faseCodigoIntermedio(ParserConErrores analizador, File fuente, boolean archivoCorrecto)
            throws Exception {

        Consola.mostrarSeccion("FASE 4 · CÓDIGO INTERMEDIO");

        if (!archivoCorrecto) {
            Consola.mostrarEstado(false, "No se generó código intermedio: el archivo tiene errores.");
            return null;
        }

        String codigo = analizador.gen.obtenerCodigo();
        File archivoTac = analizador.gen.guardar(fuente);

        Consola.mostrarConteo("Instrucciones", VistaCodigoIntermedio.contarInstrucciones(codigo));
        Consola.mostrarDato("Archivo", archivoTac.getName());
        Consola.mostrarDato("Carpeta", Archivos.carpetaCorta(archivoTac));

        Consola.mostrarEstado(true, "Código intermedio generado.");

        return codigo;
    }

    /*
     * Descripcion: Fase 5: traduce el codigo intermedio a MIPS.
     * Entrada: Codigo intermedio (null si hubo errores) y archivo fuente.
     * Salida: El codigo MIPS (guardado en salida/mips/<nombre>.asm), o null si no se genero.
     * Restricciones: Si el archivo tiene errores, no se genera MIPS.
     * Objetivo: Generar el codigo destino del programa.
     */
    private static String faseCodigoMIPS(String codigoIntermedio, File fuente) throws Exception {

        Consola.mostrarSeccion("FASE 5 · CÓDIGO MIPS");

        if (codigoIntermedio == null) {
            Consola.mostrarEstado(false, "No se generó código MIPS: el archivo tiene errores.");
            return null;
        }

        String codigoMips = new GeneradorMIPS().traducir(codigoIntermedio);

        /* prueba.cmm -> salida/mips/prueba.asm */
        String nombreAsm = Archivos.nombreSinExtension(fuente) + ".asm";
        File archivoAsm = Archivos.guardarEnSalida("mips", nombreAsm, codigoMips);

        Consola.mostrarDato("Archivo", archivoAsm.getName());
        Consola.mostrarDato("Carpeta", Archivos.carpetaCorta(archivoAsm));
        Consola.mostrarEstado(true, "Código MIPS generado.");

        return codigoMips;
    }

    /*
     * Descripcion: Muestra el resumen de todas las fases.
     * Entrada: Resultados de cada fase y hora de inicio.
     * Salida: Conteo de errores, tiempo y veredicto.
     * Restricciones: Ninguna.
     * Objetivo: Decir si el archivo puede ser generado por la gramatica.
     */
    private static void mostrarResultadoFinal(ResultadoLexico lexico, ParserConErrores analizador,
                                              TablaSimbolos tabla, int erroresTotales,
                                              boolean archivoCorrecto, long inicio) {

        Consola.mostrarSeccion("RESULTADO FINAL");

        Consola.mostrarConteo("Tokens reconocidos", lexico.tokens);
        Consola.mostrarConteoErrores("Errores léxicos", lexico.errores.size());
        Consola.mostrarConteoErrores("Errores sintácticos", analizador.errores.size());
        Consola.mostrarConteoErrores("Errores semánticos", tabla.getErrores().size());

        System.out.println("  " + "─".repeat(24) + " : " + "─".repeat(5));

        Consola.mostrarConteoErrores("Errores totales", erroresTotales);

        System.out.println(String.format("  %-24s : ", "Tiempo de ejecución")
                + Consola.pintar(Consola.CIAN, String.format("%5s", tiempoTranscurrido(inicio))));
        System.out.println();

        if (archivoCorrecto) {
            Consola.mostrarCajaResultado(true, "  ARCHIVO CORRECTO",
                    "El archivo SÍ puede ser generado por la gramática.",
                    "No se encontraron errores léxicos, sintácticos ni semánticos.");
        } else {
            Consola.mostrarCajaResultado(false, "  ARCHIVO CON ERRORES",
                    "El archivo NO puede ser generado por la gramática.",
                    "Corrige los errores indicados y vuelve a ejecutar.");
        }
    }

    /*
     * Descripcion: Calcula cuanto tardo el programa.
     * Entrada: Hora de inicio.
     * Salida: Texto como "850 ms" o "1.250 s".
     * Restricciones: Ninguna.
     * Objetivo: Mostrar cuanto duro la compilacion.
     */
    private static String tiempoTranscurrido(long inicio) {

        long milisegundos = (System.nanoTime() - inicio) / 1_000_000;

        if (milisegundos < 1000) {
            return milisegundos + " ms";
        }

        return (milisegundos / 1000) + "." + String.format("%03d", milisegundos % 1000) + " s";
    }

    /*
     * Descripcion: Pregunta si se quiere ver el codigo intermedio.
     * Entrada: El codigo, o null si no se genero.
     * Salida: El codigo en consola, si se pide.
     * Restricciones: Si no hay codigo, no pregunta.
     * Objetivo: Dejar que el usuario decida si ve el codigo intermedio.
     */
    private static void ofrecerCodigoIntermedio(String codigo) {

        if (codigo == null) {
            return;
        }

        System.out.println();

        if (Consola.preguntarSiNo("¿Desea ver el código intermedio? (s/n): ")) {
            VistaCodigoIntermedio.mostrar(codigo);
        }
    }

    /*
     * Descripcion: Pregunta si se quiere ver el codigo MIPS.
     * Entrada: El codigo MIPS, o null si no se genero.
     * Salida: El codigo en consola, si se pide.
     * Restricciones: Si no hay codigo, no pregunta.
     * Objetivo: Dejar que el usuario decida si ve el codigo MIPS.
     */
    private static void ofrecerCodigoMIPS(String codigo) {

        if (codigo == null) {
            return;
        }

        System.out.println();

        if (Consola.preguntarSiNo("¿Desea ver el código MIPS? (s/n): ")) {
            Consola.mostrarSeccion("CÓDIGO MIPS");
            VistaCodigoMIPS.mostrar(codigo);
        }
    }

    /*
     * Descripcion: Muestra donde quedo la tabla y pregunta si se quiere ver.
     * Entrada: Tabla de simbolos y archivo del reporte.
     * Salida: La tabla en consola, si se pide.
     * Restricciones: Ninguna.
     * Objetivo: Dejar que el usuario decida si ve la tabla de simbolos.
     */
    private static void ofrecerTablaSimbolos(TablaSimbolos tabla, File reporte) {

        System.out.println();
        VistaTablaSimbolos.mostrarCuadroReporte(reporte);
        System.out.println();

        if (Consola.preguntarSiNo("¿Desea ver la tabla de símbolos? (s/n): ")) {
            Consola.mostrarSeccion("TABLA DE SÍMBOLOS");
            VistaTablaSimbolos.mostrar(tabla);
        } else {
            System.out.println();
            System.out.println(Consola.pintar(Consola.GRIS, "  Puede revisarla después en la ruta indicada."));
        }
    }
}
