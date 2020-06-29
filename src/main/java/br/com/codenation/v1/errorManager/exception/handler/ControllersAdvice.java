package br.com.codenation.v1.errorManager.exception.handler;

import br.com.codenation.v1.errorManager.exception.ApiErrors;
import br.com.codenation.v1.errorManager.exception.ApplicationNotFoundException;
import br.com.codenation.v1.errorManager.exception.LevelNotFoundException;
import br.com.codenation.v1.errorManager.exception.LogNotFoundException;
import br.com.codenation.v1.errorManager.exception.OwnershipException;
import br.com.codenation.v1.errorManager.exception.PageableDefinitionException;
import br.com.codenation.v1.errorManager.exception.TokenNotValidOrNotInformedException;
import br.com.codenation.v1.errorManager.exception.UserNotFoundException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllersAdvice {

    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleApplicationNotFoundException( ApplicationNotFoundException e){
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(LogNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleLogNotFoundException(LogNotFoundException e){
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(LevelNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleLevelNotFoundException(LevelNotFoundException e){
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleUserNotFoundException(UserNotFoundException e){
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodArgumentNotValidException( MethodArgumentNotValidException e){
        return new ApiErrors(e.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(fieldError -> fieldError.getDefaultMessage())
                                .collect(Collectors.toList()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleConstraintDeclarationException (ConstraintViolationException e){
        return new ApiErrors(e.getMessage());
    }
    @ExceptionHandler(PageableDefinitionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlePageableDefinitionException(PageableDefinitionException e){
        return new ApiErrors(e.getMessage());

    }

    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlePropertyReferenceException(PropertyReferenceException e){
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(OwnershipException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors handleOwnershipException(OwnershipException e){
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleHttpRequestMethodNotSupportedException( HttpRequestMethodNotSupportedException e){
        return new ApiErrors(e.getSupportedHttpMethods(), e.getMethod(), e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
         return new ApiErrors(e.getMessage());
    }
  
    @ExceptionHandler(TokenNotValidOrNotInformedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrors handleTokenNotValidException(TokenNotValidOrNotInformedException e){
        return new ApiErrors(e.getMessage());
    }

}
