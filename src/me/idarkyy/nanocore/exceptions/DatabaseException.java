package me.idarkyy.nanocore.exceptions;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException() {
        super("An error occured while executing a database task.");
    }

}
