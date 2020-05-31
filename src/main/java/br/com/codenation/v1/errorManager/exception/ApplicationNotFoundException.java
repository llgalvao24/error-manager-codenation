package br.com.codenation.v1.errorManager.exception;

public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException() {
        super("Application not found.");
    }
}
