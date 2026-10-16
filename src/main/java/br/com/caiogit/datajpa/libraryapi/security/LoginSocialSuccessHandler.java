package br.com.caiogit.datajpa.libraryapi.security;

import br.com.caiogit.datajpa.libraryapi.model.Usuario;
import br.com.caiogit.datajpa.libraryapi.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
    private final UsuarioService usuarioService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User principal = auth2AuthenticationToken.getPrincipal();

        String email = principal.getAttribute("email");
        Usuario usuario = usuarioService.obterUsuarioPorEmail(email);

        CustomAuthentication customAuthentication = new CustomAuthentication(usuario);
        SecurityContextHolder.getContext().setAuthentication(customAuthentication); //Muda a authenticação padrão para a que a gente criou

        super.onAuthenticationSuccess(request, response, customAuthentication);
    }
}
