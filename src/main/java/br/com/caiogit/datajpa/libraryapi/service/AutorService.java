package br.com.caiogit.datajpa.libraryapi.service;

import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService
{

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository)
    {
        this.autorRepository = autorRepository;
    }


    public Autor salvarAutor(Autor autor)
    {
        return autorRepository.save(autor);
    }
}
