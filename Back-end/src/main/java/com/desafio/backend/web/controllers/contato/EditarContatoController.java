package com.desafio.backend.web.controllers.contato;

import com.desafio.backend.application.useCases.contato.EditarContatoUseCase;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.enums.TipoContato;
import com.desafio.backend.enterprise.contato.valueObjects.ContatoValor;
import com.desafio.backend.web.dto.contato.EditarContatoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes/{clienteId}/contatos")
public class EditarContatoController {

    private final EditarContatoUseCase editarContato;

    public EditarContatoController(EditarContatoUseCase editarContato) {
        this.editarContato = editarContato;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Integer clienteId,
                                       @PathVariable Integer id,
                                       @RequestBody EditarContatoRequest request) {
        Contato contato = new Contato(
                id, clienteId,
                new ContatoValor(TipoContato.valueOf(request.getTipo()), request.getValor()),
                request.getObservacao()
        );
        editarContato.execute(contato);
        return ResponseEntity.noContent().build();
    }
}
