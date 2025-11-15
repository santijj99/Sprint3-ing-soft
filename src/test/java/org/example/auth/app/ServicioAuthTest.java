package org.example.auth.app;

import mock.*;
import org.example.auth.BCryptHasher;
import org.example.auth.domain.Usuario;
import org.example.domain.Enfermera;
import org.example.domain.Medico;
import org.example.domain.Exceptions.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ServicioAuthTest {

    DBUsuarioEnMemoria usuarios;
    BCryptHasher hasher;
    DBEnfermeraEnMemoria enfRepo;
    DBMedicoEnMemoria medRepo;
    ServicioAuth auth;

    @BeforeEach
    void setUp() {
        usuarios = new DBUsuarioEnMemoria();
        hasher   = new BCryptHasher(4);
        enfRepo  = new DBEnfermeraEnMemoria();
        medRepo  = new DBMedicoEnMemoria();

        // seed
        enfRepo.guardar(new Enfermera("Lucia", "Paz", "20-32456878-7"));
        medRepo.guardar(new Medico("Carlos", "Ruiz", "20-55555555-6"));

        auth = new ServicioAuth(usuarios, hasher, enfRepo, medRepo);
    }

    @Test
    void el_hash_no_es_la_password_y_matchea_con_bcrypt() {
        var u = auth.registrarParaMedico("m@c.com", "clave1234", "20-55555555-6");

        // El hash NO contiene la password en claro
        assertThat(u.getHash()).doesNotContain("clave1234");

        // Formato típico de BCrypt ($2a/$2b/…)
        assertThat(u.getHash()).startsWith("$2");

        // Verificación correcta con BCrypt (usa salt aleatorio)
        assertThat(hasher.matches("clave1234", u.getHash())).isTrue();
    }

    @Test
    void registrar_enfermera_ok() {
        Usuario u = auth.registrarParaEnfermera("lucia@clinica.com", "secreto123", "20-32456878-7");
        assertThat(u.esEnfermera()).isTrue();
        assertThat(u.getRol()).isEqualTo("ENFERMERA");
        assertThat(u.getCuilActor()).isEqualTo("20-32456878-7");
        // BCrypt: verificar con matches
        assertThat(hasher.matches("secreto123", u.getHash())).isTrue();
    }
    @Test
    void registrar_medico_ok() {
        Usuario u = auth.registrarParaMedico("dr@clinica.com", "secreto123", "20-55555555-6");
        assertThat(u.esMedico()).isTrue();
        assertThat(u.getRol()).isEqualTo("MEDICO");
        assertThat(u.getCuilActor()).isEqualTo("20-55555555-6");
        assertThat(hasher.matches("secreto123", u.getHash())).isTrue();
    }

    @Test
    void login_ok() {
        auth.registrarParaMedico("dr@clinica.com", "claveSegura", "20-55555555-6");
        Usuario u = auth.login("dr@clinica.com", "claveSegura");
        assertThat(u.esMedico()).isTrue();
    }

    @Test
    void login_falla_contrasenia_incorrecta() {
        auth.registrarParaMedico("dr@clinica.com", "claveSegura", "20-55555555-6");
        assertThatThrownBy(() -> auth.login("dr@clinica.com", "otraClave"))
                .isInstanceOf(DomainException.class)
                .hasMessage("Usuario o contraseña inválidos");
    }

    @Test
    void no_permite_doble_usuario_misma_enfermera() {
        auth.registrarParaEnfermera("a@c.com", "password1", "20-32456878-7");
        assertThatThrownBy(() ->
                auth.registrarParaEnfermera("b@c.com", "password2", "20-32456878-7")
        ).isInstanceOf(DomainException.class).hasMessageContaining("ya tiene un usuario");
    }

    @Test
    void no_permite_doble_usuario_mismo_medico() {
        auth.registrarParaMedico("m1@c.com", "password1", "20-55555555-6");
        assertThatThrownBy(() ->
                auth.registrarParaMedico("m2@c.com", "password2", "20-55555555-6")
        ).isInstanceOf(DomainException.class).hasMessageContaining("ya tiene un usuario");
    }

    @Test
    void login_falla_mensaje_generico_si_password_incorrecta() {
        auth.registrarParaMedico("login2@c.com", "claveLarga", "20-55555555-6");
        assertThatThrownBy(() -> auth.login("login2@c.com", "malaclave"))
                .isInstanceOf(DomainException.class)
                .hasMessage("Usuario o contraseña inválidos");
    }

    @Test
    void login_falla_mensaje_generico_si_email_inexistente() {
        assertThatThrownBy(() -> auth.login("no@existe.com", "loquesea"))
                .isInstanceOf(DomainException.class)
                .hasMessage("Usuario o contraseña inválidos");
    }

    @Test
    void registro_falla_email_invalido() {
        assertThatThrownBy(() ->
                auth.registrarParaEnfermera("mal_email", "secreto123", "20-32456878-7")
        ).isInstanceOf(DomainException.class).hasMessageContaining("Email inválido");
    }

    @Test
    void registro_falla_password_corta() {
        assertThatThrownBy(() ->
                auth.registrarParaMedico("dr@c.com", "short", "20-55555555-6")
        ).isInstanceOf(DomainException.class).hasMessageContaining("al menos 8");
    }

    @Test
    void registro_falla_enfermera_inexistente() {
        assertThatThrownBy(() ->
                auth.registrarParaEnfermera("l@c.com", "secreto123", "20-00000000-0")
        )
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("Enfermera inexistente");
    }

    @Test
    void registro_falla_Medico_inexistente() {
        assertThatThrownBy(() ->
                auth.registrarParaMedico("l@c.com", "secreto123", "20-00000000-0")
        )
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("Médico inexistente");
    }


}
