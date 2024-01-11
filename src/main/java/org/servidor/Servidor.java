package org.servidor;
public class Servidor
{
    private Profesor[] profesores;
    private int lastId;

    public Servidor(){
        this.profesores =new Profesor[5];
        this.lastId=0;
        init();

        for(int i = 0;i< profesores.length;i++){
            System.out.println("idProfesor: "+profesores[i].getIdprofesor());
            System.out.println("\n NombreProfesor:" + profesores[i].getNombre());

        }
    }

    private void init(){
        //Inicializar profesores
        for(int i=0;i<5;i++){
            int idTemp = getNewId();
            Especialidad espe = new Especialidad(idTemp,"especialidad " + idTemp );
            String nombreProfesor="Nombre " +i;
            Asignatura[] asignaturas = new Asignatura[3];
            for (int j = 0 ; i<3;i++){
                asignaturas[j] = new Asignatura(getNewId(),"NombreAsignatura" + j );
            }
            profesores[i]=new Profesor(getNewId(),nombreProfesor,asignaturas,espe);
        }
    }

    private int getNewId(){
        return lastId++;
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}

