package com.desafio.backend.unit.useCases.contato;

import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.application.useCases.contato.CadastrarContatoUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
import com.desafio.backend.enterprise.contato.Contato;
import org.junit.jupiter.api.BeforeEach;
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
class CadastrarContatoUseCaseTest {

    @Autowired
    private CadastrarClienteUseCase cadastrarCliente;

    @Autowired
    private CadastrarContatoUseCase cadastrarContato;

    private Cliente clienteSalvo;

    @BeforeEach
    void setup() {
        clienteSalvo = cadastrarCliente.execute(
                new Cliente(null, "João", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "alguma coisa")
        );
    }

    @Test
    void deveCadastrarContatoComSucesso() {
        Contato contato = new Contato(null, clienteSalvo.getId(), "Email", "joao@email.com", "alguma coisa");

        Contato salvo = cadastrarContato.execute(contato);

        assertNotNull(salvo.getId());
    }

    @Test
    void deveLancarExcecaoQuandoTipoVazio() {
        Contato contato = new Contato(null, clienteSalvo.getId(), "", "joao@email.com", "alguma coisa");

        assertThrows(IllegalArgumentException.class, () -> cadastrarContato.execute(contato));
    }

    @Test
    void deveLancarExcecaoQuandoValorVazio() {
        Contato contato = new Contato(null, clienteSalvo.getId(), "Email", "", "alguma coisa");

        assertThrows(IllegalArgumentException.class, () -> cadastrarContato.execute(contato));
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        Contato contato = new Contato(null, 9999, "Email", "joao@email.com", "alguma coisa");

        assertThrows(ResourceNotFoundException.class, () -> cadastrarContato.execute(contato));
    }
}
