package br.com.caiogit.datajpa.libraryapi.service;


import br.com.caiogit.datajpa.libraryapi.enums.GeneroLivro;
import br.com.caiogit.datajpa.libraryapi.model.Livro;
import br.com.caiogit.datajpa.libraryapi.repository.LivroRepository;
import br.com.caiogit.datajpa.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static br.com.caiogit.datajpa.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService
{
    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;

    public void salvarLivro(Livro livro)
    {
        livroValidator.validarLivro(livro);
        livroRepository.save(livro);
    }

    public Optional<Livro> obterLivroPorId(UUID id)
    {
        return livroRepository.findById(id);
    }

    public void deletarLivro(Livro livro)
    {
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisarLivro(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao)
    {
        // select * from livro where isbn = :isbn and nomeAutor = :nomeAutor
        // conjunction é um where que sempre vai dar positivo( 0 = 0)
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if(isbn != null) {
            specs = specs.and(isbnEqual(isbn));
        }

        if(titulo != null) {
            specs = specs.and(tituloLike(titulo));
        }
        if(genero != null) {
            specs = specs.and(generoEqual(genero));
        }

        if(anoPublicacao != null) {
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if(nomeAutor != null) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }
        return livroRepository.findAll(specs);
    }

    public void atualizarLivro(Livro livro)
    {
        if(livro.getId() == null)
        {
            throw new IllegalArgumentException("Para atualizar, é necessário que o livro já esteja na base de dados");
        }

        livroValidator.validarLivro(livro);
        livroRepository.save(livro);
    }
}
