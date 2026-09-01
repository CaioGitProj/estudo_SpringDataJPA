package br.com.caiogit.datajpa.libraryapi.exceptions;

public class CampoInvalidoException extends RuntimeException {
  public CampoInvalidoException(String message) {
    super(message);
  }
}
