package com.desafio.backend.e2e.controllers.cliente;

import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
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
class ExcluirClienteControllerTest {

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
    void deveExcluirClienteComSucesso() {

        client.delete()
                .uri("/clientes/" + cliente.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deveRetornar400QuandoClienteNaoExiste() {

        client.delete()
                .uri("/clientes/9999")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deveExcluirContatosJuntoComCliente() {

        client.delete()
                .uri("/clientes/" + cliente.getId())
                .exchange()
                .expectStatus().isNoContent();

        // valida que o cliente realmente não existe mais
        client.get()
                .uri("/clientes/cpf/" + cliente.getCpf())
                .exchange()
                .expectStatus().isNotFound();
    }
}