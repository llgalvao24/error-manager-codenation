package br.com.codenation.v1.errorManager.exception;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found.");
    }
}
