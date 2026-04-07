package com.desafio.backend.infra.database;

import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.IContatoRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ContatoRepositoryJDBC implements IContatoRepository {

    private final JdbcTemplate jdbc;

    public ContatoRepositoryJDBC(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Mapper
    private final RowMapper<Contato> rowMapper = (rs, rowNum) -> new Contato(
            rs.getInt("id"),
            rs.getInt("cliente_id"),
            rs.getString("tipo"),
            rs.getString("valor"),
            rs.getString("observacao")
    );

    @Override
    public List<Contato> findByClienteId(Integer clienteId) {
        String sql = "SELECT * FROM contato WHERE cliente_id = ?";
        return jdbc.query(sql, rowMapper, clienteId);
    }

    @Override
    public Optional<Contato> findById(Integer id) {
        String sql = "SELECT * FROM contato WHERE id = ?";
        List<Contato> result = jdbc.query(sql, rowMapper, id);
        return result.stream().findFirst();
    }

    @Override
    public Contato save(Contato contato) {
        String sql = """
            INSERT INTO contato (cliente_id, tipo, valor, observacao)
            VALUES (?, ?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setInt(1, contato.getClienteId());
            ps.setString(2, contato.getTipo());
            ps.setString(3, contato.getValor());
            ps.setString(4, contato.getObservacao());

            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            contato.setId(keyHolder.getKey().intValue());
        }

        return contato;
    }

    @Override
    public void update(Contato contato) {
        String sql = """
            UPDATE contato
            SET cliente_id = ?, tipo = ?, valor = ?, observacao = ?
            WHERE id = ?
        """;

        jdbc.update(sql,
                contato.getClienteId(),
                contato.getTipo(),
                contato.getValor(),
                contato.getObservacao(),
                contato.getId()
        );
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM contato WHERE id = ?";
        jdbc.update(sql, id);
    }

    @Override
    public void deleteByClienteId(Integer clienteId) {
        String sql = "DELETE FROM contato WHERE cliente_id = ?";
        jdbc.update(sql, clienteId);
    }
}