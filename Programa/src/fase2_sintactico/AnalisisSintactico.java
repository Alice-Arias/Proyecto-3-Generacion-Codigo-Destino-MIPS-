import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/*
 * Descripcion: Fase 2: ejecuta el parser sobre el archivo fuente.
 * Entrada: El archivo fuente.
 * Salida: El parser ya ejecutado, con sus errores, tabla y codigo intermedio.
 * Restricciones: Usa el parser generado por CUP.
 * Objetivo: Encargarse solo de correr el analisis sintactico.
 */
public class AnalisisSintactico {

    /*
     * Descripcion: Ejecuta el parser sobre el archivo.
     * Entrada: Archivo fuente.
     * Salida: El parser ya ejecutado.
     * Restricciones: Si el parser falla, queda marcado como interrumpido.
     * Objetivo: Revisar la estructura del programa.
     */
    static ParserConErrores analizar(File fuente) throws Exception {

        PrintStream consola = Consola.silenciarConsola();

        try (Reader lector = new InputStreamReader(new FileInputStream(fuente), StandardCharsets.UTF_8)) {

            ParserConErrores analizador = new ParserConErrores(new Lexer(lector));

            try {
                analizador.parse();
            } catch (Exception e) {
                analizador.interrumpido = true;
            }

            return analizador;

        } finally {
            System.setOut(consola);
        }
    }
}
