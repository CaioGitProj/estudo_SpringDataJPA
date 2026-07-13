package br.com.caiogit.datajpa.libraryapi.repository;


import br.com.caiogit.datajpa.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransacoesTest
{

    @Autowired
    TransacaoService transacaoService;

    /**
     * Commit -> confirmar as alterações
     * Rollback -> desfaz as alterações
     * Ou faz todas as transações, ou dá rollback
     */

    @Test
    void trasacaoSimples()
    {
        transacaoService.executar();
    }
}
