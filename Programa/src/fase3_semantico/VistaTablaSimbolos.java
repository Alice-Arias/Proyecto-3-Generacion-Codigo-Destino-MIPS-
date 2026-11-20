import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Descripcion: Muestra y prepara la tabla de simbolos.
 * Entrada: La tabla de simbolos.
 * Salida: La tabla en consola o como texto para un archivo.
 * Restricciones: La tabla es ancha: conviene una terminal grande.
 * Objetivo: Revisar los simbolos y ambitos del programa.
 */
public class VistaTablaSimbolos {

    /* Columnas de la tabla de simbolos en consola y su ancho */
    private static final String[] TITULOS_TABLA = {
        "Nº", "NOMBRE", "CATEGORÍA", "TIPO", "ÁMBITO", "BYTES",
        "DIMENSIÓN", "DIRECCIÓN", "VALOR INICIAL", "ACCESO", "LÍNEA", "COL"};

    private static final int[] ANCHOS_TABLA = {3, 18, 13, 7, 24, 5, 10, 11, 22, 8, 5, 3};

    /* Una linea del arbol: "  ├── funcion suma  (3 símbolos)" */
    private static final Pattern PATRON_LINEA_ARBOL = Pattern.compile("^([\\s│├└─]*)(.*?)  \\((\\d+) (símbolos?)\\)$");

    /*
     * Descripcion: Muestra donde quedo guardada la tabla de simbolos.
     * Entrada: Archivo del reporte.
     * Salida: Cuadro verde en consola.
     * Restricciones: Ninguna.
     * Objetivo: Decir donde se guardo la tabla de simbolos.
     */
    static void mostrarCuadroReporte(File reporte) {

        String titulo = " TABLA DE SÍMBOLOS GENERADA ";
        String mensaje = "✔ La tabla de símbolos se guardó correctamente.";

        System.out.println(Consola.pintar(Consola.VERDE, "┌─" + titulo
                + "─".repeat(Math.max(1, Consola.ANCHO - 3 - titulo.length())) + "┐"));

        Consola.filaCuadro("", "", Consola.VERDE);
        Consola.filaCuadro(mensaje, Consola.pintar(Consola.VERDE, mensaje), Consola.VERDE);
        Consola.filaCuadro("", "", Consola.VERDE);

        mostrarDatoEnCuadro("Archivo", reporte.getName(), Consola.BLANCO, Consola.VERDE);
        mostrarDatoEnCuadro("Carpeta", Archivos.carpetaCorta(reporte), Consola.CIAN_SUAVE, Consola.VERDE);
        mostrarDatoEnCuadro("Ruta", reporte.getAbsolutePath(), Consola.GRIS, Consola.VERDE);

        Consola.filaCuadro("", "", Consola.VERDE);
        System.out.println(Consola.pintar(Consola.VERDE, "└" + "─".repeat(Consola.ANCHO - 2) + "┘"));
    }

    /*
     * Descripcion: Muestra un dato dentro de un cuadro.
     * Entrada: Etiqueta, valor, color y color del borde.
     * Salida: Una o varias filas.
     * Restricciones: Las rutas largas se cortan despues de una barra.
     * Objetivo: Mostrar rutas largas dentro del cuadro.
     */
    private static void mostrarDatoEnCuadro(String etiqueta, String valor, String color, String colorBorde) {

        String prefijo = String.format("%-8s : ", etiqueta);
        int ancho = Consola.ANCHO - 4 - prefijo.length();

        List<String> partes = new ArrayList<>();
        String resto = valor;

        while (resto.length() > ancho) {

            int ultimaBarra = Math.max(resto.lastIndexOf(File.separatorChar, ancho - 1),
                                       resto.lastIndexOf('/', ancho - 1));

            int corte = ultimaBarra > 0 ? ultimaBarra + 1 : ancho;

            partes.add(resto.substring(0, corte));
            resto = resto.substring(corte);
        }

        partes.add(resto);

        for (int i = 0; i < partes.size(); i++) {

            String inicio = i == 0 ? prefijo : " ".repeat(prefijo.length());
            String inicioConColor = i == 0 ? Consola.pintar(Consola.NEGRITA, inicio) : inicio;

            Consola.filaCuadro(inicio + partes.get(i), inicioConColor + Consola.pintar(color, partes.get(i)), colorBorde);
        }
    }

