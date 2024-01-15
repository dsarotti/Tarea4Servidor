import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Singleton
 *
 */
public class ObjetoCompartido {

    static ObjetoCompartido objetoCompartidoInstance;
    private File logFile;

    private static File inicializarCustomLogger() {
        // Nombre del archivo a verificar/crear
        String nombreArchivo = "FichLog.txt";

        // Verificar si el archivo existe
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            try {
                // Crear el archivo si no existe
                archivo.createNewFile();
                System.out.println("Se ha creado el archivo de log " + nombreArchivo);
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de log " + nombreArchivo);
                e.printStackTrace();
            }
        }
        return archivo;
    }

    public void agregarLineaAlFinal(String nuevaLinea) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(logFile, true));

            // Usamos true como segundo parámetro para indicar que queremos abrir el archivo en modo "append"
            // Es decir, que se añadan nuevas líneas al final del archivo

            // Escribimos la nueva línea al final del archivo
            writer.write(nuevaLinea );
            writer.newLine(); // Añadimos un salto de línea después de la nueva línea
            writer.flush();
            System.out.println("Se ha añadido la línea al archivo.");

        } catch (IOException e) {
            System.err.println("Error al añadir la línea al archivo.");
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

    public static String formatearTimestamp(Timestamp timestamp) {
        // Se crea un objeto SimpleDateFormat con el formato deseado
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Se convierte el Timestamp a un objeto Date
        Date date = new Date(timestamp.getTime());

        // Se formatea la fecha utilizando el objeto SimpleDateFormat
        return formatoFecha.format(date);
    }

    private ObjetoCompartido() {
        logFile = inicializarCustomLogger();
    }

    public static ObjetoCompartido getInstance() {
        if (objetoCompartidoInstance == null) {
            objetoCompartidoInstance = new ObjetoCompartido();
        }
        return objetoCompartidoInstance;
    }
}