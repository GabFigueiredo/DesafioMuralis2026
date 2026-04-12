package com.desafio.backend.application.useCases.contato;

import com.desafio.backend.application.exceptions.ResourceNotFoundException;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.IContatoRepository;
import com.desafio.backend.enterprise.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BuscarContatosUseCase {
    private final IContatoRepository contatoRepository;

    public BuscarContatosUseCase(IContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    public Contato findById(Integer id) {
        return contatoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
    }
}
