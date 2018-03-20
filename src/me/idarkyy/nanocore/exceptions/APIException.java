package me.idarkyy.nanocore.exceptions;

public class APIException extends Exception {
    public APIException(String message) {
        super(message);
    }

    public APIException() {
        super("An error occured while executing an api task");
    }
}
