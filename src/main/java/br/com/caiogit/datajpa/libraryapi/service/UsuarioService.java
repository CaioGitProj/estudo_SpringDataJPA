package br.com.caiogit.datajpa.libraryapi.service;

import br.com.caiogit.datajpa.libraryapi.model.Usuario;
import br.com.caiogit.datajpa.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService
{
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder; //Em securityConfiguration

    public void salvarUsuario(Usuario usuario)
    {
        var senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));

        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> obterUsuarioPorLogin(String login)
    {
        return usuarioRepository.findByLogin(login);
    }

    public Usuario obterUsuarioPorEmail(String email)
    {
        return usuarioRepository.findByEmail(email);
    }

}
