import java.io.Serializable;

/**
 * La clase Asignatura representa una asignatura en un sistema académico.
 * Implementa la interfaz Serializable para permitir la serialización de objetos.
 * @author Dante Sarotti, Miriam Betanzos Jamardo
 */
public class Asignatura implements Serializable {

    /**
     * Identificador único de la asignatura.
     */
    private int id;

    /**
     * Nombre de la asignatura.
     */
    private String nombreAsignatura;

    /**
     * Constructor de la clase Asignatura.
     *
     * @param id               El identificador único de la asignatura.
     * @param nombreAsignatura El nombre de la asignatura.
     */
    public Asignatura(int id, String nombreAsignatura) {
        this.id = id;
        this.nombreAsignatura = nombreAsignatura;
    }

    /**
     * Obtiene el identificador de la asignatura.
     *
     * @return El identificador de la asignatura.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la asignatura.
     *
     * @param id El nuevo identificador de la asignatura.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la asignatura.
     *
     * @return El nombre de la asignatura.
     */
    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    /**
     * Establece el nombre de la asignatura.
     *
     * @param nombreAsignatura El nuevo nombre de la asignatura.
     */
    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }
}
