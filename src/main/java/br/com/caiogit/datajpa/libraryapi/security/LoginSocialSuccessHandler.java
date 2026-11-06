package br.com.caiogit.datajpa.libraryapi.security;

import br.com.caiogit.datajpa.libraryapi.model.Usuario;
import br.com.caiogit.datajpa.libraryapi.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
    private static final String GENERIC_PASSWORD = "321";

    private final UsuarioService usuarioService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User principal = auth2AuthenticationToken.getPrincipal();

        String email = principal.getAttribute("email");
        Usuario usuario = usuarioService.obterUsuarioPorEmail(email);

        // Usuário apenas tem login pelo google
        if(usuario == null)
        {
            usuario = cadastrarUsuarioNaBase(email);
        }

        CustomAuthentication customAuthentication = new CustomAuthentication(usuario);
        SecurityContextHolder.getContext().setAuthentication(customAuthentication); //Muda a authenticação padrão para a que a gente criou

        super.onAuthenticationSuccess(request, response, customAuthentication);
    }

    private static @NonNull Usuario cadastrarUsuarioNaBase(String email)
    {
        Usuario usuario;
        usuario = new Usuario();

        usuario.setEmail(email);
        usuario.setLogin(obterLoginApartirEmail(email));
        usuario.setSenha(GENERIC_PASSWORD);
        usuario.setRoles(List.of("OPERADOR"));
        return usuario;
    }

    private static String obterLoginApartirEmail(String email)
    {
        return email.substring(0, email.indexOf("@"));
    }

}
