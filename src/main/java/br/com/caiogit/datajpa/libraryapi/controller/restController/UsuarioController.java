package br.com.caiogit.datajpa.libraryapi.controller.restController;

import br.com.caiogit.datajpa.libraryapi.controller.dto.UsuarioDTO;
import br.com.caiogit.datajpa.libraryapi.controller.dto.mappers.UsuarioMapper;
import br.com.caiogit.datajpa.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController
{
    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvarUsuario(@RequestBody UsuarioDTO dto)
    {
        var usuario = mapper.toEntity(dto);
        usuarioService.salvarUsuario(usuario);
    }
}
