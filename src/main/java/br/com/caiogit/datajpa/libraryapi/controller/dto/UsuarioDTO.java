package br.com.caiogit.datajpa.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(

        @NotBlank
        String login,

        @NotBlank
        String senha,

        @Email(message = "Email inválido")
        String email,

        List<String> roles) {
}
