package com.desafio.backend.web.controllers.contato;

import com.desafio.backend.application.useCases.contato.EditarContatoUseCase;
import com.desafio.backend.enterprise.contato.Contato;
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
                                       @RequestBody Contato contato) {
        contato.setId(id);
        contato.setClienteId(clienteId);
        editarContato.execute(contato);
        return ResponseEntity.noContent().build();
    }
}
