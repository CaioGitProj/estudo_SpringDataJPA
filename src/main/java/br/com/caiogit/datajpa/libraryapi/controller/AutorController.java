package br.com.caiogit.datajpa.libraryapi.controller;

import br.com.caiogit.datajpa.libraryapi.controller.dto.AutorDTO;
import br.com.caiogit.datajpa.libraryapi.controller.dto.error.ErrorResposta;
import br.com.caiogit.datajpa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import br.com.caiogit.datajpa.libraryapi.exceptions.RegistroDuplicadoException;
import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores") //http://localhst:8080/autores
@RequiredArgsConstructor
public class AutorController
{
    private final AutorService autorService;


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto)
    {
        try {
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
        catch(RegistroDuplicadoException e) {

            var erroDTO = ErrorResposta.conflito(e.getMessage());

            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
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
    public ResponseEntity<Object> deletarAutor(@PathVariable("id") String id)
    {
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = autorService.obterAutorPorId(idAutor);

            if(autorOptional.isEmpty())
            {
                return ResponseEntity.notFound().build();
            }

            autorService.deletarAutor(autorOptional.get());

            return ResponseEntity.noContent().build();
        }
        catch(OperacaoNaoPermitidaException e)
        {
            var erroResposta = ErrorResposta.respostaPadrao(e.getMessage());

            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<AutorDTO>> pesquisarAutores(
            @RequestParam(value ="nome", required = false) String nome,
            @RequestParam(value ="nacionalidade", required = false) String nacionalidade)
    {

        List<Autor> resultadoAutor = autorService.pesquisarAutorByExample(nome, nacionalidade);

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

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarAutor(@PathVariable("id") String id, @RequestBody AutorDTO dto)
    {
        try {
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
        catch (RegistroDuplicadoException e) {
            var erroDTO = ErrorResposta.conflito(e.getMessage());

            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
