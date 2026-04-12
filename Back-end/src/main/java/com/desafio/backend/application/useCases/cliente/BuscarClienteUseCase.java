package com.desafio.backend.application.useCases.cliente;

import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
import com.desafio.backend.enterprise.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
    public Page<Cliente> executeByNome(String nome, int page, int size) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome para busca não pode ser vazio.");
        return clienteRepository.findByNome(nome, page, size);
    }

    // RF05 - Search by name or CPF
    public Cliente executeByCpf(CPF cpf) {
        return clienteRepository.findByCpf(cpf.getValue())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));
    }
}
