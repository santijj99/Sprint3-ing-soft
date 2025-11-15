package org.example.app;

import mock.DBObrasSocialesEnMemoria;
import mock.DBPacienteEnMemoria;
import org.example.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AltaPacienteServiceTest {

    DBPacienteEnMemoria repoPac;
    DBObrasSocialesEnMemoria repoOS;
    AltaPacienteService alta;

    @BeforeEach
    void setUp() {
        repoPac = new DBPacienteEnMemoria();
        repoOS  = new DBObrasSocialesEnMemoria();
        // catálogo con OSDE
        repoOS.guardar(new ObraSocial("OSDE", "OSDE"));
        alta = new AltaPacienteService(repoPac, repoOS);
    }

    @Test
    void crea_con_mandatorios_sin_obra_social_ok() {
        Paciente p = alta.registrarPaciente(
                "20-40902338-0", "Facundo", "Moya",
                "San Martín", 1234, "Tucumán",
                null, null
        );
        assertThat(p.tieneAfiliacion()).isFalse();
    }

    @Test
    void crea_con_mandatorios_y_obra_social_existente_ok() {
        Paciente p = alta.registrarPaciente(
                "20-43336577-2", "Santiago", "Martin",
                "Laprida", 321, "Tucumán",
                "OSDE", "45-889900"
        );
        assertThat(p.tieneAfiliacion()).isTrue();
        assertThat(p.getAfiliacion().getObraSocial().getCodigo()).isEqualTo("OSDE");
    }

    @Test
    void obra_social_inexistente_lanza_error_pedido() {
        assertThatThrownBy(() -> alta.registrarPaciente(
                "20-43336577-2", "Santiago", "Martin",
                "Laprida", 321, "Tucumán",
                "OSECAC", "12345"
        )).isInstanceOf(org.example.domain.Exceptions.DomainException.class)
                .hasMessageContaining("obra social inexistente");
    }

    @Test
    void obra_social_existente_sin_numero_afiliado_lanza_error_pedido() {
        assertThatThrownBy(() -> alta.registrarPaciente(
                "20-43336577-2", "Santiago", "Martin",
                "Laprida", 321, "Tucumán",
                "OSDE", "   "
        )).isInstanceOf(org.example.domain.Exceptions.DomainException.class)
                .hasMessageContaining("no está afiliado");
    }

    @Test
    void dato_mandatorio_faltante_en_domicilio_lanza() {
        assertThatThrownBy(() -> alta.registrarPaciente(
                "20-43336577-2", "Santiago", "Martin",
                "   ", 321, "Tucumán",
                null, null
        )).isInstanceOf(org.example.domain.Exceptions.DomainException.class)
                .hasMessageContaining("calle");
    }
}
