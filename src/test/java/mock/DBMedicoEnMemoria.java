package mock;

import org.example.auth.ports.MedicoRepositorio;
import org.example.domain.Medico;

import java.util.*;

public class DBMedicoEnMemoria implements MedicoRepositorio {
    private final Map<String, Medico> porCuil = new HashMap<>();

    public void guardar(Medico m) { porCuil.put(m.getCuil(), m); }

    @Override public Optional<Medico> buscarPorCuil(String cuil) {
        return Optional.ofNullable(porCuil.get(cuil));
    }
}
