package com.desafio.backend.unit.useCases.cliente;

import com.desafio.backend.application.exceptions.ResourceAlreadyExists;
import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.application.useCases.cliente.EditarClienteUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
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
class EditarClienteUseCaseTest {

    @Autowired
    private CadastrarClienteUseCase cadastrarCliente;

    @Autowired
    private EditarClienteUseCase editarCliente;

    @Test
    void deveEditarClienteComSucesso() {
        Cliente salvo = cadastrarCliente.execute(
                new Cliente(null, "João Silva", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A, 123")
        );

        salvo.setNome("João Atualizado");
        salvo.setEndereco("Rua Nova, 456");

        assertDoesNotThrow(() -> editarCliente.execute(salvo));
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        Cliente inexistente = new Cliente(9999, "Fantasma", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A, 123");

        assertThrows(ResourceNotFoundException.class, () -> editarCliente.execute(inexistente));
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaUsadoPorOutro() {
        Cliente c1 = cadastrarCliente.execute(
                new Cliente(null, "João", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A, 123")
        );
        Cliente c2 = cadastrarCliente.execute(
                new Cliente(null, "Maria", new CPF("52998224725"), LocalDate.of(1992, 3, 3), "Rua A, 123")
        );

        c2.setCpf(new CPF("63929247011"));

        assertThrows(ResourceAlreadyExists.class, () -> editarCliente.execute(c2));
    }

    @Test
    void devePermitirEdicaoComMesmoCpf() {
        Cliente salvo = cadastrarCliente.execute(
                new Cliente(null, "João", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A, 123")
        );

        salvo.setNome("João Atualizado");
        // same CPF, should not throw

        assertDoesNotThrow(() -> editarCliente.execute(salvo));
    }
}
