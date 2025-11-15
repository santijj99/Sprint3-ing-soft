package org.example.domain;

import org.example.domain.Exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GuardCuilTest {

    @Test
    void cuil_valido_conGuiones() {
        String c = Guard.cuilValido("20-40902338-0", "CUIL inválido");
        assertThat(c).isEqualTo("20-40902338-0");
    }

    @Test
    void cuil_invalido_digito_verificador_lanza() {
        assertThatThrownBy(() -> Guard.cuilValido("20-40902338-7", "CUIL inválido"))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("CUIL inválido");
    }

    @Test
    void cuil_invalido_longitud_lanza() {
        assertThatThrownBy(() -> Guard.cuilValido("20-4090233", "CUIL inválido"))
                .isInstanceOf(DomainException.class);
    }

    @Test
    void cuil_valido_conGuiones_ok() {
        String c = Guard.cuilValido("20-40902338-0", "CUIL inválido");
        assertThat(c).isEqualTo("20-40902338-0");
    }

    @Test
    void cuil_valido_sinGuiones_11_digitos_ok() {
        String c = Guard.cuilValido("20409023380", "CUIL inválido");
        assertThat(c).isEqualTo("20-40902338-0");
    }

    @Test
    void cuil_10_digitos_es_rechazado() {
        assertThatThrownBy(() -> Guard.cuilValido("2040923380", "CUIL inválido"))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("CUIL inválido");
    }

    @Test
    void cuil_11_digitos_con_digito_incorrecto_es_rechazado() {
        assertThatThrownBy(() -> Guard.cuilValido("20409023387", "CUIL inválido"))
                .isInstanceOf(DomainException.class);
    }

    @Test
    void email_invalido() {
        assertThatThrownBy(() ->
                Guard.requireEmailValido("mal_email")
        ).isInstanceOf(DomainException.class).hasMessageContaining("Email inválido");
    }

}