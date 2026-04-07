package com.desafio.backend.application.useCases.contato;

import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.contato.Contato;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ExcluirContatoUseCaseTest {

    @Autowired
    private CadastrarClienteUseCase cadastrarCliente;

    @Autowired
    private CadastrarContatoUseCase cadastrarContato;

    @Autowired
    private ExcluirContatoUseCase excluirContato;

    @Test
    void deveExcluirContatoComSucesso() {
        Cliente cliente = cadastrarCliente.execute(
                new Cliente(null, "João", "111.111.111-11", LocalDate.of(1990, 1, 1), null)
        );
        Contato salvo = cadastrarContato.execute(
                new Contato(null, cliente.getId(), "Email", "joao@email.com", null)
        );

        assertDoesNotThrow(() -> excluirContato.execute(salvo.getId()));
    }

    @Test
    void deveLancarExcecaoQuandoContatoNaoExiste() {
        assertThrows(IllegalArgumentException.class, () -> excluirContato.execute(9999));
    }
}