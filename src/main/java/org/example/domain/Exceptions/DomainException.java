package org.example.domain.Exceptions;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Excepción base de dominio.
 *
 * Pensada para representar errores de reglas de negocio (domain-driven),
 * desacoplada de detalles de infraestructura (HTTP, BD, etc.).
 *
 * ✔ Lleva un "code" estable (útil para i18n/logs/tests)
 * ✔ Permite adjuntar "context" (clave/valor) para depurar mejor
 * ✔ Hereda de RuntimeException para no forzar try/catch
 */
public class DomainException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** Código estable y legible del error (ej: "paciente.no_encontrado"). */
    private final String code;

    /** Datos de contexto (no sensibles) que ayudan a entender el fallo. */
    private final Map<String, Object> context;

    // ---------- Constructores básicos ----------

    public DomainException(String message) {
        this(null, message, null, null);
    }

    public DomainException(String message, Throwable cause) {
        this(null, message, cause, null);
    }

    public DomainException(String code, String message) {
        this(code, message, null, null);
    }

    public DomainException(String code, String message, Throwable cause) {
        this(code, message, cause, null);
    }

    public DomainException(String code, String message, Map<String, Object> context) {
        this(code, message, null, context);
    }

    public DomainException(String code, String message, Throwable cause, Map<String, Object> context) {
        super(message, cause);
        this.code = code;
        if (context == null || context.isEmpty()) {
            this.context = Collections.emptyMap();
        } else {
            this.context = Collections.unmodifiableMap(new LinkedHashMap<>(context));
        }
    }

    // ---------- Getters ----------

    public String getCode() {
        return code;
    }

    /**
     * Mapa inmutable con datos de contexto (ej: {"cuil":"20-...","entidad":"Paciente"}).
     */
    public Map<String, Object> getContext() {
        return context;
    }

    // ---------- Helpers estáticos comunes ----------

    /** Crea una DomainException de validación con código estandarizado. */
    public static DomainException validation(String message) {
        return new DomainException("validacion.error", message);
    }

    /** Crea una DomainException de "no encontrado" con contexto útil. */
    public static DomainException notFound(String entity, String key, Object value) {
        Map<String, Object> ctx = new LinkedHashMap<>();
        ctx.put("entity", entity);
        ctx.put(key, value);
        return new DomainException(entity.toLowerCase() + ".no_encontrado",
                entity + " no encontrado", ctx);
    }

    /** Crea una DomainException de negocio genérica con código propio. */
    public static DomainException business(String code, String message) {
        return new DomainException(code, message);
    }

    // ---------- Fluent API para anexar contexto ----------

    /**
     * Devuelve una copia de esta excepción con más contexto (inmutable).
     * Útil en capas que agregan información sin perder el stack original.
     */
    public DomainException withContext(String key, Object value) {
        Map<String, Object> ctx = new LinkedHashMap<>(this.context.isEmpty() ? Map.of() : this.context);
        ctx.put(key, value);
        return new DomainException(this.code, this.getMessage(), this.getCause(), ctx);
    }

    @Override
    public String toString() {
        String base = "DomainException{code=" + code + ", message=" + getMessage() + "}";
        if (context.isEmpty()) return base;
        return base + " context=" + context;
    }
}
