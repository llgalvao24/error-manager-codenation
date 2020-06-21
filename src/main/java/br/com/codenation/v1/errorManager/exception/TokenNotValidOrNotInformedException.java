package br.com.codenation.v1.errorManager.exception;

public class TokenNotValidOrNotInformedException extends RuntimeException {
    public TokenNotValidOrNotInformedException() {
        super("Token is not valid or has not been informed.");
    }

    public TokenNotValidOrNotInformedException(String message) {
        super(message);
    }
}
