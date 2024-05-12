package dev.lxqtpr.linda.restapi.core.exceptions;

public class PasswordDoesNotMatchException extends RuntimeException{
    public PasswordDoesNotMatchException(String message){
        super(message);
    }
}
