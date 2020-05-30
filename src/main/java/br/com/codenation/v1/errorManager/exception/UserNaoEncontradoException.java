package br.com.codenation.v1.errorManager.exception;


public class UserNaoEncontradoException extends RuntimeException {
    public UserNaoEncontradoException() {
        super("Usuário não encontrado.");
    }
}
