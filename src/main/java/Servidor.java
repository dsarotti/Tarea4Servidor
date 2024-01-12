import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Servidor
{
    final int PUERTO = 12345;
    private static Profesor[] profesores;
    private int lastIdProfesor;
    private int lastIdEspecialidad;
    private int lastIdAsignatura;

    public Servidor() throws IOException {
        this.lastIdProfesor =0;
        this.lastIdAsignatura=300;
        this.lastIdEspecialidad=200;
    }

    /**
     * Inicializa el array de 5 profesores con una especialidad y 3 asignaturas cada uno.
     */
    public void init(){
        profesores=new Profesor[5];
        //Inicializar profesores
        for(int i=0;i<5;i++){
            int idTempEspe = getNewIdEspecialidad();
            Especialidad espe = new Especialidad(idTempEspe,"especialidad " + idTempEspe );
            String nombreProfesor="Nombre " +i;
            Asignatura[] asignaturas = new Asignatura[3];
            for (int j = 0 ; j<3;j++){
                asignaturas[j] = new Asignatura(getNewIdAsignatura(),"NombreAsignatura" + j );
            }
            profesores[i]=new Profesor(getNewIdProfesor(),nombreProfesor,asignaturas,espe);
        }

        try {
            ServerSocket servidorSocket = new ServerSocket(PUERTO);
            new Thread(new GestorSockets(servidorSocket)).start();
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
        public static AtomicInteger contadorClientes = new AtomicInteger(0);

        public GestorSockets(ServerSocket server) {
            this.server = server;
        }

        @Override
        public void run() {
            while (true) {

                contadorClientes.incrementAndGet();
                try {
                    new Thread(new ManejadorCliente(server.accept(), "Cliente " + contadorClientes.incrementAndGet())).start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // Clase interna para manejar la comunicación con cada cliente
    static class ManejadorCliente implements Runnable {
        private Socket socketCliente;
        private String nombreCliente;

        public ManejadorCliente(Socket socketCliente, String nombreCliente) {
            this.nombreCliente = nombreCliente;
            this.socketCliente=socketCliente;
        }

        @Override
        public void run() {
            ObjectOutputStream salidaObjeto = null;
            try {
                System.out.println("Cliente " + nombreCliente + " conectado!");
                salidaObjeto = new ObjectOutputStream(socketCliente.getOutputStream());
                Scanner entradaDatos = new Scanner(socketCliente.getInputStream());

                salidaObjeto.writeObject("¡Bienvenido, " + nombreCliente + "!");

                // Escuchar solicitudes del cliente y enviar el Profesor correspondiente
                while (true) {
                    // Recibir el ID del profesor que el cliente desea obtener
                    int idProfesorSolicitado = Integer.parseInt(entradaDatos.nextLine());

                    // Buscar el profesor con el ID solicitado
                    Profesor profesorEncontrado = buscarProfesorPorId(idProfesorSolicitado);

                    // Enviar el objeto Profesor al cliente
                    salidaObjeto.writeObject(profesorEncontrado);
                    salidaObjeto.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    // Cerrar el socket cuando el cliente cierra la conexión
                    socketCliente.close();

                    if (salidaObjeto != null) {
                        salidaObjeto.close();
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Profesor buscarProfesorPorId(int idProfesor) {
        for (Profesor profesor : profesores) {
            if (profesor.getIdprofesor() == idProfesor) {
                return profesor;
            }
        }
        return null; // Devolver null si no se encuentra el profesor
    }
    private int getNewIdEspecialidad(){return lastIdEspecialidad++;}

    private int getNewIdProfesor(){return lastIdProfesor++;}

    private int getNewIdAsignatura(){return lastIdAsignatura++;}

    //Muestra por la salida estándar los datos de los profesores y sus asignaturas. (para debug)
    public void report(){
        StringBuilder builder = new StringBuilder();
        for (Profesor profesor : profesores) {
            builder.setLength(0);
            System.out.println(builder.append("Id: ").append(profesor.getIdprofesor()));
            builder.setLength(0);
            System.out.println(builder.append("Nombre: ").append(profesor.getNombre()));

            System.out.println("Especialidad: ");
            builder.setLength(0);
            System.out.print((builder.append("Id: ").append(profesor.getEspecialidad().getId())).toString().indent(4));
            builder.setLength(0);
            System.out.print((builder.append("Nombre: ").append(profesor.getEspecialidad().getNombreEspecialidad())).toString().indent(4));
            System.out.println("Asignaturas: ");
            for (int j = 0; j < profesor.getAsignaturas().length; j++) {
                builder.setLength(0);
                System.out.print(builder.append("Id: ").append(profesor.getAsignaturas()[j].getId()).toString().indent(4) );
                builder.setLength(0);
                System.out.print(builder.append("Nombre: ").append(profesor.getAsignaturas()[j].getNombreAsignatura()).toString().indent(4));
            }
        }
    }
}