package br.com.codenation.v1.errorManager.exception;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Usuário não encontrado.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
