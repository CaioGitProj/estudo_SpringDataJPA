package br.com.caiogit.datajpa.libraryapi.controller;

import br.com.caiogit.datajpa.libraryapi.controller.dto.AutorDTO;
import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/autores") //http://localhst:8080/autores
public class AutorController
{

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }


    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO dto)
    {
        Autor autor = dto.transformarAutor();
        autorService.salvarAutor(autor);

        //http://localhst:8080/autores/{id}
        //Caso ele consiga salvar no banco, retorna na url o id do autor criado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(autor.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
