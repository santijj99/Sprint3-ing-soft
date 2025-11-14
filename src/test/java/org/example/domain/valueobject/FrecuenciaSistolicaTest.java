package org.example.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FrecuenciaSistolicaTest {
    @Test
    public void creacion_con_numero_negativo(){
        //Preparacion
        Float frecuencia= -80f;

        //Ejecucion y validacion
        assertThatThrownBy(() -> new FrecuenciaSistolica(frecuencia))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("La frecuencia sistolica no puede ser negativa");
    }
}