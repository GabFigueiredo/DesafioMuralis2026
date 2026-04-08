package com.desafio.backend.unit.useCases.cliente;

import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.application.useCases.cliente.ExcluirClienteUseCase;
import com.desafio.backend.application.useCases.contato.CadastrarContatoUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.IContatoRepository;
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
class ExcluirClienteUseCaseTest {

    @Autowired
    private CadastrarClienteUseCase cadastrarCliente;

    @Autowired
    private CadastrarContatoUseCase cadastrarContato;

    @Autowired
    private ExcluirClienteUseCase excluirCliente;

    @Autowired
    private IContatoRepository contatoRepository;

    @Test
    void deveExcluirClienteComSucesso() {
        Cliente salvo = cadastrarCliente.execute(
                new Cliente(null, "João", "111.111.111-11", LocalDate.of(1990, 1, 1), null)
        );

        assertDoesNotThrow(() -> excluirCliente.execute(salvo.getId()));
    }

    @Test
    void deveExcluirContatosAoExcluirCliente() {
        Cliente salvo = cadastrarCliente.execute(
                new Cliente(null, "João", "111.111.111-11", LocalDate.of(1990, 1, 1), null)
        );
        cadastrarContato.execute(
                new Contato(null, salvo.getId(), "Email", "joao@email.com", null)
        );

        excluirCliente.execute(salvo.getId());

        // cascade should have removed contacts
        List<Contato> contatos = contatoRepository.findByClienteId(salvo.getId());
        assertTrue(contatos.isEmpty());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        assertThrows(IllegalArgumentException.class, () -> excluirCliente.execute(9999));
    }
}
