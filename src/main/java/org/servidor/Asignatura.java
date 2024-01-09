package org.servidor;

import java.io.Serializable;

public class Asignatura implements Serializable {
    private int id;
    private String nombreasig;

    public Asignatura(int id, String nombreasig) {
        this.id = id;
        this.nombreasig = nombreasig;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreasig() {
        return nombreasig;
    }

    public void setNombreasig(String nombreasig) {
        this.nombreasig = nombreasig;
    }
}
