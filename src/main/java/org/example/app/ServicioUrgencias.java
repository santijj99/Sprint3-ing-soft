package org.example.app;

import org.example.app.interfaces.RepositorioPacientes;
import org.example.domain.Enfermera;
import org.example.domain.Ingreso;
import org.example.domain.NivelEmergencia;
import org.example.domain.Paciente;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ServicioUrgencias {

    //SEGREGACION DE INTERFAZ
    //PATRON ADAPTADOR
    private RepositorioPacientes dbPacientes;

    private final List<Ingreso> listaEspera;

    //INYECCION DE DEPENDENCIA -> Pruebas
    public ServicioUrgencias(RepositorioPacientes dbPacientes) {
        this.dbPacientes = dbPacientes;
        this.listaEspera = new ArrayList<>();
    }


    public void registrarUrgencia(String cuilPaciente,
                                  Enfermera enfermera,
                                  String informe,
                                  NivelEmergencia emergencia,
                                  Float temperatura,
                                  Float frecuenciaCardiaca,
                                  Float frecuenciaRespiratoria,
                                  Float frecuenciaSistolica,
                                  Float frecuenciaDiastolica){
        Paciente paciente = dbPacientes.buscarPacientePorCuil(cuilPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Ingreso ingreso = new Ingreso(
                paciente,
                enfermera,
                informe,
                emergencia,
                temperatura,
                frecuenciaCardiaca,
                frecuenciaRespiratoria,
                frecuenciaSistolica,
                frecuenciaDiastolica);

        listaEspera.add(ingreso);
        listaEspera.sort(Ingreso::compareTo);
    }

    public List<Ingreso> obtenerIngresosPendientes(){
        return this.listaEspera;
    }
}
