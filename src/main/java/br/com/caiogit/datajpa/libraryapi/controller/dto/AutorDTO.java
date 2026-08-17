package br.com.caiogit.datajpa.libraryapi.controller.dto;


import br.com.caiogit.datajpa.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,

        @NotBlank(message = "Campo obtrigatório")
        @Size(min = 2,max = 100, message = "Campo fora do tamanho padrão")
        String nome,

        @NotNull(message = "Campo obrigatório")
        @Past(message = "Data de nascimento não pode ser de uma data futura")
        LocalDate dataNascimento,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 2,max = 100, message = "Campo fora do tamanho padrão")
        String nacionalidade)
{
    public Autor transformarAutor()
    {
        Autor autor = new Autor();

        autor.setDataNascimento(this.dataNascimento());
        autor.setNacionalidade(this.nacionalidade());
        autor.setNome(this.nome());

        return autor;
    }
}
