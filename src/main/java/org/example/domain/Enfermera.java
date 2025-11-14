package org.example.domain;

public class Enfermera extends Persona {
    public Enfermera(String nombre, String apellido, String cuil) {
        super(cuil, nombre, apellido);
    }
    public Enfermera(String cuil, String nombre, String apellido, String email) {
        super(cuil, nombre, apellido, email);
    }
}