package org.example.domain;

public class Enfermera {
    private String nombre;
    private String apellido;
    private String cuil;

    public Enfermera(String nombre, String apellido, String cuil) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cuil = cuil;
    }

    public boolean getCuil() {
        return true;
    }
}
