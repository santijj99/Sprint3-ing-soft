// src/main/java/org/example/domain/Guard.java
package org.example.domain;

import org.example.domain.Exceptions.DomainException;

import java.util.regex.Pattern;

public final class Guard {
    private Guard() {}

    private static final Pattern EMAIL_RE =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,63}$");

    public static String notBlank(String val, String message) {
        if (val == null || val.isBlank()) {
            throw DomainException.validation(message).withContext("valor", val);
        }
        return val.trim();
    }

    public static int positiveInt(Integer val, String message) {
        if (val == null || val <= 0) {
            throw DomainException.validation(message).withContext("valor", val);
        }
        return val;
    }

    /** Valida CUIL/CUIT con dígito verificador (11 dígitos). Acepta con o sin guiones. */
    public static String cuilValido(String cuil, String message) {
        String s = notBlank(cuil, message).replaceAll("\\D", "");
        if (s.length() != 11) throw DomainException.validation(message).withContext("valor", cuil);
        int[] w = {5,4,3,2,7,6,5,4,3,2};
        int sum = 0;
        for (int i = 0; i < 10; i++) sum += Character.digit(s.charAt(i), 10) * w[i];
        int mod = 11 - (sum % 11);
        int dv = (mod == 11) ? 0 : (mod == 10 ? 9 : mod);
        int dvReal = Character.digit(s.charAt(10), 10);
        if (dv != dvReal) throw DomainException.validation(message).withContext("valor", cuil);
        return s.substring(0,2) + "-" + s.substring(2,10) + "-" + s.substring(10);
    }

    public static String requireEmailValido(String email) {
        if (email == null || email.isBlank() || !EMAIL_RE.matcher(email).matches())
            throw DomainException.validation("Email inválido");
        return email.trim();
    }
}
