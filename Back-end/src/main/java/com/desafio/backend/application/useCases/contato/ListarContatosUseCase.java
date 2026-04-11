package com.desafio.backend.application.useCases.contato;

import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.IContatoRepository;
import com.desafio.backend.enterprise.pagination.Page;
import org.springframework.stereotype.Service;

@Service
public class ListarContatosUseCase {

    private final IContatoRepository contatoRepository;
    private final IClienteRepository clienteRepository;

    public ListarContatosUseCase(IContatoRepository contatoRepository, IClienteRepository clienteRepository) {
        this.contatoRepository = contatoRepository;
        this.clienteRepository = clienteRepository;
    }

    public Page<Contato> findByClientId(Integer clienteId, int page, int size) {
        if (page < 0)
            throw new IllegalArgumentException("O número da página não pode ser negativo.");
        if (size < 1 || size > 100)
            throw new IllegalArgumentException("O tamanho da página deve ser entre 1 e 100.");

        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));

        return contatoRepository.findByClienteId(clienteId, page, size);
    }
}