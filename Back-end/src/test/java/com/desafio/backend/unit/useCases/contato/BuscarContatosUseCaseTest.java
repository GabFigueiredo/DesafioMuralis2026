package com.desafio.backend.unit.useCases.contato;

import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.application.useCases.contato.CadastrarContatoUseCase;
import com.desafio.backend.application.useCases.contato.BuscarContatosUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.enums.TipoContato;
import com.desafio.backend.enterprise.contato.valueObjects.ContatoValor;
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
class BuscarContatosUseCaseTest {

    @Autowired
    private CadastrarClienteUseCase cadastrarCliente;

    @Autowired
    private CadastrarContatoUseCase cadastrarContato;

    @Autowired
    private BuscarContatosUseCase buscarContatos;

    private Integer clienteId;
    private Integer contatoId;

    @BeforeEach
    void setup() {
        Cliente cliente = cadastrarCliente.execute(
                new Cliente(null, "João", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A")
        );
        clienteId = cliente.getId();

        Contato contato = cadastrarContato.execute(
                new Contato(null, clienteId, new ContatoValor(TipoContato.TELEFONE, "11999990001"), "Celular")
        );
        contatoId = contato.getId();
    }

    @Test
    void deveBuscarContatoPorId() {
        Contato result = buscarContatos.findById(contatoId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(contatoId);
    }

    @Test
    void deveLancarExcecaoQuandoContatoNaoExistir() {
        assertThatThrownBy(() -> buscarContatos.findById(9999))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Id não encontrado");
    }

    @Test
    void deveRetornarContatoComDadosCorretos() {
        Contato result = buscarContatos.findById(contatoId);

        assertThat(result.getContatoValor().getTipo()).isEqualTo(TipoContato.TELEFONE);
        assertThat(result.getContatoValor().getValue()).isEqualTo("11999990001");
        assertThat(result.getObservacao()).isEqualTo("Celular");
    }

}
