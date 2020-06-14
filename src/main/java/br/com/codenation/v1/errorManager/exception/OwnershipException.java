package br.com.codenation.v1.errorManager.exception;


public class OwnershipException extends RuntimeException {

    public OwnershipException() {
        super("Operação não concluída, pois o objeto solicitado não é de propriedade do usuário autenticado. " +
                "Por favor, informe um objeto válido");
    }

    public OwnershipException(String message) {
        super(message);
    }
}
