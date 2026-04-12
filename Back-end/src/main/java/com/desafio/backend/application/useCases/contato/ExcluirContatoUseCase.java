package com.desafio.backend.application.useCases.contato;

import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.enterprise.contato.IContatoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExcluirContatoUseCase {
    private final IContatoRepository contatoRepository;

    public ExcluirContatoUseCase(IContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    public void execute(Integer id) {
        contatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado."));
        contatoRepository.delete(id);
    }
}
