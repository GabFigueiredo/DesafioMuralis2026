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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class EditarContatoControllerTest {

    private RestTestClient client;

    @LocalServerPort
    private int port;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IContatoRepository contatoRepository;

    private Cliente cliente;
    private Contato contato;

    @BeforeEach
    void setup() {
        client = RestTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        cliente = clienteRepository.save(
                new Cliente(null, "João Silva", "111.111.111-11", LocalDate.of(1990, 1, 1), "Rua A, 123")
        );

        contato = contatoRepository.save(
                new Contato(null, cliente.getId(), "Email", "joao@email.com", null)
        );
    }

    @AfterEach
    void tearDown() {
        clienteRepository.findAll().forEach(c -> clienteRepository.delete(c.getId()));
    }

    @Test
    void deveEditarContatoComSucesso() {

        contato.setValor("novo@email.com");

        client.put()
                .uri("/clientes/" + cliente.getId() + "/contatos/" + contato.getId())
                .body(contato)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deveRetornar400QuandoValorVazio() {

        contato.setValor("");

        client.put()
                .uri("/clientes/" + cliente.getId() + "/contatos/" + contato.getId())
                .body(contato)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deveRetornar404QuandoContatoNaoExiste() {

        contato.setValor("novo@email.com");

        client.put()
                .uri("/clientes/" + cliente.getId() + "/contatos/9999")
                .body(contato)
                .exchange()
                .expectStatus().isNotFound();
    }
}