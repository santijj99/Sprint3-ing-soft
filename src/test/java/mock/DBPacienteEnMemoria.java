package mock;

import org.example.app.interfaces.RepositorioPacientes;
import org.example.domain.Paciente;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DBPacienteEnMemoria implements RepositorioPacientes {
    private Map<String,Paciente> pacientes;

    public DBPacienteEnMemoria() {
        this.pacientes = new HashMap<>();
    }

    @Override
    public void guardarPaciente(Paciente paciente) {
        this.pacientes.put(paciente.getCuil(), paciente);
    }

    @Override
    public Optional<Paciente> buscarPacientePorCuil(String cuil) {
        return Optional.ofNullable(pacientes.get(cuil));
    }
}
