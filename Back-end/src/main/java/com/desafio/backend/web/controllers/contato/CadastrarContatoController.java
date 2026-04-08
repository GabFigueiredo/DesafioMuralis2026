package com.desafio.backend.web.controllers.contato;

import com.desafio.backend.application.useCases.contato.CadastrarContatoUseCase;
import com.desafio.backend.enterprise.contato.Contato;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes/{clienteId}/contatos")
public class CadastrarContatoController {

    private final CadastrarContatoUseCase cadastrarContato;

    public CadastrarContatoController(CadastrarContatoUseCase cadastrarContato) {
        this.cadastrarContato = cadastrarContato;
    }

    @PostMapping
    public ResponseEntity<Contato> cadastrar(@PathVariable Integer clienteId, @RequestBody Contato contato) {
        contato.setClienteId(clienteId);
        Contato salvo = cadastrarContato.execute(contato);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}
