package br.com.caiogit.datajpa.libraryapi.controller;

import br.com.caiogit.datajpa.libraryapi.controller.dto.AutorDTO;
import br.com.caiogit.datajpa.libraryapi.controller.dto.mappers.AutorMapper;
import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores") //http://localhst:8080/autores
@RequiredArgsConstructor
public class AutorController implements GenericController {
    private final AutorService autorService;
    private final AutorMapper mapper;


    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        Autor autor = mapper.toEntity(dto);
        autorService.salvarAutor(autor);

        URI location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obterDetalhesAutor(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return autorService.obterAutorPorId(idAutor).map(autor -> {
            AutorDTO dto = mapper.toDTO(autor);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterAutorPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deletarAutor(autorOptional.get());

        return ResponseEntity.noContent().build();

    }

    @GetMapping("")
    public ResponseEntity<List<AutorDTO>> pesquisarAutores(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultadoAutor = autorService.pesquisarAutorByExample(nome, nacionalidade);

        List<AutorDTO> listaRetorno = resultadoAutor
                .stream()
                .map(mapper::toDTO)
                .toList();


        return ResponseEntity.ok(listaRetorno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarAutor(@PathVariable("id") String id, @RequestBody AutorDTO dto) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterAutorPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();

        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();
    }
}