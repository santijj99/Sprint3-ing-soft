package org.example.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FrecuenciaDiastolicaTest {
    @Test
    public void creacion_con_numero_negativo(){
        //Preparacion
        Float frecuencia= -80f;

        //Ejecucion y validacion
        assertThatThrownBy(() -> new FrecuenciaDiastolica(frecuencia))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("La frecuencia diastolica no puede ser negativa");
    }
}