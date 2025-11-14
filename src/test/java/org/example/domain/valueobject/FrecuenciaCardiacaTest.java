package org.example.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FrecuenciaCardiacaTest {

    @Test
    public void creacionExitosa(){
        //Preparacion
        Float frecuencia= 80f;

        //Ejecucion y validacion
        assertThatCode(()-> new FrecuenciaCardiaca(frecuencia))
                .doesNotThrowAnyException();
    }
}