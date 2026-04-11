package com.desafio.backend.unit.useCases.cliente;

import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.application.useCases.cliente.BuscarClienteUseCase;
import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
import com.desafio.backend.enterprise.pagination.Page;
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
class BuscarClienteUseCaseTest {

    @Autowired
    private CadastrarClienteUseCase cadastrarCliente;

    @Autowired
    private BuscarClienteUseCase buscarCliente;

    @Test
    void deveBuscarPorCpfComSucesso() {
        cadastrarCliente.execute(new Cliente(null, "João", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A, 123"));

        Cliente encontrado = buscarCliente.executeByCpf(new CPF("63929247011"));

        assertEquals("João", encontrado.getNome());
    }

    @Test
    void deveBuscarPorNomeComSucesso() {
        cadastrarCliente.execute(new Cliente(null, "João Silva", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A, 123"));

        Page<Cliente> resultado = buscarCliente.executeByNome("João", 0, 10);

        assertFalse(resultado.content().isEmpty());
        assertEquals(1, resultado.totalElements());
    }

    @Test
    void deveLancarExcecaoQuandoBuscarPorNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> buscarCliente.executeByNome("", 0, 10));
    }

    @Test
    void deveLancarExcecaoQuandoCpfNaoEncontrado() {
        assertThrows(ResourceNotFoundException.class, () -> buscarCliente.executeByCpf(new CPF("63929247011")));
    }
}
