package br.com.caiogit.datajpa.libraryapi.exceptions;

public class RegistroDuplicadoException extends RuntimeException
{
    public RegistroDuplicadoException(String message) {
        super(message);
    }
}
