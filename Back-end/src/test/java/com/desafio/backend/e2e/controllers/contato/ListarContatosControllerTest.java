package com.desafio.backend.e2e.controllers.contato;

import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.application.useCases.contato.CadastrarContatoUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.IContatoRepository;
import com.desafio.backend.enterprise.contato.enums.TipoContato;
import com.desafio.backend.enterprise.contato.valueObjects.ContatoValor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ListarContatosControllerTest {

    private RestTestClient client;

    @LocalServerPort
    private int port;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IContatoRepository contatoRepository;

    @Autowired
    private CadastrarClienteUseCase cadastrarCliente;

    @Autowired
    private CadastrarContatoUseCase cadastrarContato;

    private Integer clienteId;

    @BeforeEach
    void setup() {
        client = RestTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        Cliente cliente = cadastrarCliente.execute(
                new Cliente(null, "João", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A")
        );
        clienteId = cliente.getId();

        cadastrarContato.execute(new Contato(null, clienteId, new ContatoValor(TipoContato.EMAIL, "joao@email.com"), "obs1"));
        cadastrarContato.execute(new Contato(null, clienteId, new ContatoValor(TipoContato.TELEFONE, "11912345678"), "obs2"));
    }

    @AfterEach
    void tearDown() {
        contatoRepository.findAll(0, 1000).content().forEach(c -> contatoRepository.delete(c.getId()));
        clienteRepository.findAll(0, 1000).content().forEach(c -> clienteRepository.delete(c.getId()));
    }

    @Test
    void deveRetornarPaginaDeContatos() {
        client.get()
                .uri("/clientes/" + clienteId + "/contatos?page=0&size=10")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content").isArray()
                .jsonPath("$.page").isEqualTo(0)
                .jsonPath("$.size").isEqualTo(10)
                .jsonPath("$.totalElements").isNumber();
    }

    @Test
    void deveRespeitarTamanhoDaPagina() {
        client.get()
                .uri("/clientes/" + clienteId + "/contatos?page=0&size=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.length()").isEqualTo(1);
    }

    @Test
    void deveRetornarListaVaziaParaClienteSemContatos() {
        Cliente semContatos = cadastrarCliente.execute(
                new Cliente(null, "Maria", new CPF("52998224725"), LocalDate.of(1992, 3, 3), "Rua B")
        );

        client.get()
                .uri("/clientes/" + semContatos.getId() + "/contatos?page=0&size=10")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.length()").isEqualTo(0);
    }

    @Test
    void deveRetornar404QuandoClienteNaoExiste() {
        client.get()
                .uri("/clientes/9999/contatos?page=0&size=10")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void deveRetornar400QuandoPageNegativa() {
        client.get()
                .uri("/clientes/" + clienteId + "/contatos?page=-1&size=10")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deveRetornar400QuandoSizeInvalido() {
        client.get()
                .uri("/clientes/" + clienteId + "/contatos?page=0&size=0")
                .exchange()
                .expectStatus().isBadRequest();
    }
}