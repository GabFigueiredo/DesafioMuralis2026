package com.desafio.backend.web.controllers.contato;

import com.desafio.backend.application.useCases.contato.ListarContatosUseCase;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.pagination.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes/{clienteId}/contatos")
public class ListarContatosController {

    private final ListarContatosUseCase listarContatos;

    public ListarContatosController(ListarContatosUseCase listarContatos) {
        this.listarContatos = listarContatos;
    }

    @GetMapping
    public ResponseEntity<Page<Contato>> listarContatosPorClienteId(
            @PathVariable Integer clienteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(listarContatos.findByClientId(clienteId, page, size));
    }
}