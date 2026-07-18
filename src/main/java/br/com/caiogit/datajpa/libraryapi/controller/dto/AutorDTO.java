package br.com.caiogit.datajpa.libraryapi.controller.dto;


import br.com.caiogit.datajpa.libraryapi.model.Autor;

import java.time.LocalDate;

public record AutorDTO(
        String nome,
        LocalDate dataNascimento,
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
