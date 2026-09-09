package br.com.caiogit.datajpa.libraryapi.config;

import br.com.caiogit.datajpa.libraryapi.security.CustomUserDetailsService;
import br.com.caiogit.datajpa.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration
{

    //Pesquisar sobre Authorits, já que o curso não explicou(até agora)

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                //.formLogin(configurer -> configurer.loginPage("/login.html").successForwardUrl("/home.html"))
                .formLogin(configurer -> configurer.loginPage("/login"))
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {

                    authorize.requestMatchers("/login").permitAll();
                    authorize.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll();


                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService)
    {
       //UserDetails user1 = User.builder()
               // .username("usuario")
                //.password(encoder.encode("123"))
              //  .roles("USER")
              //  .build();


        //UserDetails user2 = User.builder()
             //  .username("admin")
              //  .password(encoder.encode("321"))
              //  .roles("ADMIN")
              //  .build();

        return new CustomUserDetailsService(usuarioService);
    }
}
