package org.example.auth.domain;

import org.example.domain.Enfermera;
import org.example.domain.Exceptions.DomainException;
import org.example.domain.Medico;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UsuarioTest {
    @Test
    void usuario_debe_asociarse_a_un_solo_actor() {
        var e = new Enfermera("Lucia","Paz","20-32456878-7");
        var m = new Medico("Carlos","Ruiz","20-55555555-6");

        assertThatThrownBy(() -> new Usuario("a@b.com", "$2a....", e, m))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("exactamente uno");
        assertThatThrownBy(() -> new Usuario("a@b.com", "$2a....", null, null))
                .isInstanceOf(DomainException.class);
    }

}