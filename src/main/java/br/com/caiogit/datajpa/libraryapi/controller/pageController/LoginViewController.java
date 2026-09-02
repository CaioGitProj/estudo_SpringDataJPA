package br.com.caiogit.datajpa.libraryapi.controller.PageController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController
{
    @GetMapping("/login")
    public String paginaLogin()
    {
        return "login";
    }
}
