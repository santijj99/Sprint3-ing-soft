package org.example.domain;

import org.example.domain.Exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AfiliacionTest {

    @Test
    void crea_afiliacion_valida() {
        ObraSocial os = new ObraSocial("OSDE", "OSDE");
        Afiliacion a = new Afiliacion(os, "45-889900");
        assertThat(a.getObraSocial().getCodigo()).isEqualTo("OSDE");
        assertThat(a.getNumeroAfiliado()).isEqualTo("45-889900");
    }

    @Test
    void obra_social_null_lanza() {
        assertThatThrownBy(() -> new Afiliacion(null, "X"))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("obra social");
    }

    @Test
    void numero_afiliado_blanco_lanza() {
        ObraSocial os = new ObraSocial("OSDE", "OSDE");
        assertThatThrownBy(() -> new Afiliacion(os, "   "))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("número de afiliado");
    }
}
