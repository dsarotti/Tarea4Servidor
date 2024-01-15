import java.io.Serializable;

/**
 * La clase Profesor representa a un profesor en un sistema académico.
 * Implementa la interfaz Serializable para permitir la serialización de objetos.
 */
public class Profesor implements Serializable {

    /**
     * Identificador único del profesor.
     */
    private int idprofesor;

    /**
     * Nombre del profesor.
     */
    private String nombre;

    /**
     * Array de asignaturas que imparte el profesor.
     */
    private Asignatura[] asignaturas;

    /**
     * Especialidad del profesor.
     */
    private Especialidad especialidad;

    /**
     * Constructor de la clase Profesor.
     *
     * @param idprofesor   El identificador único del profesor.
     * @param nombre       El nombre del profesor.
     * @param asignaturas  Las asignaturas que imparte el profesor.
     * @param especialidad La especialidad del profesor.
     */
    public Profesor(int idprofesor, String nombre, Asignatura[] asignaturas, Especialidad especialidad) {
        this.idprofesor = idprofesor;
        this.nombre = nombre;
        this.asignaturas = asignaturas;
        this.especialidad = especialidad;
    }

    /**
     * Obtiene el identificador del profesor.
     *
     * @return El identificador del profesor.
     */
    public int getIdprofesor() {
        return idprofesor;
    }

    /**
     * Establece el identificador del profesor.
     *
     * @param idprofesor El nuevo identificador del profesor.
     */
    public void setIdprofesor(int idprofesor) {
        this.idprofesor = idprofesor;
    }

    /**
     * Obtiene el nombre del profesor.
     *
     * @return El nombre del profesor.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del profesor.
     *
     * @param nombre El nuevo nombre del profesor.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene las asignaturas que imparte el profesor.
     *
     * @return Arreglo de asignaturas del profesor.
     */
    public Asignatura[] getAsignaturas() {
        return asignaturas;
    }

    /**
     * Establece las asignaturas que imparte el profesor.
     *
     * @param asignaturas El arreglo de asignaturas del profesor.
     */
    public void setAsignaturas(Asignatura[] asignaturas) {
        this.asignaturas = asignaturas;
    }

    /**
     * Obtiene la especialidad del profesor.
     *
     * @return La especialidad del profesor.
     */
    public Especialidad getEspecialidad() {
        return especialidad;
    }

    /**
     * Establece la especialidad del profesor.
     *
     * @param especialidad La nueva especialidad del profesor.
     */
    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }
}
