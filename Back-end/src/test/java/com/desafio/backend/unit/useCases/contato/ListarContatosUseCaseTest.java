package com.desafio.backend.unit.useCases.contato;

import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.application.useCases.contato.CadastrarContatoUseCase;
import com.desafio.backend.application.useCases.contato.ListarContatosUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.enums.TipoContato;
import com.desafio.backend.enterprise.contato.valueObjects.ContatoValor;
import com.desafio.backend.enterprise.pagination.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ListarContatosUseCaseTest {

    @Autowired
    private CadastrarClienteUseCase cadastrarCliente;

    @Autowired
    private CadastrarContatoUseCase cadastrarContato;

    @Autowired
    private ListarContatosUseCase listarContatos;

    private Integer clienteId;

    @BeforeEach
    void setup() {
        Cliente cliente = cadastrarCliente.execute(
                new Cliente(null, "João", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A")
        );
        clienteId = cliente.getId();
    }

    @Test
    void deveListarContatosDoCliente() {
        cadastrarContato.execute(new Contato(null, clienteId, new ContatoValor(TipoContato.TELEFONE, "11999990001"), "Celular"));
        cadastrarContato.execute(new Contato(null, clienteId, new ContatoValor(TipoContato.EMAIL, "joao@email.com"), "Email"));

        Page<Contato> result = listarContatos.findByClientId(clienteId, 0, 10);

        assertThat(result.content()).hasSize(2);
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExistir() {
        assertThatThrownBy(() -> listarContatos.findByClientId(9999, 0, 10))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cliente não encontrado");
    }

    @Test
    void deveLancarExcecaoQuandoPaginaNegativa() {
        assertThatThrownBy(() -> listarContatos.findByClientId(clienteId, -1, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("página não pode ser negativo");
    }

    @Test
    void deveLancarExcecaoQuandoTamanhoInvalido() {
        assertThatThrownBy(() -> listarContatos.findByClientId(clienteId, 0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("tamanho da página");

        assertThatThrownBy(() -> listarContatos.findByClientId(clienteId, 0, 101))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("tamanho da página");
    }

    @Test
    void deveRetornarPaginaVaziaQuandoClienteSemContatos() {
        Page<Contato> result = listarContatos.findByClientId(clienteId, 0, 10);

        assertThat(result.content()).isEmpty();
    }

    @Test
    void deveRespeitorPaginacao() {
        for (int i = 0; i < 5; i++) {
            cadastrarContato.execute(new Contato(null, clienteId, new ContatoValor(TipoContato.TELEFONE, "1199999000" + i), "Contato " + i));
        }

        Page<Contato> page0 = listarContatos.findByClientId(clienteId, 0, 2);
        Page<Contato> page1 = listarContatos.findByClientId(clienteId, 1, 2);

        assertThat(page0.content()).hasSize(2);
        assertThat(page1.content()).hasSize(2);
        assertThat(page0.content().get(0).getId()).isNotEqualTo(page1.content().get(0).getId());
    }
}