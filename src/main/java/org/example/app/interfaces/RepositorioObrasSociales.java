package org.example.app.interfaces;

import org.example.domain.ObraSocial;
import java.util.Optional;

public interface RepositorioObrasSociales {
    Optional<ObraSocial> buscarPorCodigo(String codigo);
}
