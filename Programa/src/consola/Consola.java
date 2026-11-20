import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 * Descripcion: Todo lo que se muestra en la consola: colores, titulos, cuadros y preguntas.
 * Entrada: Textos y datos que se quieren mostrar.
 * Salida: Lineas en la consola.
 * Restricciones: Los colores se apagan con la variable de entorno NO_COLOR.
 * Objetivo: Que todas las fases muestren la informacion con el mismo estilo.
 */
public class Consola {

    /* Ancho de los titulos, cuadros y textos de la consola */
    static final int ANCHO = 76;

    static final String NEGRITA = "1";

    static final String SIN_COLOR = "0";

    static final String GRIS = "90";

    static final String BLANCO = "1;37";

    static final String ROJO = "1;31";

    static final String ROJO_SUAVE = "31";

    static final String VERDE = "1;32";

    static final String VERDE_SUAVE = "32";

    static final String AMARILLO = "1;33";

    static final String AMARILLO_SUAVE = "33";

    static final String CIAN = "1;36";

    static final String CIAN_SUAVE = "36";

    static final String MAGENTA_SUAVE = "35";

    static final String AZUL = "34;1";

    /* Los colores se apagan definiendo la variable de entorno NO_COLOR (por ejemplo: set NO_COLOR=1) */
    private static final boolean USAR_COLORES = detectarColores();

    /* Los codigos ANSI de color: ESC[36m, ESC[0m... */
    private static final Pattern PATRON_COLOR = Pattern.compile("\u001B\\[[0-9;]*m");

    /* El teclado se abre una sola vez: dos Scanner sobre System.in se quitan la entrada */
    private static Scanner teclado = null;

    /*
     * Descripcion: Revisa si la consola puede mostrar colores.
     * Entrada: Ninguna.
     * Salida: true si se pueden usar colores.
     * Restricciones: Se apagan si existe la variable NO_COLOR.
     * Objetivo: Mostrar colores solo cuando la consola los entiende.
     */
    private static boolean detectarColores() {

        if (System.getenv("NO_COLOR") != null) {
            return false;
        }

        String sistema = System.getProperty("os.name", "").toLowerCase();

        if (sistema.contains("win")) {
            activarColoresEnWindows();
            return true;
        }

        String terminal = System.getenv("TERM");
        return terminal != null && !terminal.equals("dumb");
    }

    /*
     * Descripcion: Prepara la consola de Windows para los colores.
     * Entrada: Ninguna.
     * Salida: Ninguna.
     * Restricciones: Si falla, solo no se ven colores.
     * Objetivo: Que los colores se vean en la consola de Windows.
     */
    private static void activarColoresEnWindows() {
        try {
            new ProcessBuilder("cmd", "/c", "rem").inheritIO().start().waitFor();
        } catch (Exception e) {
            /* Si falla, simplemente no se ven colores */
        }
    }

    /*
     * Descripcion: Le pone color a un texto.
     * Entrada: Color y texto.
     * Salida: El texto con color.
     * Restricciones: Si no hay colores, devuelve el texto igual.
     * Objetivo: Resaltar partes importantes en la consola.
     */
    static String pintar(String color, String texto) {
        return USAR_COLORES ? "\u001B[" + color + "m" + texto + "\u001B[0m" : texto;
    }

    /*
     * Descripcion: Quita los colores de un texto.
     * Entrada: Texto con colores.
     * Salida: Texto sin colores.
     * Restricciones: Se usa antes de guardar en un archivo.
     * Objetivo: Guardar archivos de texto limpios.
     */
    static String quitarColores(String texto) {
        return PATRON_COLOR.matcher(texto).replaceAll("");
    }

    /*
     * Descripcion: Muestra un titulo dentro de un marco.
     * Entrada: Texto del titulo.
     * Salida: Titulo en consola.
     * Restricciones: El texto debe caber en el ancho.
     * Objetivo: Mostrar el titulo principal del programa.
     */
    static void mostrarTitulo(String texto) {

        int libre = ANCHO - 2 - texto.length();
        int izquierda = Math.max(0, libre / 2);
        int derecha = Math.max(0, libre - izquierda);

        System.out.println();
        System.out.println(pintar(CIAN, "╔" + "═".repeat(ANCHO - 2) + "╗"));
        System.out.println(pintar(CIAN, "║" + " ".repeat(izquierda) + texto + " ".repeat(derecha) + "║"));
        System.out.println(pintar(CIAN, "╚" + "═".repeat(ANCHO - 2) + "╝"));
    }

    /*
     * Descripcion: Muestra la linea que separa cada fase.
     * Entrada: Nombre de la seccion.
     * Salida: Linea de titulo en consola.
     * Restricciones: Ninguna.
     * Objetivo: Separar visualmente cada fase.
     */
    static void mostrarSeccion(String texto) {

        String cabecera = "━━ " + texto + " ";

        System.out.println();
        System.out.println(pintar(CIAN, cabecera + "━".repeat(Math.max(3, ANCHO - cabecera.length()))));
        System.out.println();
    }

    /*
     * Descripcion: Muestra un cuadro verde o rojo con mensajes.
     * Entrada: Si es correcto y las lineas de texto.
     * Salida: Cuadro en consola.
     * Restricciones: Cada linea debe caber en el cuadro.
     * Objetivo: Resaltar el resultado final.
     */
    static void mostrarCajaResultado(boolean correcto, String... lineas) {

        String color = correcto ? VERDE : ROJO;

        System.out.println(pintar(color, "┌" + "─".repeat(ANCHO - 2) + "┐"));

        for (String linea : lineas) {
            System.out.println(pintar(color, String.format("│ %-" + (ANCHO - 4) + "s │", linea)));
        }

        System.out.println(pintar(color, "└" + "─".repeat(ANCHO - 2) + "┘"));
    }

