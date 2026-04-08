package com.desafio.backend.e2e.controllers.cliente;

import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import com.desafio.backend.web.dto.cliente.CadastrarClienteRequest;
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
class CadastrarClienteControllerTest {

    private RestTestClient client;

    @Autowired
    private IClienteRepository clienteRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        client = RestTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @AfterEach
    void tearDown() {
        clienteRepository.findAll().forEach(c -> clienteRepository.delete(c.getId()));
    }

    @Test
    void deveCadastrarClienteComSucesso() {

        CadastrarClienteRequest request = new CadastrarClienteRequest();
        request.setNome("Gabriel");
        request.setCpf("123.456.789-00");
        request.setDataNascimento(LocalDate.of(2000, 1, 1));

        var result = client.post()
                .uri("/clientes")
                .body(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Cliente.class)
                .returnResult();

        Cliente response = result.getResponseBody();

        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    void deveRetornar400QuandoNomeVazio() {

        CadastrarClienteRequest request = new CadastrarClienteRequest();
        request.setNome("");
        request.setCpf("123.456.789-00");

        client.post()
                .uri("/clientes")
                .body(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deveRetornar409QuandoCpfDuplicado() {

        CadastrarClienteRequest request = new CadastrarClienteRequest();
        request.setNome("Gabriel");
        request.setCpf("123.456.789-00");

        // primeiro cadastro
        client.post()
                .uri("/clientes")
                .body(request)
                .exchange()
                .expectStatus().isCreated();

        // segundo cadastro
        client.post()
                .uri("/clientes")
                .body(request)
                .exchange()
                .expectStatus().isEqualTo(409);
    }

    @Test
    void deveRetornar400QuandoDataInvalida() {

        CadastrarClienteRequest request = new CadastrarClienteRequest();
        request.setNome("Gabriel");
        request.setCpf("123.456.789-00");
        request.setDataNascimento(LocalDate.now().plusDays(1));

        client.post()
                .uri("/clientes")
                .body(request)
                .exchange()
                .expectStatus().isBadRequest();
    }
}