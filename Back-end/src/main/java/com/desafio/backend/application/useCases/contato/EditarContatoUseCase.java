package com.desafio.backend.application.useCases.contato;

import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.IContatoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EditarContatoUseCase {
    private final IContatoRepository contatoRepository;

    public EditarContatoUseCase(IContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    public void execute(Contato contato) {
        contatoRepository.findById(contato.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado."));

        if (contato.getContatoValor().getTipo() == null)
            throw new IllegalArgumentException("Tipo do contato é obrigatório.");

        if (contato.getContatoValor().getValue() == null || contato.getContatoValor().getValue().isBlank())
            throw new IllegalArgumentException("Valor do contato é obrigatório.");

        contatoRepository.update(contato);
    }
}
