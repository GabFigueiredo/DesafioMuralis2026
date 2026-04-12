package com.desafio.backend.application.useCases.cliente;

import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExcluirClienteUseCase {
    private final IClienteRepository clienteRepository;

    public ExcluirClienteUseCase(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void execute(Integer id) {
        clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));
        clienteRepository.delete(id);
    }
}
