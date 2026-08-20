package br.com.caiogit.datajpa.libraryapi.controller.dto.mappers;

import br.com.caiogit.datajpa.libraryapi.controller.dto.AutorDTO;
import br.com.caiogit.datajpa.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper
{
    Autor toEntity(AutorDTO dto); //transforma obj AutorDTO em Autor

    AutorDTO toDTO(Autor autor); //transforma obj Autor em AutorDTO
}
