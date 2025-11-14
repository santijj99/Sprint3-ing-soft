package org.example.auth.ports;

import org.example.domain.Enfermera;
import java.util.Optional;

public interface EnfermeraRepositorio {
    Optional<Enfermera> buscarPorCuil(String cuil);
}
