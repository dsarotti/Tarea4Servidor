/**
 * Punto de entrada a la aplicación
 */
 class ServidorTCP {
    public static void main(String[] args) {
        HiloServidor hiloServidor = new HiloServidor();

        new Thread(hiloServidor).start();
    }
}