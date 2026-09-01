package br.com.caiogit.datajpa.libraryapi.validator;

import br.com.caiogit.datajpa.libraryapi.exceptions.CampoInvalidoException;
import br.com.caiogit.datajpa.libraryapi.exceptions.RegistroDuplicadoException;
import br.com.caiogit.datajpa.libraryapi.model.Livro;
import br.com.caiogit.datajpa.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator
{
    private static final int ANO_EXIGENCIA_PRECO = 22;

    private final LivroRepository livroRepository;

    public void validarLivro(Livro livro)
    {
        if(existeLivroComIsbn(livro))
        {
            throw new RegistroDuplicadoException("ISBN já cadastrado");
        }

        if(isPrecoObrigatorioNulo(livro))
        {
            throw new CampoInvalidoException("preco", "Para livros com ano de publicação a partir de 2020, o preço é obrigatório");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro)
    {
        return livro.getPreco() == null
                && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComIsbn(Livro livro)
    {
        Optional<Livro> livroEncontrado = livroRepository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null)
        {
            return livroEncontrado.isPresent();
        }

        return livroEncontrado.map(Livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));
    }
}
