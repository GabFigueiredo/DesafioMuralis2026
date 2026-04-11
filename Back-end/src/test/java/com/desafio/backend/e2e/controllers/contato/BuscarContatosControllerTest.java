package com.desafio.backend.e2e.controllers.contato;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BuscarContatosControllerTest {

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
                new Cliente(null, "João Silva", new CPF("63929247011"), LocalDate.of(1990, 1, 1), null)
        );
        contato = contatoRepository.save(
                new Contato(null, cliente.getId(), new ContatoValor(TipoContato.EMAIL, "joao@email.com"), "obs")
        );
    }

    @AfterEach
    void tearDown() {
        contatoRepository.findAll(0, 1000).content().forEach(c -> contatoRepository.delete(c.getId()));
        clienteRepository.findAll(0, 1000).content().forEach(c -> clienteRepository.delete(c.getId()));
    }

    @Test
    void deveBuscarContatoPorId() {
        client.get()
                .uri("/contatos/" + contato.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(contato.getId())
                .jsonPath("$.contatoValor.tipo").isEqualTo("EMAIL")
                .jsonPath("$.contatoValor.value").isEqualTo("joao@email.com");
    }

    @Test
    void deveRetornar404QuandoContatoNaoExiste() {
        client.get()
                .uri("/contatos/9999")
                .exchange()
                .expectStatus().isNotFound();
    }
}