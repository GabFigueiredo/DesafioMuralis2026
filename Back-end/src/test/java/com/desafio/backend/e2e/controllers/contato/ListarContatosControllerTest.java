package com.desafio.backend.e2e.controllers.contato;

import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.IContatoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    private Cliente cliente;

    @BeforeEach
    void setup() {
        client = RestTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        cliente = clienteRepository.save(
                new Cliente(null, "João Silva", "111.111.111-11", LocalDate.of(1990, 1, 1), null)
        );
        contatoRepository.save(new Contato(null, cliente.getId(), "Email", "joao@email.com", null));
        contatoRepository.save(new Contato(null, cliente.getId(), "Telefone", "11999999999", null));
    }

    @AfterEach
    void tearDown() {
        clienteRepository.findAll().forEach(c -> clienteRepository.delete(c.getId()));
    }

    @Test
    void deveListarContatosDoCliente() {

        var result = client.get()
                .uri("/clientes/" + cliente.getId() + "/contatos")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Contato[].class)
                .returnResult();

        Contato[] response = result.getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.length).isEqualTo(2);
    }

    @Test
    void deveRetornarListaVaziaParaClienteSemContatos() {

        Cliente semContatos = clienteRepository.save(
                new Cliente(null, "Maria", "222.222.222-22", LocalDate.of(1992, 3, 3), "Rua A, 123")
        );

        var response = client.get()
                .uri("/clientes/" + semContatos.getId() + "/contatos")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Contato[].class)
                .returnResult()
                .getResponseBody();

        assertEquals(0, response.length);
    }

    @Test
    void deveRetornar404QuandoClienteNaoExiste() {

        client.get()
                .uri("/clientes/9999/contatos")
                .exchange()
                .expectStatus().isNotFound();
    }
}