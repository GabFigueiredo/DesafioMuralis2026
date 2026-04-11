package com.desafio.backend.e2e.controllers.cliente;

import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ListarClientesControllerTest {

    private RestTestClient client;

    @LocalServerPort
    private int port;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private CadastrarClienteUseCase cadastrarCliente;

    @BeforeEach
    void setup() {
        client = RestTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        cadastrarCliente.execute(new Cliente(null, "João", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A"));
        cadastrarCliente.execute(new Cliente(null, "Maria", new CPF("52998224725"), LocalDate.of(1992, 3, 3), "Rua B"));
    }

    @AfterEach
    void tearDown() {
        clienteRepository.findAll(0, 1000).content().forEach(c -> clienteRepository.delete(c.getId()));
    }

    @Test
    void deveRetornarPaginaDeClientes() {
        client.get()
                .uri("/clientes?page=0&size=10")
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
                .uri("/clientes?page=0&size=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.length()").isEqualTo(1);
    }

    @Test
    void deveRetornar400QuandoPageNegativa() {
        client.get()
                .uri("/clientes?page=-1&size=10")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deveRetornar400QuandoSizeInvalido() {
        client.get()
                .uri("/clientes?page=0&size=0")
                .exchange()
                .expectStatus().isBadRequest();
    }
}