package com.desafio.backend.web.dto.cliente;

import java.time.LocalDate;

public class CadastrarClienteRequest {

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String endereco;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}
