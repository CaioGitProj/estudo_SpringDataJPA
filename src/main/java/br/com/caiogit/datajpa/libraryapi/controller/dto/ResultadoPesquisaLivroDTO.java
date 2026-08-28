package br.com.caiogit.datajpa.libraryapi.controller.dto;

import br.com.caiogit.datajpa.libraryapi.enums.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivroDTO(
        UUID id,

        String isbn,

        String titulo,

        LocalDate dataPublicacao,

        GeneroLivro generoLivro,
        BigDecimal preco,
        AutorDTO autor
) {
}
