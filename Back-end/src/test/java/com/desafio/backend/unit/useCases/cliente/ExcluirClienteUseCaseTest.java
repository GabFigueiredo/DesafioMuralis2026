package com.desafio.backend.unit.useCases.cliente;

import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.application.useCases.cliente.ExcluirClienteUseCase;
import com.desafio.backend.application.useCases.contato.CadastrarContatoUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.IContatoRepository;
import com.desafio.backend.enterprise.contato.enums.TipoContato;
import com.desafio.backend.enterprise.contato.valueObjects.ContatoValor;
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
                new Cliente(null, "João", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A, 123")
        );

        assertDoesNotThrow(() -> excluirCliente.execute(salvo.getId()));
    }

    @Test
    void deveExcluirContatosAoExcluirCliente() {
        Cliente salvo = cadastrarCliente.execute(
                new Cliente(null, "João", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A, 123")
        );
        cadastrarContato.execute(
                new Contato(null, salvo.getId(), new ContatoValor(TipoContato.EMAIL, "joao@email.com"), "Rua A, 123")
        );

        excluirCliente.execute(salvo.getId());

        // cascade should have removed contacts
        List<Contato> contatos = contatoRepository.findByClienteId(salvo.getId());
        assertTrue(contatos.isEmpty());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        assertThrows(ResourceNotFoundException.class, () -> excluirCliente.execute(9999));
    }
}
