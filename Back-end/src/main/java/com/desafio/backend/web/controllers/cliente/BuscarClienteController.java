package com.desafio.backend.web.controllers.cliente;

import com.desafio.backend.application.useCases.cliente.BuscarClienteUseCase;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
import com.desafio.backend.enterprise.contato.IContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class BuscarClienteController {

    private final BuscarClienteUseCase buscarCliente;

    public BuscarClienteController(BuscarClienteUseCase buscarCliente) {
        this.buscarCliente = buscarCliente;
    }

    // Buscar por nome
    @GetMapping("/id/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
        Cliente cliente = buscarCliente.executeById(id);
        return ResponseEntity.ok(cliente);
    }

    // Buscar por nome
    @GetMapping("/nome")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {
        List<Cliente> clientes = buscarCliente.executeByNome(nome);
        return ResponseEntity.ok(clientes);
    }

    // Buscar por CPF
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
        Cliente cliente = buscarCliente.executeByCpf(new CPF(cpf));
        return ResponseEntity.ok(cliente);
    }
}