    /*
     * Descripcion: Muestra la tabla de simbolos en consola.
     * Entrada: Tabla de simbolos.
     * Salida: Arbol de ambitos y tabla.
     * Restricciones: Es ancha: conviene una terminal grande.
     * Objetivo: Revisar la tabla de simbolos en consola.
     */
    static void mostrar(TablaSimbolos tabla) {

        for (String linea : formatearTablaSimbolos(tabla)) {
            System.out.println(linea);
        }
    }

    /*
     * Descripcion: Prepara la tabla para guardarla en un archivo.
     * Entrada: Tabla de simbolos.
     * Salida: La tabla como texto, sin colores.
     * Restricciones: Queda igual que en consola.
     * Objetivo: Guardar la tabla en un archivo de texto.
     */
    static String tablaSimbolosParaArchivo(TablaSimbolos tabla) {

        StringBuilder texto = new StringBuilder();

        for (String linea : formatearTablaSimbolos(tabla)) {
            texto.append(Consola.quitarColores(linea)).append("\n");
        }

        return texto.toString();
    }

    /*
     * Descripcion: Arma la tabla de simbolos linea por linea.
     * Entrada: Tabla de simbolos.
     * Salida: Lista de lineas.
     * Restricciones: La usan la consola y el archivo.
     * Objetivo: Usar el mismo formato en consola y en archivo.
     */
    private static List<String> formatearTablaSimbolos(TablaSimbolos tabla) {

        List<String> lineas = new ArrayList<>();

        lineas.add(Consola.pintar(Consola.NEGRITA, "  Árbol de ámbitos"));
        lineas.add("");

        for (String linea : tabla.arbolDeAmbitos().split("\n")) {
            lineas.add(colorearLineaArbol(linea));
        }

        lineas.add("");

        lineas.add("  " + Consola.pintar(Consola.NEGRITA, "Categorías: ")
                + Consola.pintar(colorCategoria("variable"), "variable") + "  "
                + Consola.pintar(colorCategoria("arreglo"), "arreglo") + "  "
                + Consola.pintar(colorCategoria("parámetro"), "parámetro") + "  "
                + Consola.pintar(colorCategoria("función"), "función") + "  "
                + Consola.pintar(colorCategoria("procedimiento"), "procedimiento"));

        lineas.add(Consola.pintar(Consola.GRIS, "  Dirección:   global+n = zona estática                 ----   pila+n = registro de activación"));
        lineas.add(Consola.pintar(Consola.GRIS, "  Acceso:      público = declarado en el ámbito global ------- privado = dentro de una función o bloque"));
        lineas.add("");

        lineas.add(Consola.pintar(Consola.GRIS, "  " + bordeTabla('┌', '┬', '┐')));

        String[] coloresTitulos = new String[TITULOS_TABLA.length];
        java.util.Arrays.fill(coloresTitulos, Consola.CIAN);
        filaTabla(lineas, TITULOS_TABLA, coloresTitulos);

        lineas.add(Consola.pintar(Consola.GRIS, "  " + bordeTabla('├', '┼', '┤')));

        int numero = 1;
        boolean esPrimerAmbito = true;

        for (Ambito ambito : tabla.getAmbitos()) {

            /* Un ambito sin simbolos no tiene filas: solo aparece en el arbol */
            if (ambito.cantidad() == 0) {
                continue;
            }

            if (!esPrimerAmbito) {
                lineas.add(Consola.pintar(Consola.GRIS, "  " + bordeTabla('├', '┼', '┤')));
            }
            esPrimerAmbito = false;

            String nombreAmbito = nombreCortoAmbito(ambito);

            for (Simbolo simbolo : ambito.simbolos()) {
                filaTabla(lineas, celdasDelSimbolo(numero++, simbolo, nombreAmbito), coloresDelSimbolo(simbolo));
            }
        }

        lineas.add(Consola.pintar(Consola.GRIS, "  " + bordeTabla('└', '┴', '┘')));
        lineas.add("");

        return lineas;
    }

