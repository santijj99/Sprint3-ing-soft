package org.example.domain;

import org.example.domain.Exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PacienteTest {

    @Test
    void crea_paciente_sin_afiliacion_ok() {
        Domicilio dom = new Domicilio("San Martín", 1234, "Tucumán");
        Paciente p = new Paciente("20-40902338-0", "Facundo", "Moya", dom);
        assertThat(p.getDomicilio()).isNotNull();
        assertThat(p.tieneAfiliacion()).isFalse();
    }

    @Test
    void crea_paciente_con_afiliacion_ok() {
        Domicilio dom = new Domicilio("San Martín", 1234, "Tucumán");
        ObraSocial os = new ObraSocial("OSDE", "OSDE");
        Afiliacion af = new Afiliacion(os, "45-889900");
        Paciente p = new Paciente("20-43336577-2", "Santiago", "Martin", dom, af);
        assertThat(p.tieneAfiliacion()).isTrue();
        assertThat(p.getAfiliacion().getObraSocial().getCodigo()).isEqualTo("OSDE");
    }

    @Test
    void domicilio_null_lanza() {
        assertThatThrownBy(() -> new Paciente("20-40902338-0", "Facundo", "Moya", (Domicilio) null))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("domicilio");
    }
}
