import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * La clase ObjetoCompartido implementa un patrón Singleton y proporciona funcionalidades
 * para el manejo de un archivo de registro (log) compartido entre múltiples hilos.
 */
public class ObjetoCompartido {

    /**
     * Instancia única de la clase
     */
    static ObjetoCompartido objetoCompartidoInstance;

    /**
     * Archivo de registro (log)
     */
    private File logFile;

    /**
     * Constructor privado que inicializa el archivo de log.
     */
    private ObjetoCompartido() {
        logFile = inicializarArchivoLog();
    }

    /**
     * Método privado para inicializar el archivo de log.
     * Verifica la existencia del archivo y lo crea si no existe.
     *
     * @return El archivo de log.
     */
    private static File inicializarArchivoLog() {
        // Nombre del archivo a verificar/crear
        String nombreArchivo = "FichLog.txt";

        // Verificar si el archivo existe
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            try {
                // Crear el archivo si no existe
                archivo.createNewFile();

            } catch (IOException e) {
                System.err.println("Error al crear el archivo de log " + nombreArchivo);
                e.printStackTrace();
            }
        }
        return archivo;
    }

    /**
     * Agrega una nueva línea al final del archivo de log.
     *
     * @param nuevaLinea La línea a agregar al archivo de log.
     */
    public synchronized void agregarLineaAlFinal(String nuevaLinea) {
        BufferedWriter writer = null;
        try {

            // Abre el archivo en modo "append" para añadir nuevas líneas al final
            writer = new BufferedWriter(new FileWriter(logFile, true));

            writer.write(nuevaLinea); // Escribe la nueva línea al final del archivo
            writer.newLine(); // Añade un salto de línea después de la nueva línea
            writer.flush();

        } catch (IOException e) {
            System.err.println("Error al añadir la línea al archivo de log.");
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Formatea un objeto Timestamp como una cadena con un formato específico.
     *
     * @param timestamp El Timestamp a formatear.
     * @return La cadena formateada.
     */
    public static String formatearTimestamp(Timestamp timestamp) {
        // Se crea un objeto SimpleDateFormat con el formato deseado
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Se convierte el Timestamp a un objeto Date
        Date date = new Date(timestamp.getTime());

        // Se formatea la fecha utilizando el objeto SimpleDateFormat
        return formatoFecha.format(date);
    }

    /**
     * Método estático que devuelve la instancia única de la clase ObjetoCompartido.
     *
     * @return La instancia única de ObjetoCompartido.
     */
    public static ObjetoCompartido getInstance() {
        if (objetoCompartidoInstance == null) {
            synchronized (ObjetoCompartido.class) {
                if (objetoCompartidoInstance == null) {
                    objetoCompartidoInstance = new ObjetoCompartido();
                }
            }
        }
        return objetoCompartidoInstance;
    }
}