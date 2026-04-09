package com.desafio.backend.unit.useCases.cliente;

import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.application.useCases.cliente.ListarClientesUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
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
class ListarClientesUseCaseTest {

    @Autowired
    private CadastrarClienteUseCase cadastrarCliente;

    @Autowired
    private ListarClientesUseCase listarClientes;

    @Test
    void deveRetornarListaDeClientes() {
        cadastrarCliente.execute(new Cliente(null, "João", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A, 123"));
        cadastrarCliente.execute(new Cliente(null, "Maria", new CPF("52998224725"), LocalDate.of(1992, 3, 3), "Rua A, 123"));

        List<Cliente> clientes = listarClientes.execute();

        assertTrue(clientes.size() >= 2);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaClientes() {
        List<Cliente> clientes = listarClientes.execute();

        assertNotNull(clientes);
        assertTrue(clientes.isEmpty());
    }
}