    /*
     * Descripcion: Le pone color a una linea del arbol de ambitos.
     * Entrada: Linea del arbol.
     * Salida: La linea con colores.
     * Restricciones: Usa los mismos colores de las categorias de la tabla:
     *                funciones en azul, el procedimiento en cian, las ramas en gris y
     *                la cantidad en verde si el bloque tiene simbolos.
     * Objetivo: Que el arbol de ambitos se lea facil.
     */
    private static String colorearLineaArbol(String linea) {

        Matcher partes = PATRON_LINEA_ARBOL.matcher(linea);

        if (!partes.matches()) {
            return linea;
        }

        String ramas = partes.group(1);
        String nombre = partes.group(2);
        int cantidad = Integer.parseInt(partes.group(3));
        String cantidadTexto = "(" + cantidad + " " + partes.group(4) + ")";

        return Consola.pintar(Consola.GRIS, ramas)
                + Consola.pintar(colorDelAmbito(nombre), nombre) + "  "
                + Consola.pintar(cantidad > 0 ? Consola.VERDE_SUAVE : Consola.GRIS, cantidadTexto);
    }

    /*
     * Descripcion: Elige el color del nombre de un ambito.
     * Entrada: Nombre del ambito.
     * Salida: Color.
     * Restricciones: Los bloques (if, while, for...) quedan sin color.
     * Objetivo: Distinguir funciones, procedimientos y bloques.
     */
    private static String colorDelAmbito(String nombre) {

        if (nombre.equals("global")) {
            return Consola.NEGRITA;
        }
        if (nombre.startsWith("funcion ")) {
            return colorCategoria("función");
        }
        if (nombre.startsWith("procedimiento ")) {
            return colorCategoria("procedimiento");
        }
        return Consola.SIN_COLOR;
    }

    /*
     * Descripcion: Arma las celdas de una fila de la tabla.
     * Entrada: Numero, simbolo y nombre del ambito.
     * Salida: Textos de cada columna.
     * Restricciones: Las funciones no tienen bytes, dimension ni valor inicial.
     * Objetivo: Llenar una fila de la tabla de simbolos.
     */
    private static String[] celdasDelSimbolo(int numero, Simbolo simbolo, String nombreAmbito) {

        boolean esFuncion = simbolo.esFuncion();

        return new String[] {
            String.valueOf(numero),
            simbolo.nombre,
            simbolo.categoria,
            simbolo.tipo,
            nombreAmbito,
            esFuncion ? "—" : (simbolo.bytes > 0 ? String.valueOf(simbolo.bytes) : "?"),
            esFuncion ? "—" : simbolo.textoDimensionCorta(),
            simbolo.direccion(),
            esFuncion || simbolo.valorInicial == null ? "—" : simbolo.valorInicial,
            simbolo.acceso,
            String.valueOf(simbolo.linea),
            String.valueOf(simbolo.columna)
        };
    }

    /*
     * Descripcion: Elige el color de cada celda de una fila.
     * Entrada: Simbolo.
     * Salida: Color de cada columna.
     * Restricciones: Ninguna.
     * Objetivo: Resaltar los datos importantes de cada simbolo.
     */
    private static String[] coloresDelSimbolo(Simbolo simbolo) {
        return new String[] {
            Consola.SIN_COLOR,
            Consola.NEGRITA,
            colorCategoria(simbolo.categoria),
            Consola.SIN_COLOR,
            Consola.CIAN_SUAVE,
            Consola.SIN_COLOR,
            "arreglo".equals(simbolo.categoria) ? Consola.AMARILLO_SUAVE : Consola.GRIS,
            Consola.SIN_COLOR,
            Consola.SIN_COLOR,
            "público".equals(simbolo.acceso) ? Consola.VERDE_SUAVE : Consola.GRIS,
            Consola.SIN_COLOR,
            Consola.SIN_COLOR
        };
    }

