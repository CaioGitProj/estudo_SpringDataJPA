package br.com.caiogit.datajpa.libraryapi.service;

import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.repository.AutorRepository;
import br.com.caiogit.datajpa.libraryapi.validator.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService
{

    private final AutorRepository autorRepository;
    private final AutorValidator validator;

    public AutorService(AutorRepository autorRepository, AutorValidator validator)
    {
        this.autorRepository = autorRepository;
        this.validator = validator;
    }


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

    public void deletarAutor(Autor autor)
    {
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
