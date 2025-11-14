package org.example.auth.ports;

import org.example.domain.Medico;
import java.util.Optional;

public interface MedicoRepositorio {
    Optional<Medico> buscarPorCuil(String cuil);
}
