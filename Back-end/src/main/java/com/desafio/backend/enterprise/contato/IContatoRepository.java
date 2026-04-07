package com.desafio.backend.enterprise.contato;

import java.util.List;
import java.util.Optional;

public interface IContatoRepository {
    List<Contato> findByClienteId(Integer clienteId);  // RF09
    Optional<Contato> findById(Integer id);
    Contato save(Contato contato);
    void update(Contato contato);
    void delete(Integer id);
    void deleteByClienteId(Integer clienteId);
}
