package com.desafio.backend.application.useCases.contato;

import com.desafio.backend.application.exceptions.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));


        if (contato.getContatoValor().getValue() == null) {
            System.out.println("é nulo");
        }

        // RN02 - Tipo e Valor obrigatórios
        if (contato.getContatoValor().getTipo() == null)
            throw new IllegalArgumentException("Tipo do contato é obrigatório.");

        if (contato.getContatoValor().getValue() == null || contato.getContatoValor().getValue().isBlank())
            throw new IllegalArgumentException("Valor do contato é obrigatório.");

        return contatoRepository.save(contato);
    }
}
