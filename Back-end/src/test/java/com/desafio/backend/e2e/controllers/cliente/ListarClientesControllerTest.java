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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ListarClientesControllerTest {

    private RestTestClient client;

    @LocalServerPort
    private int port;

    @Autowired
    private IClienteRepository clienteRepository;

    @BeforeEach
    void setup() {
        client = RestTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        clienteRepository.save(new Cliente(null, "João Silva", "111.111.111-11", LocalDate.of(1990, 1, 1), "Rua A, 123"));
        clienteRepository.save(new Cliente(null, "Maria Souza", "222.222.222-22", LocalDate.of(1992, 3, 3), "Rua A, 123"));
    }

    @AfterEach
    void tearDown() {
        clienteRepository.findAll().forEach(c -> clienteRepository.delete(c.getId()));
    }

    @Test
    void deveListarTodosOsClientes() {

        var result = client.get()
                .uri("/clientes")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cliente[].class)
                .returnResult();

        Cliente[] response = result.getResponseBody();

        assertNotNull(response);
        assertTrue(response.length >= 2);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaClientes() {

        clienteRepository.findAll().forEach(c -> clienteRepository.delete(c.getId()));

        var response = client.get()
                .uri("/clientes")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cliente[].class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals(0, response.length);
    }
}