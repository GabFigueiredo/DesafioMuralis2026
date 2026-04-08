package com.desafio.backend.web.controllers.cliente;

import com.desafio.backend.application.useCases.cliente.ListarClientesUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ListarClientesController {

    private final ListarClientesUseCase listarClientes;

    public ListarClientesController(ListarClientesUseCase listarClientes) {
        this.listarClientes = listarClientes;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(listarClientes.execute());
    }
}
