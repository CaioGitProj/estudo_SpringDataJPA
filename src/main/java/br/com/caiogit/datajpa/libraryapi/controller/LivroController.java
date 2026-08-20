package br.com.caiogit.datajpa.libraryapi.controller;

import br.com.caiogit.datajpa.libraryapi.controller.dto.CadastroLivroDTO;
import br.com.caiogit.datajpa.libraryapi.controller.dto.error.ErrorResposta;
import br.com.caiogit.datajpa.libraryapi.controller.dto.mappers.LivroMapper;
import br.com.caiogit.datajpa.libraryapi.exceptions.RegistroDuplicadoException;
import br.com.caiogit.datajpa.libraryapi.model.Livro;
import br.com.caiogit.datajpa.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
