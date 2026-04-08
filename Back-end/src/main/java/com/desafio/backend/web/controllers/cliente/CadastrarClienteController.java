package com.desafio.backend.web.controllers.cliente;

import com.desafio.backend.application.useCases.cliente.CadastrarClienteUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.web.dto.cliente.CadastrarClienteRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class CadastrarClienteController {

    private final CadastrarClienteUseCase cadastrarCliente;

    public CadastrarClienteController(CadastrarClienteUseCase cadastrarCliente) {
        this.cadastrarCliente = cadastrarCliente;
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrar(@RequestBody CadastrarClienteRequest request) {

        Cliente cliente = new Cliente(
                null,
                request.getNome(),
                request.getCpf(),
                request.getDataNascimento(),
                request.getEndereco()
        );

        Cliente salvo = cadastrarCliente.execute(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}
