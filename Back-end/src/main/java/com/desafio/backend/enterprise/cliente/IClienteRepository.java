package com.desafio.backend.enterprise.cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteRepository {
    List<Cliente> findAll();
    Optional<Cliente> findById(Integer id);
    Optional<Cliente> findByCpf(String cpf);        // RN03 + RF05
    List<Cliente> findByNome(String nome);           // RF05
    Cliente save(Cliente cliente);
    void update(Cliente cliente);
    void delete(Integer id);
}
