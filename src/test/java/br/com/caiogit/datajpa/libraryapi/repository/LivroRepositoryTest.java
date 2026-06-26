package br.com.caiogit.datajpa.libraryapi.repository;

import br.com.caiogit.datajpa.libraryapi.enums.GeneroLivro;
import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.model.Livro;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class LivroRepositoryTest
{
    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void saveTest()
    {
        Livro livro = new Livro();

        livro.setGeneroLivro(GeneroLivro.FICCAO);
        livro.setIsbn("90135-902317");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        var autorLivro = autorRepository
                .findById(UUID.fromString("1dfd86d7-e5e3-45e6-aced-49a4d69ecd33"))
                .orElseThrow(() -> new IllegalArgumentException("Autor não existe"));



        livro.setAutor(autorLivro);
        livroRepository.save(livro);
    }

    @Test
    void saveCascadeTest()
    {
        Livro livro = new Livro();

        livro.setGeneroLivro(GeneroLivro.FICCAO);
        livro.setIsbn("94567-75235");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setTitulo("UFO 2");
        livro.setDataPublicacao(LocalDate.of(1981,11,27));


        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(2002, 7, 15));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }
}
