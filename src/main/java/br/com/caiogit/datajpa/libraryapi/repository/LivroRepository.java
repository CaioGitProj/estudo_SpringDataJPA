package br.com.caiogit.datajpa.libraryapi.repository;

import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @see br.com.caiogit.datajpa.libraryapi.repository.LivroRepositoryTest
 */


public interface LivroRepository extends JpaRepository<Livro, UUID> {
    // QUERY Method
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    Livro findByIsbn(String isbn);

    Livro findByAutorAndTitulo(Autor autor, String titulo);

    List<Livro> findByPrecoGreaterThan(BigDecimal preco);

    List<Livro> findByTituloContainingIgnoreCase(String titulo);


    //Annotation que permite fazer a sua própria consulta personalizada
    //JPQL -> referencia as entidades e as propriedades
    @Query(" select l from Livro as l order by l.preco, l.titulo ")
    List<Livro> listEveryoneByTituloAndPreco();

    @Query(" select a from Livro l join l.autor a ")
    List<Autor> findAutorOfLivros();

    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileira'
            """)
    List<String> listGeneroByAutor();
}