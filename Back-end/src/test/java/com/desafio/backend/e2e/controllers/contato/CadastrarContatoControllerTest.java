package com.desafio.backend.e2e.controllers.contato;

import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import com.desafio.backend.enterprise.contato.Contato;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CadastrarContatoControllerTest {

    private RestTestClient client;

    @LocalServerPort
    private int port;

    @Autowired
    private IClienteRepository clienteRepository;

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
    }

    @AfterEach
    void tearDown() {
        clienteRepository.findAll().forEach(c -> clienteRepository.delete(c.getId()));
    }

    @Test
    void deveCadastrarContatoComSucesso() {

        Contato contato = new Contato(null, null, "Email", "joao@email.com", "Trabalho");

        var result = client.post()
                .uri("/clientes/" + cliente.getId() + "/contatos")
                .body(contato)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Contato.class)
                .returnResult();

        Contato response = result.getResponseBody();

        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    void deveRetornar400QuandoTipoVazio() {

        Contato contato = new Contato(null, null, "", "joao@email.com", null);

        client.post()
                .uri("/clientes/" + cliente.getId() + "/contatos")
                .body(contato)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deveRetornar400QuandoValorVazio() {

        Contato contato = new Contato(null, null, "Email", "", null);

        client.post()
                .uri("/clientes/" + cliente.getId() + "/contatos")
                .body(contato)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deveRetornar404QuandoClienteNaoExiste() {

        Contato contato = new Contato(null, null, "Email", "joao@email.com", null);

        client.post()
                .uri("/clientes/9999/contatos")
                .body(contato)
                .exchange()
                .expectStatus().isNotFound();
    }
}