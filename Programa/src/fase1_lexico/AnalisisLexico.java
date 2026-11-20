import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java_cup.runtime.Symbol;

/*
 * Descripcion: Fase 1: lee los tokens del archivo y muestra los errores lexicos.
 * Entrada: El archivo fuente.
 * Salida: Cantidad de tokens, errores lexicos y el archivo de tokens.
 * Restricciones: Usa el Lexer generado por JFlex.
 * Objetivo: Encargarse solo de la fase lexica.
 */
public class AnalisisLexico {

    /*
     * Descripcion: Lee todos los tokens y los guarda en un archivo.
     * Entrada: Archivo fuente y archivo de tokens.
     * Salida: Cantidad de tokens y errores lexicos.
     * Restricciones: Crea la carpeta de tokens si no existe.
     * Objetivo: Obtener los tokens y los errores lexicos.
     */
    static ResultadoLexico analizar(File fuente, File archivoTokens) throws Exception {

        File carpetaTokens = archivoTokens.getAbsoluteFile().getParentFile();

        if (carpetaTokens != null) {
            carpetaTokens.mkdirs();
        }

        PrintStream consola = Consola.silenciarConsola();

        try (Reader lector = new InputStreamReader(new FileInputStream(fuente), StandardCharsets.UTF_8);
             PrintWriter salida = new PrintWriter(archivoTokens, "UTF-8")) {

            Lexer lexer = new Lexer(lector);

            salida.printf("%-8s %-8s %-18s %s%n", "LINEA", "COLUMNA", "TOKEN", "LEXEMA");
            salida.println("-".repeat(60));

            ResultadoLexico resultado = new ResultadoLexico();

            Symbol token = lexer.next_token();

            while (token.sym != sym.EOF) {
                salida.printf("%-8d %-8d %-18s %s%n", token.left, token.right, Tokens.nombreToken(token.sym), token.value);
                resultado.tokens++;
                token = lexer.next_token();
            }

            resultado.errores = lexer.getErrores();

            if (!resultado.errores.isEmpty()) {
                salida.println();
                salida.println("ERRORES LEXICOS (" + resultado.errores.size() + ")");
                salida.println("-".repeat(60));

                for (String error : resultado.errores) {
                    salida.println(error);
                }
            }

            return resultado;

        } finally {
            System.setOut(consola);
        }
    }

    /*
     * Descripcion: Muestra la tabla de errores lexicos.
     * Entrada: Lista de errores.
     * Salida: Tabla en consola.
     * Restricciones: Ninguna.
     * Objetivo: Ver los errores lexicos de forma ordenada.
     */
    static void mostrarErrores(List<String> errores) {

        System.out.println();
        System.out.println(Consola.pintar(Consola.CIAN, String.format("  %-4s %6s %5s   %s", "Nº", "LÍNEA", "COL", "DESCRIPCIÓN")));
        System.out.println(Consola.pintar(Consola.GRIS, "  " + "─".repeat(4) + " " + "─".repeat(6) + " " + "─".repeat(5)
                + "   " + "─".repeat(Consola.ANCHO - 24)));

        int numero = 1;

        for (String error : errores) {

            PartesError partes = PartesError.separar(error);
            String textoNumero = String.format("%02d", numero++);

            String prefijoPlano = String.format("  %-4s %6s %5s   ", textoNumero, partes.linea, partes.columna);

            String prefijoConColor = "  "
                    + Consola.pintar(Consola.GRIS, String.format("%-4s", textoNumero)) + " "
                    + Consola.pintar(Consola.AMARILLO_SUAVE, String.format("%6s", partes.linea)) + " "
                    + Consola.pintar(Consola.AMARILLO_SUAVE, String.format("%5s", partes.columna)) + "   ";

            List<String> lineas = Consola.partirEnLineas(partes.detalle, Consola.ANCHO - prefijoPlano.length());

            for (int i = 0; i < lineas.size(); i++) {
                String prefijo = i == 0 ? prefijoConColor : " ".repeat(prefijoPlano.length());
                System.out.println(prefijo + lineas.get(i));
            }
        }
    }
}
