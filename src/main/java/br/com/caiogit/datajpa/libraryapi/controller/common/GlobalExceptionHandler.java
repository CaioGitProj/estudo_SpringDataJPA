package br.com.caiogit.datajpa.libraryapi.controller.common;


import br.com.caiogit.datajpa.libraryapi.controller.dto.error.ErrorCampo;
import br.com.caiogit.datajpa.libraryapi.controller.dto.error.ErrorResposta;
import br.com.caiogit.datajpa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import br.com.caiogit.datajpa.libraryapi.exceptions.RegistroDuplicadoException;
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

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResposta handleRegistroDuplicadoException(RegistroDuplicadoException e)
    {
        return ErrorResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e)
    {
        return ErrorResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResposta handleErrosNaoTratados(RuntimeException e)
    {
        return new ErrorResposta((HttpStatus.INTERNAL_SERVER_ERROR.value()), "Erro inesperado", List.of());
    }
}
