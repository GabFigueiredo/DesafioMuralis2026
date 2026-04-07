package com.desafio.backend.application.useCases.contato;

import com.desafio.backend.enterprise.cliente.IClienteRepository;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.IContatoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarContatosUseCase {
    private final IContatoRepository contatoRepository;
    private final IClienteRepository clienteRepository;

    public ListarContatosUseCase(IContatoRepository contatoRepository, IClienteRepository clienteRepository) {
        this.contatoRepository = contatoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Contato> execute(Integer clienteId) {
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
        return contatoRepository.findByClienteId(clienteId);
    }
}
