public class Main {
    public static void main(String[] args) {
        HiloServidor hiloServidor = new HiloServidor();

        new Thread(hiloServidor).start();
    }
}