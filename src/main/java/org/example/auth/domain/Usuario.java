package org.example.auth.domain;

import org.example.domain.Enfermera;
import org.example.domain.Medico;
import org.example.domain.Exceptions.DomainException;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Usuario {
    private final String email;
    private final String hash;        // contraseña hasheada
    private final Enfermera enfermera; // nullable
    private final Medico medico;       // nullable

    private static final Pattern EMAIL_RE =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,63}$");

    private Usuario(String email, String hash, Enfermera enfermera, Medico medico) {
        if ((enfermera == null) == (medico == null)) {
            // true == true  -> ambas null  | false == false -> ambas no-null
            throw DomainException.validation(
                    "Usuario debe asociarse a médico o enfermera (exactamente uno)");
        }
        this.email = requireEmailValido(email);
        this.hash  = requireNotBlank(hash, "Hash de contraseña requerido");
        this.enfermera = enfermera;
        this.medico = medico;
    }

    // Fábricas claras para mantener el invariante
    public static Usuario paraEnfermera(String email, String hash, Enfermera e) {
        return new Usuario(email, hash, Objects.requireNonNull(e), null);
    }
    public static Usuario paraMedico(String email, String hash, Medico m) {
        return new Usuario(email, hash, null, Objects.requireNonNull(m));
    }

    public String getEmail() { return email; }
    public String getHash()  { return hash;  }

    public boolean esEnfermera() { return enfermera != null; }
    public boolean esMedico()    { return medico != null; }

    public Enfermera getEnfermera() {
        if (!esEnfermera()) throw new IllegalStateException("Usuario no es enfermera");
        return enfermera;
    }
    public Medico getMedico() {
        if (!esMedico()) throw new IllegalStateException("Usuario no es médico");
        return medico;
    }

    public String getRol() { return esMedico() ? "MEDICO" : "ENFERMERA"; }
    public String getCuilActor() { return esMedico() ? medico.getCuil() : enfermera.getCuil(); }

    private static String requireEmailValido(String email) {
        if (email == null || email.isBlank() || !EMAIL_RE.matcher(email).matches())
            throw DomainException.validation("Email inválido");
        return email.trim();
    }
    private static String requireNotBlank(String v, String msg) {
        if (v == null || v.isBlank()) throw DomainException.validation(msg);
        return v.trim();
    }
}
