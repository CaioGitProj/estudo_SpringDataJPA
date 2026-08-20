package br.com.caiogit.datajpa.libraryapi.controller.dto.mappers;

import br.com.caiogit.datajpa.libraryapi.controller.dto.CadastroLivroDTO;
import br.com.caiogit.datajpa.libraryapi.model.Livro;
import br.com.caiogit.datajpa.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper
{
    @Autowired
    AutorRepository autorRepository;

    //Como Livro precisa de um objeto do tipo autor, tem que acessar o banco de dados(autor tem que existir)
    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);
}
