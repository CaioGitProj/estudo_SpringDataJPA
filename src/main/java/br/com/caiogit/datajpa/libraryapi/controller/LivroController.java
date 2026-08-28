package br.com.caiogit.datajpa.libraryapi.controller;

import br.com.caiogit.datajpa.libraryapi.controller.dto.CadastroLivroDTO;
import br.com.caiogit.datajpa.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import br.com.caiogit.datajpa.libraryapi.controller.dto.mappers.LivroMapper;
import br.com.caiogit.datajpa.libraryapi.enums.GeneroLivro;
import br.com.caiogit.datajpa.libraryapi.model.Livro;
import br.com.caiogit.datajpa.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvarLivro(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = mapper.toEntity(dto);

        livroService.salvarLivro(livro);

        var url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhesLivro(@PathVariable("id") String id)
    {
        return livroService
                .obterLivroPorId(UUID.fromString(id))
                .map(livro -> {
            var dto = mapper.toDTO(livro);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarLivro( @PathVariable("id") String id)
    {
        return livroService.obterLivroPorId(UUID.fromString(id))
                .map(livro -> {
            livroService.deletarLivro(livro);
            return ResponseEntity.noContent().build();
        })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisaLivroSpec(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "nome-autor",required = false) String nomeAutor,
            @RequestParam(value = "genero", required = false)GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false) Integer anoPublicacao
            )
    {
        var resultadoPesquisa = livroService.pesquisarLivro(isbn, titulo,nomeAutor,genero,anoPublicacao);

        var lista = resultadoPesquisa.stream().map(mapper::toDTO).toList();

        return ResponseEntity.ok(lista);
    }
}
