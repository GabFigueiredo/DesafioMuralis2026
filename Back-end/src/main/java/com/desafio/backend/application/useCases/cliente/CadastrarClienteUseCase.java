package com.desafio.backend.application.useCases.cliente;

import com.desafio.backend.application.exceptions.ResourceAlreadyExists;
import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class CadastrarClienteUseCase {
    private final IClienteRepository clienteRepository;

    public CadastrarClienteUseCase(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente execute(Cliente cliente) {
        // RN01 - Nome e CPF obrigatórios
        if (cliente.getNome() == null || cliente.getNome().isBlank())
            throw new IllegalArgumentException("Nome é obrigatório.");

        // RN04 - Nome não pode estar vazio
        if (cliente.getNome().trim().isEmpty())
            throw new IllegalArgumentException("Nome não pode estar vazio.");

        // RN05 - Data de nascimento válida
        if (cliente.getDataNascimento() != null && cliente.getDataNascimento().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Data de nascimento inválida.");

        // RN03 - CPF único
        clienteRepository.findByCpf(cliente.getCpf().getValue()).ifPresent(c -> {
            throw new ResourceAlreadyExists("CPF já cadastrado.");
        });

        if (cliente.getNome().length() > 100) {
            throw new IllegalArgumentException("O nome do cliente não pode ter mais de 100 caracteres");
        }

        if (cliente.getEndereco() != null && cliente.getEndereco().length() > 255) {
            throw new IllegalArgumentException("O Endereço do cliente não pode ter mais de 255 caracteres");
        }


        return clienteRepository.save(cliente);
    }
}
