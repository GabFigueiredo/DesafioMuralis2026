package com.desafio.backend.enterprise.contato;

import com.desafio.backend.enterprise.pagination.Page;

import java.util.List;
import java.util.Optional;

public interface IContatoRepository {
    Page<Contato> findAll(int page, int size);
    Page<Contato> findByClienteId(Integer clienteId, int page, int size);  // RF09
    Optional<Contato> findById(Integer id);
    Contato save(Contato contato);
    void update(Contato contato);
    void delete(Integer id);
    void deleteByClienteId(Integer clienteId);
}
