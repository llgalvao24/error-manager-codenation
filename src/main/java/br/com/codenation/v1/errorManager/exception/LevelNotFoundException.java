package br.com.codenation.v1.errorManager.exception;

public class LevelNotFoundException extends RuntimeException{
    public LevelNotFoundException() {
            super("Level não encontrado.");
    }
}
