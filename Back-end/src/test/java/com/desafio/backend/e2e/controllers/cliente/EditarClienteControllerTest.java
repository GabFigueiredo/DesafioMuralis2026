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
class EditarClienteControllerTest {

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
                new Cliente(null, "João Silva", "111.111.111-11", LocalDate.of(1990, 1, 1), "Rua A, 123")
        );
    }

    @AfterEach
    void tearDown() {
        clienteRepository.findAll().forEach(c -> clienteRepository.delete(c.getId()));
    }

    @Test
    void deveEditarClienteComSucesso() {

        cliente.setNome("João Atualizado");
        cliente.setEndereco("Rua Nova, 456");

        client.put()
                .uri("/clientes/" + cliente.getId())
                .body(cliente)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deveRetornar400QuandoNomeVazio() {

        cliente.setNome("");

        client.put()
                .uri("/clientes/" + cliente.getId())
                .body(cliente)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deveRetornar404QuandoClienteNaoExiste() {

        cliente.setId(9999);

        client.put()
                .uri("/clientes/9999")
                .body(cliente)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void deveRetornar409QuandoCpfJaExiste() {

        clienteRepository.save(
                new Cliente(null, "Maria", "222.222.222-22", LocalDate.of(1992, 3, 3), "Rua A, 123")
        );

        cliente.setCpf("222.222.222-22");

        client.put()
                .uri("/clientes/" + cliente.getId())
                .body(cliente)
                .exchange()
                .expectStatus().isEqualTo(409);
    }
}