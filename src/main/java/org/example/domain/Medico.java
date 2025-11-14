package org.example.domain;

public class Medico extends Persona{
    public Medico(String nombre, String apellido, String cuil) {
        super(cuil, nombre, apellido);
    }
    public Medico(String cuil, String nombre, String apellido, String email) {
        super(cuil, nombre, apellido, email);
    }
}
