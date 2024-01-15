import java.io.Serializable;

/**
 * La clase Especialidad representa una especialidad en un sistema académico.
 * Implementa la interfaz Serializable para permitir la serialización de objetos.
 */
public class Especialidad implements Serializable {

    /**
     * Identificador único de la especialidad.
     */
    private int id;

    /**
     * Nombre de la especialidad.
     */
    private String nombreEspecialidad;

    /**
     * Constructor de la clase Especialidad.
     *
     * @param id                 El identificador único de la especialidad.
     * @param nombreEspecialidad El nombre de la especialidad.
     */
    public Especialidad(int id, String nombreEspecialidad) {
        this.id = id;
        this.nombreEspecialidad = nombreEspecialidad;
    }

    /**
     * Obtiene el identificador de la especialidad.
     *
     * @return El identificador de la especialidad.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la especialidad.
     *
     * @param id El nuevo identificador de la especialidad.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la especialidad.
     *
     * @return El nombre de la especialidad.
     */
    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    /**
     * Establece el nombre de la especialidad.
     *
     * @param nombreEspecialidad El nuevo nombre de la especialidad.
     */
    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }
}
