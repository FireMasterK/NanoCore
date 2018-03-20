package me.idarkyy.nanocore.exceptions;

public class CoreException extends Exception {
    public CoreException(String s) {
        super(s);
    }

    public CoreException() {
        super("An error occured in the core.");
    }
}
