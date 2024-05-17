package dev.lxqtpr.linda.restapi.core.exceptions;

public class ValueAlreadyExistException extends RuntimeException{
    public ValueAlreadyExistException(String message){
        super(message);
    }
}
