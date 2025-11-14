package org.example.domain;

public class Domicilio {
    private  String calle;
    private  int numero;
    private  String localidad;

    public Domicilio(String calle, Integer numero, String localidad) {
        this.calle = Guard.notBlank(calle, "La calle del domicilio es obligatoria");
        this.numero = Guard.positiveInt(numero, "El número del domicilio debe ser mayor a cero");
        this.localidad = Guard.notBlank(localidad, "La localidad del domicilio es obligatoria");
    }

    public String getCalle() { return calle; }
    public int getNumero() { return numero; }
    public String getLocalidad() { return localidad; }
}
