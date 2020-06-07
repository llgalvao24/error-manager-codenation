package br.com.codenation.v1.errorManager.exception;

public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException() {
        super("Aplicação não encontrada.");
    }
}
