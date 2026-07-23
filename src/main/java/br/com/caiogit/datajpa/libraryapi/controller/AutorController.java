package br.com.caiogit.datajpa.libraryapi.controller;

import br.com.caiogit.datajpa.libraryapi.controller.dto.AutorDTO;
import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
                .path("/{id}")
                .buildAndExpand(autor.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obterDetalhesAutor(@PathVariable("id") String id)
    {

        try
        {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.obterAutorPorId(idAutor);

            if(autorOptional.isPresent())
            {
                Autor entidade = autorOptional.get();

                AutorDTO dto = new AutorDTO(
                        entidade.getId(),
                        entidade.getNome(),
                        entidade.getDataNascimento(),
                        entidade.getNacionalidade());

                return ResponseEntity.ok(dto);
            }
        }
        catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.notFound().build();

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable("id") String id)
    {

        try
        {
            var idAutor = UUID.fromString(id);
            if(autorService.obterAutorPorId(idAutor).isPresent())
            {
                Optional<Autor> autorOptional = autorService.obterAutorPorId(idAutor);

                autorService.deletarAutor(autorOptional.get());
            }
            else
            {
                return ResponseEntity.notFound().build();
            }

        }
        catch(IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();

    }

    @GetMapping("")
    public ResponseEntity<List<AutorDTO>> pesquisarAutores(
            @RequestParam(value ="nome", required = false) String nome,
            @RequestParam(value ="nacionalidade", required = false) String nacionalidade)
    {

        List<Autor> resultadoAutor = autorService.pesquisarAutor(nome, nacionalidade);

        List<AutorDTO> listaRetorno = resultadoAutor
                .stream()
                .map(
                        (autor) -> new AutorDTO(autor.getId(),
                                autor.getNome(),
                                autor.getDataNascimento(),
                                autor.getNacionalidade())
                )
                .toList();


        return ResponseEntity.ok(listaRetorno);
    }
}