    /*
     * Descripcion: Muestra una etiqueta con su valor.
     * Entrada: Etiqueta y valor.
     * Salida: Linea en consola.
     * Restricciones: Ninguna.
     * Objetivo: Mostrar datos alineados en la consola.
     */
    static void mostrarDato(String etiqueta, Object valor) {
        System.out.printf("  %-24s : %s%n", etiqueta, valor);
    }

    /*
     * Descripcion: Muestra un numero con su etiqueta.
     * Entrada: Etiqueta y numero.
     * Salida: Linea en consola.
     * Restricciones: Ninguna.
     * Objetivo: Mostrar cantidades alineadas en la consola.
     */
    static void mostrarConteo(String etiqueta, int valor) {
        System.out.println(String.format("  %-24s : ", etiqueta) + pintar(CIAN, String.format("%5d", valor)));
    }

    /*
     * Descripcion: Muestra una cantidad de errores.
     * Entrada: Etiqueta y cantidad.
     * Salida: Linea en consola.
     * Restricciones: Rojo si hay errores, verde si no.
     * Objetivo: Ver rapido si una fase tuvo errores.
     */
    static void mostrarConteoErrores(String etiqueta, int valor) {
        String color = valor > 0 ? ROJO : VERDE;
        System.out.println(String.format("  %-24s : ", etiqueta) + pintar(color, String.format("%5d", valor)));
    }

    /*
     * Descripcion: Muestra si una fase salio bien o mal.
     * Entrada: Si es correcto y el mensaje.
     * Salida: Mensaje con ✔ o ✖.
     * Restricciones: Ninguna.
     * Objetivo: Decir si una fase salio bien o mal.
     */
    static void mostrarEstado(boolean correcto, String mensaje) {
        System.out.println();
        System.out.println(pintar(correcto ? VERDE : ROJO, "  " + (correcto ? "✔ " : "✖ ") + mensaje));
    }

    /*
     * Descripcion: Muestra una fila dentro de un cuadro.
     * Entrada: Texto sin color, texto con color y color del borde.
     * Salida: Fila en consola.
     * Restricciones: El texto sin color sirve para medir el espacio.
     * Objetivo: Armar cuadros con bordes alineados.
     */
    static void filaCuadro(String textoPlano, String textoConColor, String colorBorde) {

        int relleno = Math.max(0, ANCHO - 4 - textoPlano.length());

        System.out.println(pintar(colorBorde, "│") + " " + textoConColor
                + " ".repeat(relleno) + " " + pintar(colorBorde, "│"));
    }

    /*
     * Descripcion: Corta un texto largo en varias lineas.
     * Entrada: Texto y ancho maximo.
     * Salida: Lista de lineas.
     * Restricciones: No parte palabras, salvo si una sola no cabe.
     * Objetivo: Que los textos largos quepan en la consola.
     */
    static List<String> partirEnLineas(String texto, int ancho) {

        List<String> lineas = new ArrayList<>();
        StringBuilder actual = new StringBuilder();

        for (String palabra : texto.split(" ")) {

            while (palabra.length() > ancho) {
                if (actual.length() > 0) {
                    lineas.add(actual.toString());
                    actual.setLength(0);
                }
                lineas.add(palabra.substring(0, ancho));
                palabra = palabra.substring(ancho);
            }

            if (actual.length() > 0 && actual.length() + 1 + palabra.length() > ancho) {
                lineas.add(actual.toString());
                actual.setLength(0);
            }

            if (actual.length() > 0) {
                actual.append(' ');
            }
            actual.append(palabra);
        }

        if (actual.length() > 0 || lineas.isEmpty()) {
            lineas.add(actual.toString());
        }

        return lineas;
    }

    /*
     * Descripcion: Hace una pregunta de si o no.
     * Entrada: Texto de la pregunta.
     * Salida: true si responde s, false si responde n.
     * Restricciones: Repite hasta recibir una respuesta valida.
     * Objetivo: Hacer preguntas al usuario en la consola.
     */
    static boolean preguntarSiNo(String pregunta) {

        if (teclado == null) {
            teclado = new Scanner(System.in, StandardCharsets.UTF_8);
        }

        while (true) {

            System.out.print(pintar(AMARILLO, "  " + pregunta));
            System.out.flush();

            if (!teclado.hasNextLine()) {
                System.out.println();
                return false;
            }

            String respuesta = teclado.nextLine().trim().toLowerCase();

            if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
                return true;
            }

            if (respuesta.equals("n") || respuesta.equals("no")) {
                return false;
            }

            System.out.println(pintar(ROJO_SUAVE, "  Respuesta no válida. Escriba s o n."));
        }
    }

    /*
     * Descripcion: Apaga la consola mientras trabaja el Lexer.
     * Entrada: Ninguna.
     * Salida: La consola original, para volver a encenderla.
     * Restricciones: Hay que restaurarla al terminar.
     * Objetivo: Que el Lexer y el parser no impriman mensajes propios.
     */
    static PrintStream silenciarConsola() {
        PrintStream original = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
        return original;
    }
}
