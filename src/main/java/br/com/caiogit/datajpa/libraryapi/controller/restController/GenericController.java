package br.com.caiogit.datajpa.libraryapi.controller.restController;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface GenericController
{
    // Cria uma header com uma URL desse tipo: http://caminho:porta/Object?/{id}
    //exemplo : http://localhst:8080/autores/{id}
    default URI gerarHeaderLocation(UUID id)
    {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();
    }

}
