package br.com.caiogit.datajpa.libraryapi.service;


import br.com.caiogit.datajpa.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LivroService
{
    private final LivroRepository livrorepository;


}
