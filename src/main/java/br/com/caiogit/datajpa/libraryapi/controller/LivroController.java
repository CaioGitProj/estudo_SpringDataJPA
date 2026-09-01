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
import org.springframework.data.domain.Page;
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

    @GetMapping
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisaLivroSpec(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "nome-autor",required = false) String nomeAutor,
            @RequestParam(value = "genero", required = false)GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false) Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina
    )
    {
        var paginaResultado = livroService.pesquisarLivro(isbn, titulo,
                nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);

        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);

        return ResponseEntity.ok(resultado);
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

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarLivro(@PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO dto)
    {
        return livroService
                .obterLivroPorId(UUID.fromString(id))
                .map(livro -> {

                    Livro entityAux = mapper.toEntity(dto);

                    livro.setDataPublicacao(entityAux.getDataPublicacao());
                    livro.setIsbn(entityAux.getIsbn());

                    if(entityAux.getPreco() != null)
                    {
                        livro.setPreco(entityAux.getPreco());
                    }
                    if(entityAux.getGeneroLivro() != null)
                    {
                        livro.setGeneroLivro(entityAux.getGeneroLivro());
                    }

                    livro.setTitulo(entityAux.getTitulo());
                    livro.setAutor(entityAux.getAutor());

                    livroService.atualizarLivro(livro);

                    return ResponseEntity.noContent().build();

                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
