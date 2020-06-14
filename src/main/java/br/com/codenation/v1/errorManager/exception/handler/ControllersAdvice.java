package br.com.codenation.v1.errorManager.exception.handler;

import br.com.codenation.v1.errorManager.exception.ApiErrors;
import br.com.codenation.v1.errorManager.exception.ApplicationNotFoundException;
import br.com.codenation.v1.errorManager.exception.OwnershipException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllersAdvice {

    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleApplicationNotFoundException( ApplicationNotFoundException e){
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodArgumentNotValidException( MethodArgumentNotValidException e){
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleConstraintDeclarationException (ConstraintViolationException e){
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(OwnershipException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors handleOwnershipException(OwnershipException e){
        return new ApiErrors(e.getMessage());
    }

}
