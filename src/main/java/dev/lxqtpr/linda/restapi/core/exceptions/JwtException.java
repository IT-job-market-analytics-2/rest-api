package dev.lxqtpr.linda.restapi.core.exceptions;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}