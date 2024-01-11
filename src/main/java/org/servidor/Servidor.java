package org.servidor;
public class Servidor
{
    private Profesor[] profesores;
    private int lastIdProfesor;
    private int lastIdEspecialidad;
    private int lastIdAsignatura;

    public Servidor(){
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