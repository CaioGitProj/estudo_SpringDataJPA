package br.com.caiogit.datajpa.libraryapi.controller.dto.mappers;

import br.com.caiogit.datajpa.libraryapi.controller.dto.UsuarioDTO;
import br.com.caiogit.datajpa.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper
{
    Usuario toEntity(UsuarioDTO dto);
}
