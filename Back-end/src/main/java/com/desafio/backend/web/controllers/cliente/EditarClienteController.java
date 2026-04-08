package com.desafio.backend.web.controllers.cliente;

import com.desafio.backend.application.useCases.cliente.EditarClienteUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class EditarClienteController {

    private final EditarClienteUseCase editarCliente;

    public EditarClienteController(EditarClienteUseCase editarCliente) {
        this.editarCliente = editarCliente;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        editarCliente.execute(cliente);
        return ResponseEntity.noContent().build();
    }
}
