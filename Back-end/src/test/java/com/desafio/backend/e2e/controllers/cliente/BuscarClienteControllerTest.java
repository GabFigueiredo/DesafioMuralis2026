package com.desafio.backend.e2e.controllers.cliente;

import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
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
class BuscarClienteControllerTest {

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
                new Cliente(null, "João Silva", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A, 123")
        );
    }

    @AfterEach
    void tearDown() {
        clienteRepository.findAll()
                .forEach(c -> clienteRepository.delete(c.getId()));
    }

    @Test
    void deveBuscarClientePorCpf() {

        var result = client.get()
                .uri("/clientes/cpf/" + cliente.getCpf().getValue())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cliente.class)
                .returnResult();

        Cliente response = result.getResponseBody();

        assertNotNull(response);
        assertEquals(cliente.getCpf().getValue(), response.getCpf().getValue());
    }

    @Test
    void deveBuscarClientePorNome() {

        var response = client.get()
                .uri("/clientes/nome?nome=João")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cliente[].class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertTrue(response.length > 0);
    }

    @Test
    void deveRetornar404QuandoCpfNaoExiste() {

        client.get()
                .uri("/clientes/cpf/52998224725")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void deveRetornar400QuandoNomeVazio() {

        client.get()
                .uri("/clientes/nome?nome=")
                .exchange()
                .expectStatus().isBadRequest();
    }
}