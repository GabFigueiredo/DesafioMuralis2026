package com.desafio.backend.web.controllers.contato;

import com.desafio.backend.application.useCases.contato.BuscarContatosUseCase;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.pagination.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contatos/{id}")
public class BuscarContatosController {

    private final BuscarContatosUseCase buscarContatos;

    public BuscarContatosController(BuscarContatosUseCase buscarContatos) {
        this.buscarContatos = buscarContatos;
    }

    @GetMapping
    public ResponseEntity<Contato> findByItsId(@PathVariable Integer id) {
        return ResponseEntity.ok(buscarContatos.findById(id));
    }
}