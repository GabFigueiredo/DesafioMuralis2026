package com.desafio.backend.web.controllers.contato;

import com.desafio.backend.application.useCases.contato.CadastrarContatoUseCase;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.enums.TipoContato;
import com.desafio.backend.enterprise.contato.valueObjects.ContatoValor;
import com.desafio.backend.web.dto.contato.CadastrarContatoRequest;
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
    public ResponseEntity<Contato> cadastrar(@PathVariable Integer clienteId, @RequestBody CadastrarContatoRequest request) {
        Contato novoContato = new Contato(
                null, clienteId,
                new ContatoValor(TipoContato.valueOf(request.getTipo()), request.getValor()),
                request.getObservacao()
        );

        Contato salvo = cadastrarContato.execute(novoContato);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}
