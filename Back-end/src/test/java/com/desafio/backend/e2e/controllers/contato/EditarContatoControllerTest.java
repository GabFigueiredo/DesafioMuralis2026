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
import java.util.Map;

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
                new Cliente(null, "João Silva", new CPF("63929247011"), LocalDate.of(1990, 1, 1), "Rua A, 123")
        );

        contato = contatoRepository.save(
                new Contato(null, cliente.getId(), new ContatoValor(TipoContato.EMAIL, "joao@email.com"), null)
        );
    }

    @AfterEach
    void tearDown() {
        clienteRepository.findAll().forEach(c -> clienteRepository.delete(c.getId()));
    }

    @Test
    void deveEditarContatoComSucesso() {

        contato.setContatoValor(new ContatoValor(TipoContato.EMAIL, "novo@email.com"));

        Map<String, Object> payload = Map.of(
                "tipo", "EMAIL",
                "valor", "gabriel@email.com",
                "observacao", "outra coisa"
        );

        client.put()
                .uri("/clientes/" + cliente.getId() + "/contatos/" + contato.getId())
                .body(payload)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deveRetornar400QuandoValorVazio() {

        Map<String, Object> payload = Map.of(
                "tipo", "EMAIL",
                "valor", "",
                "observacao", "outra coisa"
        );

        client.put()
                .uri("/clientes/" + cliente.getId() + "/contatos/" + contato.getId())
                .body(payload)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deveRetornar404QuandoContatoNaoExiste() {

        Map<String, Object> payload = Map.of(
                "tipo", "EMAIL",
                "valor", "gabriel@email.com",
                "observacao", "outra coisa"
        );

        client.put()
                .uri("/clientes/" + cliente.getId() + "/contatos/9999")
                .body(payload)
                .exchange()
                .expectStatus().isNotFound();
    }
}