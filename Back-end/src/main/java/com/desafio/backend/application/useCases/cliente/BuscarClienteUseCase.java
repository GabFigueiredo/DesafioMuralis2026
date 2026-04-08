package com.desafio.backend.application.useCases.cliente;

import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarClienteUseCase {
    private final IClienteRepository clienteRepository;

    public BuscarClienteUseCase(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente executeById(Integer id) {
        if (id == null)
            throw new IllegalArgumentException("Id para busca não pode ser vazio.");
        return clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
    }

    // RF05 - Search by name or CPF
    public List<Cliente> executeByNome(String nome) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome para busca não pode ser vazio.");
        return clienteRepository.findByNome(nome);
    }

    // RF05 - Search by name or CPF
    public Cliente executeByCpf(String cpf) {
        if (cpf == null || cpf.isBlank())
            throw new IllegalArgumentException("CPF para busca não pode ser vazio.");
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));
    }
}
