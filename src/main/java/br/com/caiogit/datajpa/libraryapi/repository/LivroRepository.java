package br.com.caiogit.datajpa.libraryapi.repository;

import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface LivroRepository extends JpaRepository<Livro, UUID>
{
    // QUERY Method
    List<Livro> findByAutor(Autor autor);
}
