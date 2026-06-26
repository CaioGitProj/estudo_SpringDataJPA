package br.com.caiogit.datajpa.libraryapi.repository;

import br.com.caiogit.datajpa.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class AutorRepositoryTest
{
    @Autowired
    AutorRepository autorRepository;


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
}