    /*
     * Descripcion: Acorta el nombre de un ambito.
     * Entrada: Ambito.
     * Salida: Texto como "funcion f1 › if".
     * Restricciones: El global se queda como "global".
     * Objetivo: Que el nombre del ambito quepa en la tabla.
     */
    private static String nombreCortoAmbito(Ambito ambito) {

        String ruta = ambito.ruta();

        if (ambito.nivel > 0 && ruta.startsWith("global › ")) {
            ruta = ruta.substring("global › ".length());
        }

        return ruta.replace("funcion ", "función ");
    }

    /*
     * Descripcion: Arma una linea de borde de la tabla.
     * Entrada: Caracteres de izquierda, medio y derecha.
     * Salida: Linea como ┌───┬───┐.
     * Restricciones: Ninguna.
     * Objetivo: Dibujar los bordes de la tabla.
     */
    private static String bordeTabla(char izquierda, char medio, char derecha) {

        StringBuilder borde = new StringBuilder().append(izquierda);

        for (int i = 0; i < ANCHOS_TABLA.length; i++) {
            borde.append("─".repeat(ANCHOS_TABLA[i] + 2));
            borde.append(i < ANCHOS_TABLA.length - 1 ? medio : derecha);
        }

        return borde.toString();
    }

    /*
     * Descripcion: Agrega una fila a la tabla.
     * Entrada: Lista de salida, celdas y colores.
     * Salida: Una o varias lineas agregadas.
     * Restricciones: Si una celda es larga, la fila ocupa varias lineas.
     * Objetivo: Que las celdas largas no rompan la tabla.
     */
    private static void filaTabla(List<String> salida, String[] celdas, String[] colores) {

        List<List<String>> lineasPorCelda = new ArrayList<>();
        int alto = 1;

        for (int i = 0; i < ANCHOS_TABLA.length; i++) {
            List<String> lineas = Consola.partirEnLineas(celdas[i], ANCHOS_TABLA[i]);
            lineasPorCelda.add(lineas);
            alto = Math.max(alto, lineas.size());
        }

        for (int fila = 0; fila < alto; fila++) {

            StringBuilder linea = new StringBuilder("  " + Consola.pintar(Consola.GRIS, "│"));

            for (int i = 0; i < ANCHOS_TABLA.length; i++) {

                List<String> lineasCelda = lineasPorCelda.get(i);
                String contenido = fila < lineasCelda.size() ? lineasCelda.get(fila) : "";
                String texto = String.format("%-" + ANCHOS_TABLA[i] + "s", contenido);

                linea.append(" ")
                     .append(Consola.SIN_COLOR.equals(colores[i]) ? texto : Consola.pintar(colores[i], texto))
                     .append(" ")
                     .append(Consola.pintar(Consola.GRIS, "│"));
            }

            salida.add(linea.toString());
        }
    }

    /*
     * Descripcion: Elige el color de una categoria.
     * Entrada: Categoria.
     * Salida: Color.
     * Restricciones: Ninguna.
     * Objetivo: Usar siempre el mismo color para cada categoria.
     */
    private static String colorCategoria(String categoria) {
        switch (categoria) {
            case "variable":      return Consola.VERDE_SUAVE;
            case "arreglo":       return Consola.AMARILLO_SUAVE;
            case "parámetro":     return Consola.MAGENTA_SUAVE;
            case "función":       return Consola.AZUL;
            case "procedimiento": return Consola.CIAN;
            default:              return Consola.SIN_COLOR;
        }
    }
}
