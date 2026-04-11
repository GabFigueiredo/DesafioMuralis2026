package com.desafio.backend.application.useCases.cliente;

import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import com.desafio.backend.enterprise.pagination.Page;
import org.springframework.stereotype.Service;

@Service
public class ListarClientesUseCase {

    private final IClienteRepository clienteRepository;

    public ListarClientesUseCase(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Page<Cliente> execute(int page, int size) {
        if (page < 0)
            throw new IllegalArgumentException("O número da página não pode ser negativo.");
        if (size < 1 || size > 100)
            throw new IllegalArgumentException("O tamanho da página deve ser entre 1 e 100.");

        return clienteRepository.findAll(page, size);
    }
}