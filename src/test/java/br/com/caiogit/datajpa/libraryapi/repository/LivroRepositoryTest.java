package br.com.caiogit.datajpa.libraryapi.repository;

import br.com.caiogit.datajpa.libraryapi.enums.GeneroLivro;
import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.model.Livro;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


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



    @Test
    void updateTest()
    {
        var livroSalvo = livroRepository.findById(UUID.fromString("3d19a42e-ff04-40e3-b0fc-58a0c36fe5a9"))
                .orElseThrow(() -> new IllegalArgumentException("Não existe um livro com esse Id"));


        UUID idAutorLivro = UUID.fromString("57ac8ed2-dc4c-4fa8-a373-e93ebec75036");

        var novoAutorLivro = autorRepository.findById(idAutorLivro)
                .orElseThrow(()-> new IllegalArgumentException("Não existe um autor com esse id"));


        livroSalvo.setAutor(novoAutorLivro);

        livroRepository.save(livroSalvo);
    }

    @Test
    void deleteTest()
    {
        UUID livroParaDeletar = UUID.fromString("3d19a42e-ff04-40e3-b0fc-58a0c36fe5a9");

        livroRepository.deleteById(livroParaDeletar);
    }

    @Test
    //Abre uma janela no banco para buscar coisas fora da tabela(padrão para lazy, estratégia fetch(ver classe Livro))
    @Transactional
    void findTest()
    {
        var livroBusca = livroRepository.findById
                (UUID.fromString("fa2be9eb-f4aa-4473-9410-0b1a3a63e9ba"))
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));


        System.out.println("Livro: " + livroBusca.getTitulo());
        System.out.println("Autor: " + livroBusca.getAutor().getNome());
    }

    @Test
    void findTituloTest()
    {
        List<Livro> listaLivro = livroRepository.findByTitulo("Roubo na casa Assombrada");

        listaLivro.forEach(System.out::println);
    }

    @Test
    void findPrecoGreaterThanTest()
    {
        var preco = BigDecimal.valueOf(35.99);

        List<Livro> listaLivro = livroRepository.findByPrecoGreaterThan(preco);
        listaLivro.forEach(System.out::println);
    }

    @Test
    void findByAutorAndTituloTest()
    {
        var autorLivro = autorRepository.findById(UUID.fromString("fc575bc4-a1d3-40cf-b120-4512607de86e"))
                .orElseThrow(()-> new IllegalArgumentException("esse autor não existe"));

        Livro livro = livroRepository
                .findByAutorAndTitulo(autorLivro, "Roubo na casa Assombrada");

        System.out.println("Livro da minha pesquisa: " + livro);
    }

    @Test
    void findByTituloContainingIgnoreCaseTest()
    {
        var livro = livroRepository.findByTituloContainingIgnoreCase("casa");

        livro.forEach(System.out::println);
    }

    @Test
    void listEveryoneByTituloAndPrecoTest()
    {
        var livros = livroRepository.listEveryoneByTituloAndPreco();

        livros.forEach(System.out::println);
    }

    @Test
    void findAutorOfLivrosTest()
    {
        var autores = livroRepository.findAutorOfLivros();

        autores.forEach(System.out::println);
    }

    @Test
    void findByGeneroQueryParamTest()
    {
        var livros = livroRepository.findByGenero(GeneroLivro.MISTERIO, "preco");

        livros.forEach(System.out::println);
    }

    @Test
    void deleteByGeneroLivroTest()
    {
        livroRepository.deleteByGeneroLivro(GeneroLivro.BIOGRAFIA);
    }

    @Test
    void updateDataPublicacaoTest()
    {
        livroRepository.updateDataPublicacao(LocalDate.of(1980,1,1), LocalDate.of(1980,1, 1));
    }
}
