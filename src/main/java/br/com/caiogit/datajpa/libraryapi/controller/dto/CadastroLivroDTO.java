package br.com.caiogit.datajpa.libraryapi.controller.dto;

import br.com.caiogit.datajpa.libraryapi.enums.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
                               @NotNull(message = "Campo obrigatório")
                               UUID idAutor,

                               @NotBlank(message = "Campo obrigatório")
                               @ISBN
                               String isbn,

                               @NotBlank(message = "Campo obrigatório")
                               String titulo,

                               @NotNull(message = "Campo obrigatório")
                               @Past
                               LocalDate dataPublicacao,

                               GeneroLivro generoLivro,
                               BigDecimal preco
) {
}
