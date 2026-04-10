package com.desafio.backend.unit.useCases.contato;

import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.application.useCases.contato.CadastrarContatoUseCase;
import com.desafio.backend.application.useCases.contato.EditarContatoUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.enums.TipoContato;
import com.desafio.backend.enterprise.contato.valueObjects.ContatoValor;
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
                new Cliente(null, "João", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "alguma coisa")
        );
        Contato salvo = cadastrarContato.execute(
                new Contato(null, cliente.getId(), new ContatoValor(TipoContato.EMAIL, "joao@email.com"), "alguma coisa")
        );

        salvo.setContatoValor(new ContatoValor(TipoContato.TELEFONE, "11999999999"));

        assertDoesNotThrow(() -> editarContato.execute(salvo));
    }

    @Test
    void deveLancarExcecaoQuandoContatoNaoExiste() {
        Contato inexistente = new Contato(9999, 1, new ContatoValor(TipoContato.EMAIL, "joao@email.com"), "alguma coisa");

        assertThrows(ResourceNotFoundException.class, () -> editarContato.execute(inexistente));
    }

}
