package br.com.caiogit.datajpa.libraryapi.security;

import br.com.caiogit.datajpa.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService
{

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
    {
        var usuario = usuarioService.obterUsuarioPorLogin(login);

        if(usuario.isEmpty())
        {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return User.builder()
                .username(usuario.get().getLogin())
                .password(usuario.get().getSenha())
                .roles(usuario.get().getRoles().toArray(String[]::new))
                .build();
    }

}
