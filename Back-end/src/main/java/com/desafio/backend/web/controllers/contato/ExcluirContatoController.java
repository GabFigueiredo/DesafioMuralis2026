package com.desafio.backend.web.controllers.contato;

import com.desafio.backend.application.useCases.contato.ExcluirContatoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes/{clienteId}/contatos")
public class ExcluirContatoController {

    private final ExcluirContatoUseCase excluirContato;

    public ExcluirContatoController(ExcluirContatoUseCase excluirContato) {
        this.excluirContato = excluirContato;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer clienteId,
                                        @PathVariable Integer id) {
        excluirContato.execute(id);
        return ResponseEntity.noContent().build();
    }
}
