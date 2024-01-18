/**
 * Punto de entrada a la aplicación
 */
 class ServidorTCP {

    /**
     * Inicializa el hilo que controla el servidor.
     * @param args
     */
    public static void main(String[] args) {
        HiloServidor hiloServidor = new HiloServidor();

        new Thread(hiloServidor).start();
    }
}