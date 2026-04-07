package com.desafio.backend.application.useCases.cliente;

import com.desafio.backend.enterprise.cliente.Cliente;
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
        cadastrarCliente.execute(new Cliente(null, "João", "111.111.111-11", LocalDate.of(1990, 1, 1), null));

        Cliente encontrado = buscarCliente.executeByCpf("111.111.111-11");

        assertEquals("João", encontrado.getNome());
    }

    @Test
    void deveBuscarPorNomeComSucesso() {
        cadastrarCliente.execute(new Cliente(null, "João Silva", "111.111.111-11", LocalDate.of(1990, 1, 1), null));

        List<Cliente> encontrados = buscarCliente.executeByNome("João");

        assertFalse(encontrados.isEmpty());
    }

    @Test
    void deveLancarExcecaoQuandoCpfNaoEncontrado() {
        assertThrows(IllegalArgumentException.class, () -> buscarCliente.executeByCpf("000.000.000-00"));
    }

    @Test
    void deveLancarExcecaoQuandoBuscarPorNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> buscarCliente.executeByNome(""));
    }
}
