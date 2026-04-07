package com.desafio.backend.application.useCases.contato;

import com.desafio.backend.enterprise.cliente.IClienteRepository;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.IContatoRepository;
import org.springframework.stereotype.Service;

@Service
public class CadastrarContatoUseCase {
    private final IContatoRepository contatoRepository;
    private final IClienteRepository clienteRepository;

    public CadastrarContatoUseCase(IContatoRepository contatoRepository, IClienteRepository clienteRepository) {
        this.contatoRepository = contatoRepository;
        this.clienteRepository = clienteRepository;
    }

    public Contato execute(Contato contato) {
        // Ensure client exists
        clienteRepository.findById(contato.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));

        // RN02 - Tipo e Valor obrigatórios
        if (contato.getTipo() == null || contato.getTipo().isBlank())
            throw new IllegalArgumentException("Tipo do contato é obrigatório.");

        if (contato.getValor() == null || contato.getValor().isBlank())
            throw new IllegalArgumentException("Valor do contato é obrigatório.");

        return contatoRepository.save(contato);
    }
}
