package br.com.caiogit.datajpa.libraryapi.service;


import br.com.caiogit.datajpa.libraryapi.enums.GeneroLivro;
import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.model.Livro;
import br.com.caiogit.datajpa.libraryapi.repository.AutorRepository;
import br.com.caiogit.datajpa.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransacaoService
{
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;


    @Transactional
    public void executar()
    {
        // salva o autor primeiro
        Autor autor = new Autor();

        autor.setNome("Valdevina");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1954, 4, 7));

        autorRepository.saveAndFlush(autor);


        //salva o livro
        Livro livro = new Livro();

        livro.setGeneroLivro(GeneroLivro.FICCAO);
        livro.setIsbn("94567-75235");
        livro.setPreco(BigDecimal.valueOf(69.99));
        livro.setTitulo("Beterraba");
        livro.setDataPublicacao(LocalDate.of(1981,11,27));

        livro.setAutor(autor);

        livroRepository.saveAndFlush(livro);


        if(autor.getNome().equals("Valdevina"))
        {
            throw new RuntimeException("RollBack!!!");
        }
    }

}
