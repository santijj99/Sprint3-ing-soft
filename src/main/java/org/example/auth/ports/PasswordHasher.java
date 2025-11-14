package org.example.auth.ports;

public interface PasswordHasher {
    String hash(String plain);
    boolean matches(String plain, String hash);
}
