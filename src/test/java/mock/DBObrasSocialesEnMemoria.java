package mock;

import org.example.app.interfaces.RepositorioObrasSociales;
import org.example.domain.ObraSocial;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DBObrasSocialesEnMemoria implements RepositorioObrasSociales {
    private final Map<String, ObraSocial> data = new HashMap<>();

    public void guardar(ObraSocial os) { data.put(os.getCodigo(), os); }

    @Override public Optional<ObraSocial> buscarPorCodigo(String codigo) {
        return Optional.ofNullable(data.get(codigo));
    }
}
