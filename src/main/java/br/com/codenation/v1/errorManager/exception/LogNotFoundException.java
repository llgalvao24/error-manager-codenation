package br.com.codenation.v1.errorManager.exception;

public class LogNotFoundException extends RuntimeException {
    public LogNotFoundException() {
        super("Log not found.");
    }

    public LogNotFoundException(String message) {
        super(message);
    }
}
