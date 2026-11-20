import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
 * Descripcion: Rutas y carpetas de los archivos del programa.
 * Entrada: Archivos fuente y de salida.
 * Salida: Rutas listas para usar o para mostrar.
 * Restricciones: Las salidas siempre quedan dentro de la carpeta Programa.
 * Objetivo: No repetir el manejo de rutas en cada fase.
 */
public class Archivos {

    /* Carpeta donde se guardan los tokens, dentro de Programa */
    private static final String CARPETA_TOKENS = "salida" + File.separator + "tokens";

    /*
     * Descripcion: Acorta la ruta de un archivo.
     * Entrada: Archivo.
     * Salida: Ruta desde la carpeta actual.
     * Restricciones: Si no se puede, devuelve solo el nombre.
     * Objetivo: Mostrar rutas cortas y faciles de leer.
     */
    static String rutaCorta(File archivo) {
        try {
            Path base = Paths.get("").toAbsolutePath().normalize();
            Path ruta = archivo.getCanonicalFile().toPath();

            return ruta.startsWith(base) ? base.relativize(ruta).toString() : ruta.toString();

        } catch (Exception e) {
            return archivo.getName();
        }
    }

    /*
     * Descripcion: Arma la ruta del archivo de tokens.
     * Entrada: Archivo fuente.
     * Salida: salida/tokens/<nombre>_tokens.txt.
     * Restricciones: Se usa si no se indico otro archivo.
     * Objetivo: Guardar los tokens sin que el usuario indique la ruta.
     */
    static File archivoTokensPorDefecto(File fuente) {
        return new File(carpetaPrograma(), CARPETA_TOKENS + File.separator + nombreSinExtension(fuente) + "_tokens.txt");
    }

    /*
     * Descripcion: Da el nombre de un archivo sin su extension.
     * Entrada: Archivo, por ejemplo prueba.cmm.
     * Salida: El nombre sin extension, por ejemplo prueba.
     * Restricciones: Si no tiene punto, devuelve el nombre completo.
     * Objetivo: Usar el mismo nombre del fuente en los archivos de salida.
     */
    static String nombreSinExtension(File archivo) {

        String nombre = archivo.getName();
        int punto = nombre.lastIndexOf('.');

        return punto > 0 ? nombre.substring(0, punto) : nombre;
    }

    /*
     * Descripcion: Guarda un texto en un archivo dentro de la carpeta salida.
     * Entrada: Subcarpeta (ej: mips), nombre del archivo y su contenido.
     * Salida: El archivo creado, por ejemplo salida/mips/prueba.asm.
     * Restricciones: Crea la carpeta si no existe. Usa UTF-8.
     * Objetivo: Que todas las fases guarden sus archivos de la misma forma.
     */
    static File guardarEnSalida(String subcarpeta, String nombreArchivo, String contenido) throws IOException {

        File carpeta = new File(carpetaPrograma(), "salida" + File.separator + subcarpeta);
        carpeta.mkdirs();

        File archivo = new File(carpeta, nombreArchivo);
        Files.writeString(archivo.toPath(), contenido, StandardCharsets.UTF_8);

        return archivo;
    }

    /*
     * Descripcion: Busca la carpeta Programa.
     * Entrada: Ninguna.
     * Salida: Carpeta que contiene a bin.
     * Restricciones: Si no se puede, usa la carpeta actual.
     * Objetivo: Guardar las salidas siempre dentro de Programa.
     */
    static File carpetaPrograma() {
        try {
            File carpetaBin = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            return carpetaBin.getParentFile();
        } catch (Exception e) {
            return new File(".");
        }
    }

    /*
     * Descripcion: Da las dos ultimas carpetas de un archivo.
     * Entrada: Archivo.
     * Salida: Texto como "salida/codigo_intermedio".
     * Restricciones: Ninguna.
     * Objetivo: Mostrar donde quedo un archivo sin la ruta completa.
     */
    static String carpetaCorta(File archivo) {

        File carpeta = archivo.getParentFile();

        if (carpeta == null) {
            return "";
        }

        File carpetaSuperior = carpeta.getParentFile();

        return (carpetaSuperior == null ? "" : carpetaSuperior.getName() + File.separator) + carpeta.getName();
    }

    /*
     * Descripcion: Lee todas las lineas del archivo fuente.
     * Entrada: Archivo fuente.
     * Salida: Lista de lineas.
     * Restricciones: Si no se puede leer, devuelve una lista vacia.
     * Objetivo: Mostrar la linea donde ocurrio un error.
     */
    static List<String> leerLineas(File fuente) {
        try {
            return Files.readAllLines(fuente.toPath(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
