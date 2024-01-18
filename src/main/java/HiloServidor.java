import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * La clase HiloServidor implementa la interfaz Runnable y representa un hilo de servidor
 * que acepta conexiones de clientes y gestiona la comunicación con ellos.
 * @author Dante Sarotti, Miriam Betanzos Jamardo
 */
public class HiloServidor implements Runnable {
    final int PUERTO = 12345;
    private static Profesor[] profesores;
    private int lastIdProfesor;
    private int lastIdEspecialidad;
    private int lastIdAsignatura;


    /**
     * Constructor de la clase HiloServidor.
     * Inicializa los contadores de ID y llama al método de inicialización.
     */
    public HiloServidor() {
        this.lastIdProfesor = 0;
        this.lastIdAsignatura = 300;
        this.lastIdEspecialidad = 200;
    }

    /**
     * Método principal que se ejecuta al iniciar el hilo.
     * Llama al método de inicialización.
     */
    @Override
    public void run() {
        init();
    }

    /**
     * Inicializa el array de 5 profesores con una especialidad y 3 asignaturas cada uno.
     * Por simplicidad en este ejercicio no se asignan nombre reales para los objetos, en su lugar se utilizan los
     * valores del índice del bucle.
     * Después ejecuta un bucle para aceptar conexiones.
     */
    private void init() {
        //Inicializar profesores
        profesores = new Profesor[5];
        for (int i = 0; i < 5; i++) {
            int idTempEspe = getNewIdEspecialidad();
            Especialidad espe = new Especialidad(idTempEspe, "especialidad " + idTempEspe);
            String nombreProfesor = "Nombre " + i;
            Asignatura[] asignaturas = new Asignatura[3];
            for (int j = 0; j < 3; j++) {
                int idTempAsignatura = getNewIdAsignatura();
                asignaturas[j] = new Asignatura(idTempAsignatura, "NombreAsignatura" + idTempAsignatura);
            }
            profesores[i] = new Profesor(getNewIdProfesor(), nombreProfesor, asignaturas, espe);
        }

        //Muestra los profesores, especialidad y asignaturas por la salida estándar
        //report();


        try {
            // inicia el bucle para aceptar conexiones entrantes.
            ServerSocket servidorSocket = new ServerSocket(PUERTO);
            new Thread(new GestorSockets(servidorSocket)).start();
            System.out.println("Servidor iniciado...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clase interna para aceptar conexiones de clientes en bucle creando un hilo
     * por cada conexión aceptada.
     */
    static class GestorSockets implements Runnable {
        private final ServerSocket server;

        // Se utiliza AtomicInteger para poder actualizar el valor evitando problemas de
        // concurrencia.
        public static AtomicInteger contadorClientes = new AtomicInteger(1);

        /**
         * Constructor de la clase GestorSockets.
         *
         * @param server El socket del servidor.
         */
        public GestorSockets(ServerSocket server) {
            this.server = server;
        }

        /**
         * Método principal que se ejecuta al iniciar el hilo.
         * Acepta conexiones entrantes y crea un nuevo hilo para cada cliente.
         */
        @Override
        public void run() {
            while (true) {
                try {
                    new Thread(new ManejadorCliente(server.accept(), contadorClientes.getAndIncrement())).start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * La clase ManejadorCliente implementa la interfaz Runnable y se encarga de manejar la comunicación
     * con un cliente específico en un hilo separado.
     */
    static class ManejadorCliente implements Runnable {
        /**
         * Socket del cliente.
         */
        private Socket socketCliente;

        /**
         * Identificador único del cliente.
         */
        private int idCliente;

        /**
         * Hora de conexión del cliente.
         */
        private Timestamp horaConexion;

        /**
         * Constructor de la clase ManejadorCliente.
         *
         * @param socketCliente El socket del cliente.
         * @param idCliente     El identificador único del cliente.
         */
        public ManejadorCliente(Socket socketCliente, int idCliente) {
            this.idCliente = idCliente;
            this.socketCliente = socketCliente;
        }

        /**
         * Método principal que se ejecuta al iniciar el hilo del cliente.
         * Maneja la comunicación con el cliente, enviando información del profesor solicitado.
         */
        @Override
        public void run() {
            ObjetoCompartido logger = ObjetoCompartido.getInstance();
            ObjectOutputStream salidaObjeto = null;

            //Hora del inicio de la conexión
            horaConexion = Timestamp.valueOf(LocalDateTime.now());
            logger.agregarLineaAlFinal("Cliente " + idCliente + " iniciado, (" + ObjetoCompartido.formatearTimestamp(horaConexion) + ")");
            try {
                System.out.println("Cliente " + idCliente + " conectado!");
                salidaObjeto = new ObjectOutputStream(socketCliente.getOutputStream());
                Scanner entradaDatos = new Scanner(socketCliente.getInputStream());

                salidaObjeto.writeInt(idCliente);
                salidaObjeto.flush();

                String idProfeSoli="";
                // Escuchar solicitudes del cliente y enviar el Profesor correspondiente
                while (true) {
                    // Recibir el ID del profesor que el cliente desea obtener
                    try {
                        idProfeSoli = entradaDatos.nextLine();
                        //Si se ha recibido un número, guardarlo y registrarlo en el log
                        logger.agregarLineaAlFinal("    Consultando id: " + Integer.parseInt(idProfeSoli) + ", solicitado por el cliente: " + idCliente);
                    } catch (NoSuchElementException | NumberFormatException e) {
                        if(idProfeSoli.equals("*")) {
                            break;
                        }else{
                            throw new IOException();
                        }
                    }

                    System.out.println(("Consultando id: " + idProfeSoli + ", solicitado por cliente: " + idCliente).indent(4).stripTrailing());
                    // Buscar el profesor con el ID solicitado
                    Profesor profesorEncontrado = buscarProfesorPorId(Integer.parseInt(idProfeSoli));

                    if (profesorEncontrado == null) {
                        Asignatura[] vacio = {};
                        Especialidad espe = new Especialidad(0, "sin datos");
                        profesorEncontrado = new Profesor(0, "No existe", vacio, espe);
                    }
                    // Enviar el objeto Profesor al cliente
                    salidaObjeto.writeObject(profesorEncontrado);
                    salidaObjeto.flush();
                }

            } catch (IOException e) {
                System.err.println("Cliente " + idCliente + " desconectado de forma inesperada");
            } finally {
                try {
                    if (salidaObjeto != null) {
                        salidaObjeto.close();
                    }
                    // Cerrar el socket cuando el cliente cierra la conexión
                    socketCliente.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            System.out.println("Cliente " + idCliente + " desconectado.");
            // Hora de desconexión
            Timestamp horaDesconexion = Timestamp.valueOf(LocalDateTime.now());
            logger.agregarLineaAlFinal("=> FIN con cliente: " + idCliente + ", Tiempo total conectado: " + (horaDesconexion.getTime() - horaConexion.getTime()) + " milisegundos (" + ObjetoCompartido.formatearTimestamp(horaDesconexion) + ")");

        }
    }

    /**
     * Método para buscar un profesor por su ID.
     *
     * @param idProfesor El ID del profesor a buscar.
     * @return El profesor encontrado, o null si no se encuentra.
     */
    private static Profesor buscarProfesorPorId(int idProfesor) {
        for (Profesor profesor : profesores) {
            if (profesor.getIdprofesor() == idProfesor) {
                return profesor;
            }
        }
        return null; // Devolver null si no se encuentra el profesor
    }

    /**
     * Método para obtener un nuevo ID de especialidad.
     *
     * @return El nuevo ID de especialidad.
     */
    private int getNewIdEspecialidad() {
        return lastIdEspecialidad++;
    }

    /**
     * Método para obtener un nuevo ID de profesor.
     *
     * @return El nuevo ID de profesor.
     */
    private int getNewIdProfesor() {
        return lastIdProfesor++;
    }

    /**
     * Método para obtener un nuevo ID de asignatura.
     *
     * @return El nuevo ID de asignatura.
     */
    private int getNewIdAsignatura() {
        return lastIdAsignatura++;
    }

//Comento este método, ya que es útil para debug en el server pero no necesario para la implementación
//    /**
//     * Método para mostrar por la salida estándar los datos de los profesores y sus asignaturas (para debug).
//     */
//    public void report(){
//        StringBuilder builder = new StringBuilder();
//        for (Profesor profesor : profesores) {
//            builder.setLength(0);
//            System.out.println(builder.append("Id: ").append(profesor.getIdprofesor()));
//            builder.setLength(0);
//            System.out.println(builder.append("Nombre: ").append(profesor.getNombre()));
//
//            System.out.println("Especialidad: ");
//            builder.setLength(0);
//            System.out.print((builder.append("Id: ").append(profesor.getEspecialidad().getId())).toString().indent(4));
//            builder.setLength(0);
//            System.out.print((builder.append("Nombre: ").append(profesor.getEspecialidad().getNombreEspecialidad())).toString().indent(4));
//            System.out.println("Asignaturas: ");
//            for (int j = 0; j < profesor.getAsignaturas().length; j++) {
//                builder.setLength(0);
//                System.out.print(builder.append("Id: ").append(profesor.getAsignaturas()[j].getId()).toString().indent(4) );
//                builder.setLength(0);
//                System.out.print(builder.append("Nombre: ").append(profesor.getAsignaturas()[j].getNombreAsignatura()).toString().indent(4));
//            }
//        }
//    }
}