package com.desafio.backend.application.useCases.cliente;

import com.desafio.backend.application.exceptions.ResourceAlreadyExists;
import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class EditarClienteUseCase {
    private final IClienteRepository clienteRepository;

    public EditarClienteUseCase(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void execute(Cliente cliente) {
        // RN08 - Validate before editing
        clienteRepository.findById(cliente.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));

        if (cliente.getNome() == null || cliente.getNome().isBlank())
            throw new IllegalArgumentException("Nome é obrigatório.");

        if (cliente.getDataNascimento() != null && cliente.getDataNascimento().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Data de nascimento inválida.");

        // RN03 - CPF único (exclude self)
        clienteRepository.findByCpf(cliente.getCpf().getValue()).ifPresent(existing -> {
            if (!existing.getId().equals(cliente.getId()))
                throw new ResourceAlreadyExists("CPF já cadastrado.");
        });

        clienteRepository.update(cliente);
    }
}
