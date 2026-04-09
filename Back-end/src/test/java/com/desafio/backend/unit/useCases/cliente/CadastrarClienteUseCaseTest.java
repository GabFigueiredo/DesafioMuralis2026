package com.desafio.backend.unit.useCases.cliente;

import com.desafio.backend.application.exceptions.ResourceAlreadyExists;
import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
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
class CadastrarClienteUseCaseTest {

    @Autowired
    private CadastrarClienteUseCase cadastrarCliente;

    @Test
    void deveCadastrarClienteComSucesso() {
        Cliente cliente = new Cliente(null, "João Silva", new CPF("63929247011"), LocalDate.of(1990, 1, 15), "Rua A, 123");

        Cliente salvo = cadastrarCliente.execute(cliente);

        assertNotNull(salvo.getId());
        assertEquals("João Silva", salvo.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoNomeVazio() {
        Cliente cliente = new Cliente(null, "", new CPF("63929247011"), LocalDate.of(1990, 1, 15), "Rua A, 123");

        assertThrows(IllegalArgumentException.class, () -> cadastrarCliente.execute(cliente));
    }

    @Test
    void deveLancarExcecaoQuandoCpfDuplicado() {
        Cliente c1 = new Cliente(null, "João Silva", new CPF("63929247011"), LocalDate.of(1990, 1, 15), "Rua A, 123");
        Cliente c2 = new Cliente(null, "Maria Souza", new CPF("63929247011"), LocalDate.of(1985, 5, 20), "Rua A, 123");

        cadastrarCliente.execute(c1);

        assertThrows(ResourceAlreadyExists.class, () -> cadastrarCliente.execute(c2));
    }

    @Test
    void deveLancarExcecaoQuandoDataNascimentoFutura() {
        Cliente cliente = new Cliente(null, "João Silva", new CPF("63929247011"), LocalDate.now().plusDays(1), "Rua A, 123");

        assertThrows(IllegalArgumentException.class, () -> cadastrarCliente.execute(cliente));
    }
}