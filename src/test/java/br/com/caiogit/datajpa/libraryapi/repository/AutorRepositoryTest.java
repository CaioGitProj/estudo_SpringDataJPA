package br.com.caiogit.datajpa.libraryapi.repository;

import br.com.caiogit.datajpa.libraryapi.enums.GeneroLivro;
import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class AutorRepositoryTest
{
    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;


    @Test
    void saveTest()
    {
        Autor autor = new Autor();

        autor.setNome("José");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1950, 1, 31));

        var autorSalvo = autorRepository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);

        System.out.println("Deletando autor que já existe...");
        autorRepository.delete(autorSalvo);
    }


    @Test
    @Transient
    void updateTest()
    {
        var id = UUID.fromString("1dfd86d7-e5e3-45e6-aced-49a4d69ecd33");

        Autor possivelAutor = autorRepository.findById(id).orElse(null);

        if(possivelAutor != null)
        {
            var novoAutor = possivelAutor;

            novoAutor.setDataNascimento(LocalDate.of(1960, 2, 17));

            autorRepository.save(novoAutor);
        }
    }

    @Test
    void listTest()
    {
        List<Autor> listaAutor = autorRepository.findAll();

        listaAutor.forEach(System.out::println);
    }

    @Test
    void countTest()
    {
        System.out.println("Contagem de autores: " + autorRepository.count());
    }

    @Test
    void deleteById()
    {
        var id = UUID.fromString("1dfd86d7-e5e3-45e6-aced-49a4d69ecd33");

        if(autorRepository.findById(id).isPresent())
        {
            var jose = autorRepository.findById(id).get();
            autorRepository.delete(jose);
        }
    }

    @Test
    void saveAutorWithLivroTest()
    {
        Livro livro = new Livro();
        Livro livro2 = new Livro();

        Autor autor = new Autor();
        autor.setNome("Cézar");
        autor.setNacionalidade("Marroquino");
        autor.setDataNascimento(LocalDate.of(2005, 4, 15));

        livro.setGeneroLivro(GeneroLivro.BIOGRAFIA);
        livro.setIsbn("94567-810967");
        livro.setPreco(BigDecimal.valueOf(39.99));
        livro.setTitulo("Minha histoinra como cidadeum do Marrocos");
        livro.setDataPublicacao(LocalDate.of(2020,3,2));
        livro.setAutor(autor);

        livro2.setGeneroLivro(GeneroLivro.CIENCIA);
        livro2.setIsbn("18560-12856");
        livro2.setPreco(BigDecimal.valueOf(10.20));
        livro2.setTitulo("Toma no cu corona");
        livro2.setDataPublicacao(LocalDate.of(2021,1,1));
        livro2.setAutor(autor);


        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);
    }
}