package com.desafio.backend.infra.database;

import com.desafio.backend.enterprise.cliente.Cliente;
import com.desafio.backend.enterprise.cliente.IClienteRepository;
import com.desafio.backend.enterprise.cliente.valueObjects.CPF;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryJDBC implements IClienteRepository {

    private final JdbcTemplate jdbc;

    public ClienteRepositoryJDBC(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Mapper
    private RowMapper<Cliente> rowMapper = (rs, rowNum) -> new Cliente(
            rs.getInt("id"),
            rs.getString("nome"),
            new CPF(rs.getString("cpf")),
            rs.getDate("data_nascimento") != null
                    ? rs.getDate("data_nascimento").toLocalDate()
                    : null,
            rs.getString("endereco")
    );

    @Override
    public List<Cliente> findAll() {
        String sql = "SELECT * FROM cliente";
        return jdbc.query(sql, rowMapper);
    }

    @Override
    public Optional<Cliente> findById(Integer id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        List<Cliente> result = jdbc.query(sql, rowMapper, id);
        return result.stream().findFirst();
    }

    @Override
    public Optional<Cliente> findByCpf(String cpf) {
        String sql = "SELECT * FROM cliente WHERE cpf = ?";
        List<Cliente> result = jdbc.query(sql, rowMapper, cpf);
        return result.stream().findFirst();
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        String sql = "SELECT * FROM cliente WHERE LOWER(nome) LIKE LOWER(?)";
        return jdbc.query(sql, rowMapper, "%" + nome + "%");
    }

    @Override
    public Cliente save(Cliente cliente) {
        String sql = """
            INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
            VALUES (?, ?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf().getValue());

            if (cliente.getDataNascimento() != null) {
                ps.setDate(3, Date.valueOf(cliente.getDataNascimento()));
            } else {
                ps.setNull(3, Types.DATE);
            }

            ps.setString(4, cliente.getEndereco());

            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            cliente.setId(keyHolder.getKey().intValue());
        }

        return cliente;
    }

    @Override
    public void update(Cliente cliente) {
        String sql = """
            UPDATE cliente
            SET nome = ?, cpf = ?, data_nascimento = ?, endereco = ?
            WHERE id = ?
        """;

        jdbc.update(sql,
                cliente.getNome(),
                cliente.getCpf().getValue(),
                cliente.getDataNascimento() != null ? Date.valueOf(cliente.getDataNascimento()) : null,
                cliente.getEndereco(),
                cliente.getId()
        );
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM cliente WHERE id = ?";
        jdbc.update(sql, id);
    }
}
