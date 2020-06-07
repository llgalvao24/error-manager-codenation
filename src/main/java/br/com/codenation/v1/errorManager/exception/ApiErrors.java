package br.com.codenation.v1.errorManager.exception;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApiErrors {

    private Map<String, Object> errors = new LinkedHashMap<>();

    public ApiErrors(String mensagem) {
        this.errors.put("timestamp", LocalDateTime.now());
        this.errors.put("errors", mensagem);
    }

    public ApiErrors(List<String> errors) {
        this.errors.put("mensagens", errors);
    }

    public Map<String, Object> getErrors() {
        return errors;
    }
}
