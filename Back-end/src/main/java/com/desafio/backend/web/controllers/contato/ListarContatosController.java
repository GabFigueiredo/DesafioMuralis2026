package com.desafio.backend.web.controllers.contato;

import com.desafio.backend.application.useCases.contato.ListarContatosUseCase;
import com.desafio.backend.enterprise.contato.Contato;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clientes/{clienteId}/contatos")
public class ListarContatosController {

    private final ListarContatosUseCase listarContatos;

    public ListarContatosController(ListarContatosUseCase listarContatos) {
        this.listarContatos = listarContatos;
    }

    @GetMapping
    public ResponseEntity<List<Contato>> listar(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(listarContatos.execute(clienteId));
    }
}
