package com.desafio.backend.application.useCases.cliente;

import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarClientesUseCase {
    private final IClienteRepository clienteRepository;

    public ListarClientesUseCase(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> execute() {
        return clienteRepository.findAll();
    }
}
