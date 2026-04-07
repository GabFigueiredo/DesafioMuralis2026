package com.desafio.backend.application.useCases.cliente;

import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
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
        Cliente cliente = new Cliente(null, "João Silva", "123.456.789-00", LocalDate.of(1990, 1, 15), "Rua A, 123");

        Cliente salvo = cadastrarCliente.execute(cliente);

        assertNotNull(salvo.getId());
        assertEquals("João Silva", salvo.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoNomeVazio() {
        Cliente cliente = new Cliente(null, "", "123.456.789-00", LocalDate.of(1990, 1, 15), null);

        assertThrows(IllegalArgumentException.class, () -> cadastrarCliente.execute(cliente));
    }

    @Test
    void deveLancarExcecaoQuandoCpfNulo() {
        Cliente cliente = new Cliente(null, "João Silva", null, LocalDate.of(1990, 1, 15), null);

        assertThrows(IllegalArgumentException.class, () -> cadastrarCliente.execute(cliente));
    }

    @Test
    void deveLancarExcecaoQuandoCpfDuplicado() {
        Cliente c1 = new Cliente(null, "João Silva", "123.456.789-00", LocalDate.of(1990, 1, 15), null);
        Cliente c2 = new Cliente(null, "Maria Souza", "123.456.789-00", LocalDate.of(1985, 5, 20), null);

        cadastrarCliente.execute(c1);

        assertThrows(IllegalArgumentException.class, () -> cadastrarCliente.execute(c2));
    }

    @Test
    void deveLancarExcecaoQuandoDataNascimentoFutura() {
        Cliente cliente = new Cliente(null, "João Silva", "999.999.999-99", LocalDate.now().plusDays(1), null);

        assertThrows(IllegalArgumentException.class, () -> cadastrarCliente.execute(cliente));
    }
}