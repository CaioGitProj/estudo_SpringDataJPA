package br.com.caiogit.datajpa.libraryapi.service;

import br.com.caiogit.datajpa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.repository.AutorRepository;
import br.com.caiogit.datajpa.libraryapi.repository.LivroRepository;
import br.com.caiogit.datajpa.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService
{

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final AutorValidator validator;


    public Autor salvarAutor(Autor autor)
    {
        validator.validar(autor);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor)
    {
        if(autor.getId() == null)
        {
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor exista");
        }
        validator.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> obterAutorPorId(UUID id)
    {
        return autorRepository.findById(id);
    }

    private boolean possuiLivro(Autor autor)
    {
        return livroRepository.existsByAutor(autor);
    }

    public void deletarAutor(Autor autor)
    {
        if(possuiLivro(autor))
        {
            throw new OperacaoNaoPermitidaException("Não é permitido excluir um autor que possui livros cadastrados");
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisarAutor(String nome, String nacionalidade)
    {
        if(nome != null && nacionalidade != null) {
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        else if(nome != null) {
            return autorRepository.findByNome(nome);
        }

        else if(nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();

    }
}
