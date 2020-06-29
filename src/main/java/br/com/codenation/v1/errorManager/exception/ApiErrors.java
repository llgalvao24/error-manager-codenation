package br.com.codenation.v1.errorManager.exception;

import org.springframework.http.HttpMethod;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ApiErrors {

    private Map<String, Object> errors = new LinkedHashMap<>();

    public ApiErrors(String mensagem) {
        this.errors.put("timestamp", LocalDateTime.now());
        this.errors.put("mensagem", mensagem);
    }

    public ApiErrors(List<String> errors) {
        this.errors.put("mensagens", errors);
    }

    public ApiErrors(Set<HttpMethod> supportedHttpMethods, String method, String message) {
        this.errors.put("supported methods", supportedHttpMethods);
        this.errors.put("called method", method);
        this.errors.put("mensagem", message);
    }

    public Map<String, Object> getErrors() {
        return errors;
    }
}
