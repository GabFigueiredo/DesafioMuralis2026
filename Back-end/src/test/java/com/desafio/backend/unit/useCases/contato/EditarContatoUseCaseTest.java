package com.desafio.backend.unit.useCases.contato;

import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.application.useCases.contato.CadastrarContatoUseCase;
import com.desafio.backend.application.useCases.contato.EditarContatoUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.contato.Contato;
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
class EditarContatoUseCaseTest {

    @Autowired
    private CadastrarClienteUseCase cadastrarCliente;

    @Autowired
    private CadastrarContatoUseCase cadastrarContato;

    @Autowired
    private EditarContatoUseCase editarContato;

    @Test
    void deveEditarContatoComSucesso() {
        Cliente cliente = cadastrarCliente.execute(
                new Cliente(null, "João", "111.111.111-11", LocalDate.of(1990, 1, 1), null)
        );
        Contato salvo = cadastrarContato.execute(
                new Contato(null, cliente.getId(), "Email", "joao@email.com", null)
        );

        salvo.setValor("novo@email.com");

        assertDoesNotThrow(() -> editarContato.execute(salvo));
    }

    @Test
    void deveLancarExcecaoQuandoContatoNaoExiste() {
        Contato inexistente = new Contato(9999, 1, "Email", "x@x.com", null);

        assertThrows(IllegalArgumentException.class, () -> editarContato.execute(inexistente));
    }

    @Test
    void deveLancarExcecaoQuandoValorVazio() {
        Cliente cliente = cadastrarCliente.execute(
                new Cliente(null, "João", "111.111.111-11", LocalDate.of(1990, 1, 1), null)
        );
        Contato salvo = cadastrarContato.execute(
                new Contato(null, cliente.getId(), "Email", "joao@email.com", null)
        );

        salvo.setValor("");

        assertThrows(IllegalArgumentException.class, () -> editarContato.execute(salvo));
    }
}
