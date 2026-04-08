package com.desafio.backend.web.controllers.cliente;

import com.desafio.backend.application.useCases.cliente.ExcluirClienteUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ExcluirClienteController {

    private final ExcluirClienteUseCase excluirCliente;

    public ExcluirClienteController(ExcluirClienteUseCase excluirCliente) {
        this.excluirCliente = excluirCliente;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        excluirCliente.execute(id);
        return ResponseEntity.noContent().build();
    }
}
