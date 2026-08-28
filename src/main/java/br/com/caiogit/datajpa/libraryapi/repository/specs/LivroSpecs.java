package br.com.caiogit.datajpa.libraryapi.repository.specs;

import br.com.caiogit.datajpa.libraryapi.enums.GeneroLivro;
import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs
{
    public static Specification<Livro> isbnEqual(String isbn)
    {
        return (root,query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo)
    {
        return (root, query, cb) ->
                cb.like(
                        cb.upper(root.get("titulo")),
                        "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero)
    {
        return ((root, query, cb) -> cb.equal(root.get("generoLivro"), genero));
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao)
    {
        // select to_char(dataPublicacao, 'YYYY') from livro;
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class,
                        root.get("dataPublicacao"), cb.literal("YYYY")),anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome)
    {
        return (root, query, cb) -> {
            //return cb.like(cb.upper(root.get("Autor").get("nome")), "%" + nome.toUpperCase() + "%");

            Join<Livro, Autor> autor = root.join("autor", JoinType.LEFT);

            return cb.like(cb.upper(autor.get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }
}
