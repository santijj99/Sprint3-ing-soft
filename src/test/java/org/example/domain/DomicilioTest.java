package org.example.domain;

import org.example.domain.Exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DomicilioTest {

    @Test
    void crea_domicilio_valido() {
        Domicilio d = new Domicilio("San Martín", 1234, "Tucumán");
        assertThat(d.getCalle()).isEqualTo("San Martín");
        assertThat(d.getNumero()).isEqualTo(1234);
        assertThat(d.getLocalidad()).isEqualTo("Tucumán");
    }

    @Test
    void calle_blanca_lanza() {
        assertThatThrownBy(() -> new Domicilio("   ", 10, "Tucumán"))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("calle");
    }

    @Test
    void numero_no_positivo_lanza() {
        assertThatThrownBy(() -> new Domicilio("Laprida", 0, "Tucumán"))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("número");
    }

    @Test
    void localidad_blanca_lanza() {
        assertThatThrownBy(() -> new Domicilio("Laprida", 12, "  "))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("localidad");
    }
}
