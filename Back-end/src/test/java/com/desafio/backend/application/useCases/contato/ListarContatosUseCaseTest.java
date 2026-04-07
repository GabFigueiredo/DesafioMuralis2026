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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ListarContatosUseCaseTest {

    @Autowired
    private CadastrarClienteUseCase cadastrarCliente;

    @Autowired
    private CadastrarContatoUseCase cadastrarContato;

    @Autowired
    private ListarContatosUseCase listarContatos;

    @Test
    void deveListarContatosDoCliente() {
        Cliente cliente = cadastrarCliente.execute(
                new Cliente(null, "João", "111.111.111-11", LocalDate.of(1990, 1, 1), null)
        );
        cadastrarContato.execute(new Contato(null, cliente.getId(), "Email", "joao@email.com", null));
        cadastrarContato.execute(new Contato(null, cliente.getId(), "Telefone", "11999999999", null));

        List<Contato> contatos = listarContatos.execute(cliente.getId());

        assertEquals(2, contatos.size());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        assertThrows(IllegalArgumentException.class, () -> listarContatos.execute(9999));
    }

    @Test
    void deveRetornarListaVaziaSeClienteSemContatos() {
        Cliente cliente = cadastrarCliente.execute(
                new Cliente(null, "João", "111.111.111-11", LocalDate.of(1990, 1, 1), null)
        );

        List<Contato> contatos = listarContatos.execute(cliente.getId());

        assertTrue(contatos.isEmpty());
    }
}
