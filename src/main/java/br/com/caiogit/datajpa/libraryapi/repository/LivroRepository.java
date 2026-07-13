package br.com.caiogit.datajpa.libraryapi.repository;

import br.com.caiogit.datajpa.libraryapi.enums.GeneroLivro;
import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
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
            select l.generoLivro
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileira'
            """)
    List<String> listGeneroByAutor();


    //Usando os parâmetros dos métodos dentro das consultas
    @Query(" select l from Livro l where l.generoLivro = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(@Param("genero") GeneroLivro genero, @Param("paramOrdenacao") String nomePropriedade);


    //Delete usando parâmetro posicional
    @Modifying //Eu preciso dessa anotação, pois estou modificando o banco
    @Transactional //Garante que a operação seja executada numa transação segura
    @Query(" delete from Livro where generoLivro = ?1 ")
    void deleteByGeneroLivro(GeneroLivro genero);


    @Modifying
    @Transactional
    @Query(" update Livro l set l.dataPublicacao = :novaData where l.dataPublicacao < :dataUpdate ")
    void updateDataPublicacao(@Param("novaData") LocalDate novaData, @Param("dataUpdate") LocalDate dataUpdate);
}