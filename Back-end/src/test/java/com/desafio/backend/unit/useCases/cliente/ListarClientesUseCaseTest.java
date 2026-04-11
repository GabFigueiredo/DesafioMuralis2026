package com.desafio.backend.unit.useCases.cliente;

import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.application.useCases.cliente.ListarClientesUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
import com.desafio.backend.enterprise.pagination.Page;
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
class ListarClientesUseCaseTest {

    @Autowired
    private CadastrarClienteUseCase cadastrarCliente;

    @Autowired
    private ListarClientesUseCase listarClientes;

    @Test
    void deveRetornarClientesPaginados() {
        cadastrarCliente.execute(new Cliente(null, "João", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A"));
        cadastrarCliente.execute(new Cliente(null, "Maria", new CPF("52998224725"), LocalDate.of(1992, 3, 3), "Rua B"));

        Page<Cliente> result = listarClientes.execute(0, 10);

        assertNotNull(result);
        assertFalse(result.content().isEmpty());
        assertEquals(0, result.page());
        assertEquals(10, result.size());
        assertTrue(result.totalElements() >= 2);
    }

    @Test
    void deveRespeitarTamanhoDaPagina() {
        cadastrarCliente.execute(new Cliente(null, "João", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A"));
        cadastrarCliente.execute(new Cliente(null, "Maria", new CPF("52998224725"), LocalDate.of(1992, 3, 3), "Rua B"));
        cadastrarCliente.execute(new Cliente(null, "Carlos", new CPF("45678901249"), LocalDate.of(1985, 6, 15), "Rua C"));

        Page<Cliente> result = listarClientes.execute(0, 2);

        assertEquals(2, result.content().size());
        assertEquals(2, result.size());
    }

    @Test
    void deveLancarExcecaoQuandoPageNegativa() {
        assertThrows(IllegalArgumentException.class, () -> listarClientes.execute(-1, 10));
    }

    @Test
    void deveLancarExcecaoQuandoSizeInvalido() {
        assertThrows(IllegalArgumentException.class, () -> listarClientes.execute(0, 0));
        assertThrows(IllegalArgumentException.class, () -> listarClientes.execute(0, 101));
    }
}