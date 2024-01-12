import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Servidor servidor = null;
        try {
            servidor = new Servidor();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Se ha producido un error al inicializar el servidor.");
        }
        if(servidor!=null){
            //Inicializar profesores
            servidor.init();
            //debug profesores
            servidor.report();
        }
    }
}