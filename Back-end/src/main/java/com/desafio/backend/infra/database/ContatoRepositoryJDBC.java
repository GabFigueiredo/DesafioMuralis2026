package com.desafio.backend.infra.database;

import com.desafio.backend.enterprise.contato.Contato;
import com.desafio.backend.enterprise.contato.IContatoRepository;
import com.desafio.backend.enterprise.contato.enums.TipoContato;
import com.desafio.backend.enterprise.contato.valueObjects.ContatoValor;
import com.desafio.backend.enterprise.pagination.Page;
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
            new ContatoValor(TipoContato.valueOf(rs.getString("tipo").toUpperCase()), rs.getString("valor")),
            rs.getString("observacao")
    );

    @Override
    public Page<Contato> findAll(int page, int size) {
        String sql = "SELECT * FROM contato LIMIT ? OFFSET ?";
        int offset = page * size;
        List<Contato> content = jdbc.query(sql, rowMapper, size, offset);

        Long total = jdbc.queryForObject("SELECT COUNT(*) FROM contato", Long.class);
        long totalElements = total != null ? total : 0L;
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new Page<>(content, page, size, totalElements, totalPages);
    }

    @Override
    public Page<Contato> findByClienteId(Integer clienteId, int page, int size) {
        String sql = "SELECT * FROM contato WHERE cliente_id = ? LIMIT ? OFFSET ?";
        int offset = page * size;
        List<Contato> content = jdbc.query(sql, rowMapper, clienteId, size, offset);

        Long total = jdbc.queryForObject("SELECT COUNT(*) FROM contato WHERE cliente_id = ?", Long.class, clienteId);
        long totalElements = total != null ? total : 0L;
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new Page<>(content, page, size, totalElements, totalPages);
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
            ps.setString(2, contato.getContatoValor().getTipo().toString().toUpperCase());
            ps.setString(3, contato.getContatoValor().getValue());
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
                contato.getContatoValor().getTipo().toString(),
                contato.getContatoValor().getValue(),
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