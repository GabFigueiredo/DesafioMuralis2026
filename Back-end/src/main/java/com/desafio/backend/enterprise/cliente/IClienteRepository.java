package com.desafio.backend.enterprise.cliente;

import com.desafio.backend.enterprise.pagination.Page;

import java.util.List;
import java.util.Optional;

public interface IClienteRepository {
    Page<Cliente> findAll(int page, int size);
    Optional<Cliente> findById(Integer id);
    Optional<Cliente> findByCpf(String cpf);        // RN03 + RF05
    Page<Cliente> findByNome(String nome, int page, int size);          // RF05
    Cliente save(Cliente cliente);
    void update(Cliente cliente);
    void delete(Integer id);
}
