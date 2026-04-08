package com.desafio.backend.unit.useCases.cliente;

import com.desafio.backend.application.exceptions.ResourceAlreadyExists;
import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.application.useCases.cliente.EditarClienteUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
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
                new Cliente(null, "João Silva", "111.111.111-11", LocalDate.of(1990, 1, 1), "Rua A, 123")
        );

        salvo.setNome("João Atualizado");
        salvo.setEndereco("Rua Nova, 456");

        assertDoesNotThrow(() -> editarCliente.execute(salvo));
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        Cliente inexistente = new Cliente(9999, "Fantasma", "000.000.000-00", LocalDate.of(1990, 1, 1), "Rua A, 123");

        assertThrows(ResourceNotFoundException.class, () -> editarCliente.execute(inexistente));
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaUsadoPorOutro() {
        Cliente c1 = cadastrarCliente.execute(
                new Cliente(null, "João", "111.111.111-11", LocalDate.of(1990, 1, 1), "Rua A, 123")
        );
        Cliente c2 = cadastrarCliente.execute(
                new Cliente(null, "Maria", "222.222.222-22", LocalDate.of(1992, 3, 3), "Rua A, 123")
        );

        c2.setCpf("111.111.111-11");

        assertThrows(ResourceAlreadyExists.class, () -> editarCliente.execute(c2));
    }

    @Test
    void devePermitirEdicaoComMesmoCpf() {
        Cliente salvo = cadastrarCliente.execute(
                new Cliente(null, "João", "111.111.111-11", LocalDate.of(1990, 1, 1), "Rua A, 123")
        );

        salvo.setNome("João Atualizado");
        // same CPF, should not throw

        assertDoesNotThrow(() -> editarCliente.execute(salvo));
    }
}
