package br.com.caiogit.datajpa.libraryapi.controller.common;


import br.com.caiogit.datajpa.libraryapi.controller.dto.error.ErrorCampo;
import br.com.caiogit.datajpa.libraryapi.controller.dto.error.ErrorResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ErrorResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErrorCampo> list = fieldErrors.stream()
                .map((fe) -> new ErrorCampo(fe.getField(), fe.getDefaultMessage())).toList();


        return new ErrorResposta(HttpStatus.UNPROCESSABLE_CONTENT.value(), "Erro de validação", list);
    }
}
