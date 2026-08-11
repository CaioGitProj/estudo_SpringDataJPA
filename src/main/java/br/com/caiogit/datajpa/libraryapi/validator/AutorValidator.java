package br.com.caiogit.datajpa.libraryapi.validator;

import br.com.caiogit.datajpa.libraryapi.exceptions.RegistroDuplicadoException;
import br.com.caiogit.datajpa.libraryapi.model.Autor;
import br.com.caiogit.datajpa.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator
{
    private final AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }


    public void validar(Autor autor)
    {
        if(existeAutorCadastrado(autor))
        {
            throw new RegistroDuplicadoException
                    ("Autor já cadastrado! Cadastro atual possui o mesmo nome, data de nascimento e nacionalidade de outro registro");
        }
    }

    private boolean existeAutorCadastrado(Autor autor)
    {
        Optional<Autor> autorEncontrado = autorRepository
                .findByNomeAndDataNascimentoAndNacionalidade(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());


        if(autor.getId() == null)
        {
            return autorEncontrado.isPresent();
        }

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
